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

    socketIOClient.on('connect_error', () => {
        setConnectionStatus('ERROR')
        clientDisconnect()
    })

    socketIOClient.on('disconnect', () => {
        clientDisconnect()
    })

    setConnectionStatus('PENDING')
    socketIOClient.connect()
}

const clientDisconnect = () => {
    socketIOClient.off()
    socketIOClient.close()

    setConnectionStatus('IDLE')
    disableConnectButton(false)
}
