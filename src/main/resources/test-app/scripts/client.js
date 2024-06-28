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
        clientDisconnect()
        setConnectionStatus('error')
        disableConnectButton(false)
    })

    socketIOClient.on('disconnect', () => {
        clientDisconnect()
        setConnectionStatus('disconnected')
        disableConnectButton(false)
    })

    registeredEvents.forEach((entry) => { addEventListener(entry) })

    setConnectionStatus('pending')
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
}

const selfDisconnect = () => {
    if (socketIOClient === undefined || !socketIOClient.active) {
        errorToConsole('Can not disconnect as no existing connection found!')
        return
    }

    socketIOClient.off()
    socketIOClient.close()

    disableConnectButton(false)
    setConnectionStatus('idle')
}
