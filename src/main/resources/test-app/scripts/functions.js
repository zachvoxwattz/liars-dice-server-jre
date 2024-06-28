const isJSON = (json) => {
    if (typeof json !== "string") {
        return false;
    }
    try {
        JSON.parse(json);
        return true;
    } catch (error) {
        return false;
    }
}
/**
 * Sets the status of connection
 * @param {*} targetState Applicable values are: `IDLE`, `DISCONNECTED`, `PENDING`, `ACTIVE` and `ERROR`.
 */
const setConnectionStatus = (targetState) => {
    let state = targetState.toUpperCase()
    switch (state) {
        case 'IDLE':
            connectionStatusValue.style.color = connectionStatusColors.IDLE
            break
            
        case 'PENDING':
            connectionStatusValue.style.color = connectionStatusColors.PENDING
            break
            
        case 'ACTIVE':
            connectionStatusValue.style.color = connectionStatusColors.ACTIVE
            break
            
        case 'ERROR':
            connectionStatusValue.style.color = connectionStatusColors.ERROR
            break

        case 'DISCONNECTED':
            connectionStatusValue.style.color = connectionStatusColors.DISCONNECTED

        case 'KICKED':
            connectionStatusValue.style.color = connectionStatusColors.KICKED
        
        default:
            connectionStatusValue.style.color = 'white'
            break
    }
    connectionStatusValue.textContent = state
}

/**
 * Disables the connect button with given value
 * @param {*} disabled Either `true` or `false`.
 */
const disableConnectButton = (disabled) => {
    if (disabled) {
        connectButton.style.backgroundColor = connectButtonColors.disabled.background
        connectButton.style.borderColor = connectButtonColors.disabled.border
    }
    else {
        connectButton.style.backgroundColor = connectButtonColors.connect.background
        connectButton.style.borderColor = connectButtonColors.connect.border
    }
    connectButton.disabled = disabled
}

const sendToServer = () => {
    if (socketIOClient === undefined || !socketIOClient.active) {
        errorToConsole(`Client is not currently connected to any server!`)
        return
    }

    let netcode = sendEventInputNetcode.value.trim()
    if (netcode.length === 0) {
        errorToConsole(`Undefined netcode!`)
        return
    }
    
    let body = sendEventInputBody.value.trim()
    if (!isJSON(body)) {
        errorToConsole(`Invalid JSON datagram! Recheck for typos!`)
        return
    }

    socketIOClient.emit(netcode, JSON.parse(body))

    if (body.length === 0) body = '{}'
    infoToConsole(`Sent '${netcode}' with: ${body}`)
}

const createNewEventEntry = () => {
    if (recvEventInputValue.length === 0) {
        errorToConsole(`Event name can not be empty!`)
        clearRecvEventInput()
        return
    }

    if (registeredEvents.includes(recvEventInputValue)) {
        errorToConsole(`Event name '${recvEventInputValue}' already registered!`)
        clearRecvEventInput()
        return
    }

    let eventName = recvEventInputValue
    let DOMeventID = `data-section-recv-event-id_${eventName}`

    registeredEvents.push(eventName)
    if (socketIOClient && socketIOClient.active) addEventListener(eventName)
    
    let newEventEntry = document.createElement('button')
    newEventEntry.setAttribute('class', 'data-section-recv-event-entry')
    newEventEntry.setAttribute('id', DOMeventID)
    newEventEntry.textContent = eventName
    newEventEntry.addEventListener('click', () => {
        removeFromEventList(eventName)
    })

    clearRecvEventInput()
    addToEventList(newEventEntry)
    infoToConsole(`Registered a new event named '${eventName}'`)
}

/**
 * Adds a specified event to the list.
 */
const addToEventList = (eventComponent) => {
    eventList.appendChild(eventComponent)
}

const removeFromEventList = (eventName) => {
    let DOMeventID = `data-section-recv-event-id_${eventName}`
    let toBeDeletedIndex = registeredEvents.indexOf(eventName)
    registeredEvents.splice(toBeDeletedIndex, 1)
    socketIOClient.off(eventName)

    let toBeDeleted = document.getElementById(DOMeventID)
    eventList.removeChild(toBeDeleted)
    infoToConsole(`Removed event name '${eventName}'`)
}

/**
 * Erases everything presenting inside of the console.
 */
const clearConsole = () => {
    outputConsole.textContent = ''
}

/**
 * Erases value presenting inside of the input field of receiving segment.
 */
const clearRecvEventInput = () => {
    recvEventInput.textContent = ''
    recvEventInput.value = ''
}

/**
 * Appends text content to the output console.
 * @param {*} content String to be added.
 */
const infoToConsole = (content) => {
    var appendedContent = `Client|INFO> ${content}`

    outputConsole.textContent.length !== 0
        ? appendedContent = `\n${appendedContent}`
        : appendedContent = `${appendedContent}`

    outputConsole.textContent += appendedContent
    outputConsole.scrollTop = outputConsole.scrollHeight
}

/**
 * Appends text content to the output console.
 * @param {*} content String to be added.
 */
const errorToConsole = (content) => {
    var appendedContent = `Client|ERROR> ${content}`

    outputConsole.textContent.length !== 0
        ? appendedContent = `\n${appendedContent}`
        : appendedContent = `${appendedContent}`

    outputConsole.textContent += appendedContent
    outputConsole.scrollTop = outputConsole.scrollHeight
}

/**
 * Attempts to connect to the server with given IP and Port.
 */
const connectToServer = async () => {
    const ipRegEx = /^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\.(?!$)|$)){4}$/
    let targetIP = '127.0.0.1'
    let targetPort = 11912

    if (ipRegEx.test(targetIPInput.value)) targetIP = targetIPInput.value
    if (parseInt(targetPortInput.value)) targetPort = targetPortInput.value

    clientConnect(targetIP, targetPort)
    disableConnectButton(true)
}
