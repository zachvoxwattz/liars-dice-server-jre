const numberOfReconnectionAttemps = 3
var recvEventInputValue = ''
var registeredEvents = []
var socketIOClient
var isConnecting = false

const prepareSocketIOClient = (ip, port) => {
    socketIOClient = io(`ws://${ip}:${port}/`, {
        autoConnect: false,
        forceNew: false,
        reconnection: true,
        reconnectionAttempts: numberOfReconnectionAttemps - 1,
        reconnectionDelay: 1000,
        reconnectionDelayMax: 2000,
        randomizationFactor: 0.375,
        timeout: 10000,
        transports: ["websocket"]
    })

    socketIOClient.on('connect', () => {
        setConnectionStatus('ACTIVE')
        infoToConsole('Connection established.')
        isConnecting = false
    })

    socketIOClient.on('connect_error', () => {
        errorToConsole('Failed to connect. Retrying...')
    })

    socketIOClient.on('reconnect_failed', () => {
        setConnectionStatus('error')
        disableConnectButton(false)
        errorToConsole('Failed to connect to server. Reason: Unknown host or port.')
        genericClientDisconnect()
    })

    socketIOClient.on('disconnect', () => {
        setConnectionStatus('disconnected')
        disableConnectButton(false)
        infoToConsole('Disconnected by server.')
        isConnecting = false
    })

    registeredEvents.forEach((entry) => { addEventListener(entry) })
    setConnectionStatus('pending')
}

const connectSocketIOClient = () => {
    socketIOClient.connect()
    infoToConsole('Establishing connection...')
    isConnecting = true
}

const addEventListener = (event) => {
    socketIOClient.on(event, (recvData) => {
        let toBePrinted

        if (recvData === undefined) toBePrinted = '{}'
        else toBePrinted = JSON.stringify(recvData)

        infoToConsole(`Incoming event: '${event}' with datagram: ${toBePrinted}`)
    })
}

const genericClientDisconnect = () => {
    socketIOClient.off()
    socketIOClient.disconnect()
}

const userDisconnect = () => {
    if (socketIOClient === undefined) {
        errorToConsole('Client has not been initialized yet!')
        return
    }

    else if (!isConnecting && socketIOClient.disconnected) {
        errorToConsole('Client is currently not connected to a server!')
        return
    }

    if (isConnecting) infoToConsole('Terminated in-progress connection.')
    else infoToConsole('Terminated connection.')

    genericClientDisconnect()
    disableConnectButton(false)
    setConnectionStatus('idle')
    isConnecting = false
}
