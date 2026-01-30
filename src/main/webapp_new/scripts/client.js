/**
 * The main instance of Socket.IO
 */
var SocketIOClient

/**
 * Self-explanatory
 */
var numberOfReconnectionAttempts = 5

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

    console.log(SocketIOClient)
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
    SocketIOClient.disconnect()
}