// Connection Info - Main Section
const inputIP = document.getElementById('input_ip');
const inputPort = document.getElementById('input_port');
const statusIndicator = document.getElementById('comp_status_indicator');
const btnConnect = document.getElementById('btn_connect');
const btnDisconnect = document.getElementById('btn_disconnect');

// Connection Info - Misc Section
const inputAuthToken = document.getElementById('input_authtoken');
const inputNamespace = document.getElementById('input_namespace');

// Send/Recv Options - Send Section
const inputNetCode = document.getElementById('input_netcode');
const inputNetBody = document.getElementById('input_netbody');
const btnSendNetCode = document.getElementById('btn_sendnetcode');
const btnClearNetBody = document.getElementById('btn_clearnetbody');

// Send/Recv Options - Recv Section
const inputRecvEvent = document.getElementById('input_recv_event');
const btnAddNewEvent = document.getElementById('btn_addnewevent');
const displayEventList = document.getElementById('display_eventlist');

// Console Output Section
const displayConsole = document.getElementById('display_console');

// Colors for the status
const ClientStatusColors = {
    KICKED: '#FF0000',
    DISCONNECTED: '#FFFFFF',
    IDLE: '#808080',
    PENDING: '#fbff00',
    ACTIVE: '#61e661',
    ERROR: '#f14c4c'
}