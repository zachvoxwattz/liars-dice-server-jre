// Buttons
const connectButton = document.getElementById('connection-section-button-connect')
const disconnectButton = document.getElementById('connection-section-button-disconnect')
const sendButton = document.getElementById('datagram-info-button-send')

// Output console
const outputConsole = document.getElementById('console-component')

/* Misc. components */
const connectionStatusValue = document.getElementById('connection-section-status-value')
const eventList = document.getElementById('data-section-recv-event-list')

// Input components.
const targetIPInput = document.getElementById('connection-section-input-ip')
const targetPortInput = document.getElementById('connection-section-input-port')
const sendEventInputNetcode = document.getElementById('data-section-send-input-netcode')
const sendEventInputBody = document.getElementById('data-section-send-input-body')
const recvEventInput = document.getElementById('data-section-recv-input-event')

// Binds events to crucial components.
recvEventInput.addEventListener('input', (event) => {
    recvEventInputValue = event.target.value.trim()
})

/* Constants */
const connectButtonColors = {
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
