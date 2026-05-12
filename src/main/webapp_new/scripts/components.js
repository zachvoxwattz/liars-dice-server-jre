// Connection Info - Main Section
const ElementInputIP = document.getElementById('input_ip');
const ElementInputPort = document.getElementById('input_port');
const ElementStatusIndicator = document.getElementById('comp_status_indicator');
const ElementBtnConnect = document.getElementById('btn_connect');
const ElementBtnDisconnect = document.getElementById('btn_disconnect');

// Connection Info - Misc Section
const ElementInputAuthToken = document.getElementById('input_authtoken');
const ElementInputNamespace = document.getElementById('input_namespace');

// Send/Recv Options - Send Section
const ElementInputNetCode = document.getElementById('input_netcode');
const ElementInputNetBody = document.getElementById('input_netbody');
const ElementBtnSendNetCode = document.getElementById('btn_sendnetcode');
const ElementBtnClearNetBody = document.getElementById('btn_clearnetbody');

// Send/Recv Options - Recv Section
const ElementInputRecvEvent = document.getElementById('input_recv_event');
const ElementBtnAddNewEvent = document.getElementById('btn_addnewevent');
const ElementDisplayEventList = document.getElementById('display_eventlist');

// Console Output Section
const ElementConsole = document.getElementById('display_console');

// Colors for the status
const ClientStatusColors = {
    KICKED: '#FF0000',
    DISCONNECTED: '#FFFFFF',
    IDLE: '#808080',
    PENDING: '#fbff00',
    ACTIVE: '#61e661',
    ERROR: '#f14c4c'
}

// Default connection values
const DefaultTargetIP = '127.0.0.1' // If none or invalid inputs are given, default localhost IP is used.
const DefaultTargetPort = 11912 // The same applies to the target server port.

// RegEx for enforcing correct IP format.
const IPv4RegEx = /^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\.(?!$)|$)){4}$/

// RegEx for enforcing correct Port format.
const PortRegEx = /^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$/

// Tracks the current value of the recv event input field.
ElementInputRecvEvent.addEventListener('input', (event) => {
    recvEventInputValue = event.target.value.trim()
})