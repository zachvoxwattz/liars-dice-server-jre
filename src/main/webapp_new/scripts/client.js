/**
 * The main instance of Socket.IO
 */
var SocketIOClient

/**
 * Self-explanatory
 */
var numberOfReconnectionAttempts = 5
var recvEventInputValue = ''
var registeredEvents = []

/**
 * Initializes the Socket.IO client with the given IP and Port.
 * @param {*} ip 
 * @param {*} port 
 */
const initializeSocketIOClient = (ip, port) => {
    SocketIOClient = io(`ws://${ip}:${port}/`, {
        autoConnect: false,
        forceNew: false,
        reconnection: true,
        reconnectionAttempts: numberOfReconnectionAttempts,
        reconnectionDelay: 1000,
        reconnectionDelayMax: 2000,
        randomizationFactor: 0.375,
        timeout: 10000,
        transports: ["websocket", "polling"]
    })

    SocketIOClient.on('connect', () => {
        setClientStatus('ACTIVE')
        logConsoleInfo('Connection established successfully.')

        // UI processing
        setButtonDisabledState(ElementBtnDisconnect, false)
        setButtonDisabledState(ElementBtnSendNetCode, false)
    })

    SocketIOClient.on('connect_error', (error) => {
        // if (SocketIOClient.active) {
        //     // temporary failure, the socket will automatically try to reconnect
        //     console.log(error)
        //     logConsoleError(error.message)
        // } else {
        // }

        logConsoleError(error.message)
    })

    SocketIOClient.io.on('reconnect_attempt', () => {
        logConsoleInfo('Failed to reconnect, retrying...')
    })

    SocketIOClient.io.on('reconnect_failed', () => {
        setClientStatus('ERROR')
        logConsoleError('Failed to reconnect after 5 tries. Closing socket...')
        SocketIOClient.close()

        // UI processing
        setButtonDisabledState(ElementBtnConnect, false)
        setButtonDisabledState(ElementBtnDisconnect, true)
        setButtonDisabledState(ElementBtnSendNetCode, true)
    })

    SocketIOClient.on('disconnect', (reason, details) => {
        setClientStatus('DISCONNECTED')
        logConsoleInfo('Connection terminated.')

        // UI processing
        setButtonDisabledState(ElementBtnConnect, false)
        setButtonDisabledState(ElementBtnDisconnect, true)
        setButtonDisabledState(ElementBtnSendNetCode, true)
    })

    registeredEvents.forEach((event) => registerSocketEvent(event))
}


/**
 * Connects the client with the given connection info.
 */
const ClientConnect = () => {
    let targetIPValue = ElementInputIP.value !== '' ? ElementInputIP.value : DefaultTargetIP
    let targetPortValue = ElementInputPort.value !== '' ? ElementInputPort.value : DefaultTargetPort

    // Verifies the inputs. If there is error, alert will be shown.
    let results = checkConnectionInput(targetIPValue, targetPortValue)
    if (results.length > 0) {
        let message = `Failed to connect to server - One or more validation errors have occurred:`
        results.forEach((item) => {
            message += `\n- ${item}`
        })

        alert(message)
        return
    }

    // Otherwise, proceeds to initialize client and prepares for connection.
    initializeSocketIOClient(targetIPValue, targetPortValue)
    
    // UI display
    setButtonDisabledState(ElementBtnConnect, true)
    setClientStatus('PENDING')
    logConsoleInfo('Establishing connection...')


    SocketIOClient.connect()
}


/**
 * Connects the client with the given connection info.
 */
const ClientDisconnect = () => {
    if (!SocketIOClient) {
        logConsoleError('Client has not been initialized yet!')
        return
    }
    SocketIOClient.disconnect()
}


const registerSocketEvent = (event) => {
    SocketIOClient.on(event, (recvData) => {
        let toBePrinted = recvData === undefined ? '{}' : JSON.stringify(recvData)
        logConsoleInfo(`Incoming event: '${event}' with datagram: ${toBePrinted}`)
    })
}

const sendToServer = () => {
    if (!SocketIOClient || !SocketIOClient.connected) {
        logConsoleError('Client is not currently connected to any server!')
        return
    }

    let netcode = ElementInputNetCode.value.trim()
    if (netcode.length === 0) {
        logConsoleError('Undefined netcode!')
        return
    }

    let body = ElementInputNetBody.value.trim()
    if (body.length !== 0 && !isJSON(body)) {
        logConsoleError('Invalid JSON datagram! Recheck for typos!')
        return
    }

    if (body.length !== 0) {
        SocketIOClient.emit(netcode, JSON.parse(body))
    } else {
        SocketIOClient.emit(netcode)
        body = '{}'
    }

    logConsoleInfo(`Sent '${netcode}' with: ${body}`)
}

const createNewEventEntry = () => {
    if (recvEventInputValue.length === 0) {
        logConsoleError('Event name cannot be empty!')
        clearRecvEventInput()
        return
    }

    if (registeredEvents.includes(recvEventInputValue)) {
        logConsoleError(`Event '${recvEventInputValue}' is already registered!`)
        clearRecvEventInput()
        return
    }

    let eventName = recvEventInputValue
    let domEntryId = `recv-event-entry-id_${eventName}`

    registeredEvents.push(eventName)
    if (SocketIOClient && SocketIOClient.connected) registerSocketEvent(eventName)

    let newEntry = document.createElement('button')
    newEntry.setAttribute('class', 'recv-event-entry')
    newEntry.setAttribute('id', domEntryId)
    newEntry.textContent = eventName
    newEntry.addEventListener('click', () => removeFromEventList(eventName))

    clearRecvEventInput()
    ElementDisplayEventList.appendChild(newEntry)
    logConsoleInfo(`Registered event listener for '${eventName}'`)
}

const removeFromEventList = (eventName) => {
    registeredEvents.splice(registeredEvents.indexOf(eventName), 1)
    if (SocketIOClient) SocketIOClient.off(eventName)

    let toDelete = document.getElementById(`recv-event-entry-id_${eventName}`)
    ElementDisplayEventList.removeChild(toDelete)
    logConsoleInfo(`Removed event listener for '${eventName}'`)
}

const clearRecvEventInput = () => {
    ElementInputRecvEvent.value = ''
    recvEventInputValue = ''
}