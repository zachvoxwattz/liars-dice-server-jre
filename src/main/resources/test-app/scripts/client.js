var recvEventInputValue = ''
var registeredEvents = []
var socketIOClient

const clientConnect = (ip, port) => {
    socketIOClient = io(`ws://${ip}:${port}`, {
        autoConnect: false,
        transports: ["websocket"]
    })

    socketIOClient.on('connect', () => {
        setConnectionStatus('ACTIVE')
    })

    socketIOClient.io.on('error', () => {
        setConnectionStatus('ERROR')
        clientDisconnect()
    })

    socketIOClient.on('disconnect', () => {
        clientDisconnect()
    })

    socketIOClient.on('sv-force-kick', () => {
        clientDisconnect()
    })

    registeredEvents.forEach((entry) => { addEventListener(entry) })

    setConnectionStatus('PENDING')
    socketIOClient.connect()
}

const addEventListener = (event) => {
    socketIOClient.on(event, (recvData) => {
        infoToConsole(`Incoming event: '${event}' with datagram: ${JSON.stringify(recvData)}`)
    })
}

const clientDisconnect = () => {
    if (socketIOClient === undefined || !socketIOClient.active) {
        errorToConsole('Can not disconnect as no existing connection found!')
        return
    }

    socketIOClient.off()
    socketIOClient.close()

    disableConnectButton(false)
}
