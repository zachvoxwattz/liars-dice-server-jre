// Determines whether the given string is JSON form or not.
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
 * Sets the status of client
 * @param {*} targetState Applicable values are: `IDLE`, `DISCONNECTED`, `PENDING`, `ACTIVE` and `ERROR`.
 */
const setClientStatus = (targetState) => {
    let state = targetState.toUpperCase()
    switch (state) {
        case 'IDLE':
            ElementStatusIndicator.style.color = ClientStatusColors.IDLE
            break
            
        case 'PENDING':
            ElementStatusIndicator.style.color = ClientStatusColors.PENDING
            break
            
        case 'ACTIVE':
            ElementStatusIndicator.style.color = ClientStatusColors.ACTIVE
            break
            
        case 'ERROR':
            ElementStatusIndicator.style.color = ClientStatusColors.ERROR
            break

        case 'DISCONNECTED':
            ElementStatusIndicator.style.color = ClientStatusColors.DISCONNECTED
            break

        case 'KICKED':
            ElementStatusIndicator.style.color = ClientStatusColors.KICKED
            break

        default:
            ElementStatusIndicator.style.color = 'white'
            break
    }
    ElementStatusIndicator.textContent = state
}

/**
 * Checks for the validity of the inputs before proceeding
 * @param {*} ip - The IP to connect.
 * @param {*} port - The port to connect.
 * @returns An array of error messages, empty if there is no error.
 */
const checkConnectionInput = (ip, port) => {
    let errorMessages = []

    if (!IPv4RegEx.test(ip)) {
        errorMessages.push("Invalid IPv4 format")
    }

    if (!PortRegEx.test(port)) {
        errorMessages.push("Invalid network port format")
    }

    let portInt = parseInt(port)
    if (portInt < 0) {
        errorMessages.push("Invalid port value. It cannot be less than 0")
    }
    else if (portInt > 65535) {
        errorMessages.push("Invalid port value. It cannot be greater than 65535!")
    }

    return errorMessages
}

/**
 * Toggles the status of the connect button
 * @param {*} element - The HTMLElement of the button.
 * @param {*} value - `true` or `false` only
 */
const setButtonDisabledState = (element, value) => {
    element.disabled = value
}

const getConsoleTime = () => {
    let cts = new Date()
    let formattedString = `${cts.getDate()}/${cts.getMonth() + 1}/${cts.getFullYear()} ${cts.getHours()}:${cts.getMinutes()}:${cts.getSeconds()}:${cts.getMilliseconds()}`

    return formattedString
}

/**
 * Appends text content to the output console.
 * @param {*} content String to be added.
 */
const logConsoleInfo = (content) => {
    var appendedContent = `[${getConsoleTime()}] INFO> ${content}`

    ElementConsole.textContent.length !== 0
        ? appendedContent = `\n${appendedContent}`
        : appendedContent = `${appendedContent}`

    ElementConsole.textContent += appendedContent
    ElementConsole.scrollTop = ElementConsole.scrollHeight
}

/**
 * Appends text content to the output console.
 * @param {*} content String to be added.
 */
const logConsoleError = (content) => {
    var appendedContent = `[${getConsoleTime()}] ERROR> ${content}`

    ElementConsole.textContent.length !== 0
        ? appendedContent = `\n${appendedContent}`
        : appendedContent = `${appendedContent}`

    ElementConsole.textContent += appendedContent
    ElementConsole.scrollTop = ElementConsole.scrollHeight
}

/**
 * Clears the contents of the console.
 */
const clearConsole = () => {
    ElementConsole.textContent = ''
}