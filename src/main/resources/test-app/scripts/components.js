/* Component variables */
let connectionStatusValue = document.getElementById('connection-section-status-value')
let connectionButtonConnect = document.getElementById('connection-section-button-connect')
let connectionButtonDisconnect = document.getElementById('connection-section-button-disconnect')
// let datagramButtonSend = document.getElementById('datagram-info-button-send')
let outputConsole = document.getElementById('console-component')

/* Constants */
const connectionButtonColors = {
    connect: {
        border: '#61e661',
        background: '#008000',
    },
    disconnect: {
        border: '#8b0000',
        background: '#f14c4c',
    },
    disabled: {
        border: '#4b4b4b',
        background: '#24292D',
    }
}

const connectionStatusColors = {
    IDLE: '#808080',
    PENDING: '#fbff00',
    ACTIVE: '#61e661',
    ERROR: '#f14c4c'
}
