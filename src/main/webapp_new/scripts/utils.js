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
            statusIndicator.style.color = ClientStatusColors.IDLE
            break
            
        case 'PENDING':
            statusIndicator.style.color = ClientStatusColors.PENDING
            break
            
        case 'ACTIVE':
            statusIndicator.style.color = ClientStatusColors.ACTIVE
            break
            
        case 'ERROR':
            statusIndicator.style.color = ClientStatusColors.ERROR
            break

        case 'DISCONNECTED':
            statusIndicator.style.color = ClientStatusColors.DISCONNECTED
            break

        case 'KICKED':
            statusIndicator.style.color = ClientStatusColors.KICKED
            break

        default:
            statusIndicator.style.color = 'white'
            break
    }
    statusIndicator.textContent = state
}

/**
 * Appends text content to the output console.
 * @param {*} content String to be added.
 */
const logConsoleInfo = (content) => {
    var appendedContent = `INFO> ${content}`

    displayConsole.textContent.length !== 0
        ? appendedContent = `\n${appendedContent}`
        : appendedContent = `${appendedContent}`

    displayConsole.textContent += appendedContent
    displayConsole.scrollTop = displayConsole.scrollHeight
}

/**
 * Appends text content to the output console.
 * @param {*} content String to be added.
 */
const logConsoleError = (content) => {
    var appendedContent = `ERROR> ${content}`

    displayConsole.textContent.length !== 0
        ? appendedContent = `\n${appendedContent}`
        : appendedContent = `${appendedContent}`

    displayConsole.textContent += appendedContent
    displayConsole.scrollTop = displayConsole.scrollHeight
}