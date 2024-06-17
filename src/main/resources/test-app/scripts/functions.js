/* Various states setting functions */
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
        
        default:
            connectionStatusValue.style.color = 'white'
            break
    }
    connectionStatusValue.textContent = state
}

const disableConnectButton = (disabled) => {
    if (disabled) {
        connectionButtonConnect.style.backgroundColor = connectionButtonColors.disabled.background
        connectionButtonConnect.style.borderColor = connectionButtonColors.disabled.border
    }
    else {
        connectionButtonConnect.style.backgroundColor = connectionButtonColors.connect.background
        connectionButtonConnect.style.borderColor = connectionButtonColors.connect.border
    }
    connectionButtonConnect.disabled = disabled
}

const clearConsole = () => {
    outputConsole.textContent = ''
}