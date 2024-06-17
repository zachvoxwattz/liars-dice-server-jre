let console = document.getElementById('console-component')

setInterval(() => {
    console.textContent += "\nHaha"
    console.scrollTop = console.scrollHeight;
}, 1000)
