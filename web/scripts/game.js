let leaderBoard;
let modal;
let wordsArray;
let webSocket;
window.onload = function () {
    modal = document.getElementById("chooseW");
    webSocket = new WebSocket('ws://' + location.hostname + ':25565/WS');
    leaderBoard = document.getElementById("leaderBoard");

    let chatInput = document.getElementById("textInput");
    let block = document.getElementById('right-block');

    let canvas = document.getElementById("paint-canvas");
    let context = canvas.getContext("2d");
    let xScale = 0.7 * window.innerWidth;
    let upscaleRatio = 2000 / xScale;
    let yScale = 0.7 * window.innerHeight;
    context.canvas.width = xScale;
    context.canvas.height = yScale;
    let clearButton = document.getElementById('clear');

    // Specifications
    let mouseX = 0;
    let mouseY = 0;
    context.strokeStyle = 'black'; // initial brush color
    context.lineWidth = 1; // initial brush width
    context.filter = "url(#crisp)";
    let isDrawing = false;
    let isPainter = false;


    webSocket.onopen = function (message) {
        wsOpen();
    };
    webSocket.onclose = function (message) {
        wsClose();
    };
    webSocket.onerror = function (message) {
        wsError();
    };
    //RECEIVE MESSAGES FROM SERVER ON THIS METHOD
    webSocket.onmessage = function (message) {
        wsGetMessage(message);
    };

    //SEND MESSAGES TO SERVER USING SEND

    function wsOpen() {
        addSystemMessage("CONNECTED")
    }

    function wsClose() {
        addSystemMessage("DISCONNECTED")
    }

    function wsError() {
        addSystemMessage("ERROR")
    }

    function wsGetMessage(message) {
        if (message.data.startsWith("L"))
        {  //line to
            let coordinates = message.data.split(",");
            context.lineTo(coordinates[1] / upscaleRatio, coordinates[2] / upscaleRatio);
            context.stroke();
        }
        else if (message.data.startsWith("CLEAR,"))
        {  //clear canvas
            clear();
        }
        else if (message.data.startsWith("T"))
        {  //color
            let paint = message.data.split(",");
            context.strokeStyle = paint[1];
        }
        else if (message.data.startsWith("W"))
        {  //stroke width
            let width = message.data.split(",");
            context.lineWidth = width[1];
        }
        else if (message.data.startsWith("B"))
        {  //begin line
            let coordinates = message.data.split(",");
            context.beginPath();
            context.moveTo(coordinates[1] / upscaleRatio, coordinates[2] / upscaleRatio);
        }
        else if (message.data.startsWith("N"))
        {  //new round
            isPainter = false;
            context.clearRect(0, 0, canvas.width, canvas.height);
            enableChat();
            addSystemMessage("New Round Started");
        }
        else if (message.data.startsWith("P"))
        {  //you're the painter
            isPainter = true;
        }
        else if (message.data.startsWith("D"))
        {  //disable chat
            disableChat();
        }
        else if (message.data.startsWith("S"))
        {  //score update
            let sp = message.data.substr(2).split(',');
            sp.sort(function (b, c) {
                let w1 = b.split(" - ")[1];
                let w2 = c.split(" - ")[1];
                if (parseInt(w1) > parseInt(w2))
                {
                    return - 1;
                }
                if (parseInt(w2) > parseInt(w1))
                {
                    return 1;
                }
                return 0;
            });
            displayLeaderBoard(sp);
        }
        else if (message.data.startsWith("A"))
        {  //choose word
            let ls = message.data.substr(2) + '\n';
            let sp = ls.split(" ");
            sp = sp.slice(1, sp.length - 1);
            setTimeout(function () {
                modal.style.display = "none";
            }, 5000);
            chooseWordDisplay(sp);
        }
        else if (message.data.startsWith("C"))
        {  //print user message to chat
            let data = message.data.split(','); //0 = C, 1 = color, 2 = user, 3 = text
            addUserMessage(data[1], data[2], data[3])
        }
        else if (message.data !== "")
        {  //print system message to chat
            let data = message.data.split(','); //0 = M, 1 = text
            addSystemMessage(data[1])
        }
    }

    function chooseWordDisplay(posWords) {
        let posW1 = document.getElementById("posWord1");
        let posW2 = document.getElementById("posWord2");
        let posW3 = document.getElementById("posWord3");
        posW1.textContent = posWords[0];
        posW2.textContent = posWords[1];
        posW3.textContent = posWords[2];
        modal.style.display = "block";
        wordsArray = posWords;
        console.log(wordsArray);
    }


    function displayLeaderBoard(chart) {
        leaderBoard.value = "";
        for (let i = 0; i < chart.length - 1; i ++)
        {
            let place = i + 1;
            leaderBoard.value += place + ". " + chart[i] + "\n";
        }
        wordsArray = null;
        modal.style.display = "none";
    }

    // Handle Colors
    let colors = document.getElementsByClassName('colors')[0];

    colors.addEventListener('click', function (event) {
        if (isPainter)
        {
            context.strokeStyle = event.target.value || 'black';
            webSocket.send("T," + context.strokeStyle);
        }
    });

    // Handle Brushes
    let brushes = document.getElementsByClassName('brushes')[0];

    brushes.addEventListener('click', function (event) {
        if (isPainter)
        {
            context.lineWidth = event.target.value || 1;
            webSocket.send("W," + context.lineWidth);
        }
    });

    // Mouse Down Event
    canvas.addEventListener('mousedown', function (event) {
        setMouseCoordinates(event);
        if (isPainter)
        {
            isDrawing = true;
            // Start Drawing
            webSocket.send("B," + upscaleRatio * mouseX + "," + upscaleRatio * mouseY);
            context.beginPath();
            context.moveTo(mouseX, mouseY);
        }
    });

    // Mouse Move Event
    canvas.addEventListener('mousemove', function (event) {
        setMouseCoordinates(event);
        if (isDrawing && isPainter)
        {
            webSocket.send("L," + upscaleRatio * mouseX + "," + upscaleRatio * mouseY);
            context.lineTo(mouseX, mouseY);
            context.stroke();
        }
        if (isPainter && (mouseX < 3 || mouseX > 797 || mouseY < 3 || mouseY > 447))
        {
            webSocket.send("B," + upscaleRatio * mouseX + "," + upscaleRatio * mouseY);
            context.beginPath();
            context.moveTo(mouseX, mouseY);
        }
    });

    // Mouse Up Event
    canvas.addEventListener('mouseup', function (event) {
        setMouseCoordinates(event);
        isDrawing = false;
    });

    // Handle Mouse Coordinates
    function setMouseCoordinates(event) {
        let bounds = canvas.getBoundingClientRect();
        let root = document.documentElement;

        mouseY = event.clientY - bounds.top - root.scrollTop;
        mouseX = event.clientX - bounds.left - root.scrollLeft;
    }

    // Handle Clear Button

    clearButton.addEventListener('click', function () {
        if (isPainter)
        {
            clear();
            webSocket.send("CLEAR,")
        }
    });

    function clear() {
        context.clearRect(0, 0, canvas.width, canvas.height);
    }

    chatInput.addEventListener("keyup", function (event) {
        if (event.key === 'Enter')
        {
            sendClicked();
        }
    });

    function sendClicked() {
        let text = chatInput.value.trim();
        if (text !== "")
        {
            webSocket.send("C," + text);
        }
        chatInput.value = "";
    }

    function addUserMessage(color, username, text) {
        let div = document.createElement('div');

        div.className = 'textRow';

        div.innerHTML = `<p><span style="color:` + color + `;font-weight:bold">` + username + `</span>: ` + text + `</p>`;

        document.getElementById('chat').appendChild(div);
        block.scrollTop = block.scrollHeight;
    }

    function addSystemMessage(text) {
        let div = document.createElement('div');

        div.className = 'textRow';

        div.innerHTML = `<p><span style="color:#d50000;font-weight:bold">SYSTEM</span>: ` + text + `</p>`;

        document.getElementById('chat').appendChild(div);
        block.scrollTop = block.scrollHeight;
    }

    function disableChat() {
        chatInput.disabled = true;
        chatInput.placeholder = "You can't use chat until the next round";
    }

    function enableChat() {
        chatInput.disabled = false;
        chatInput.placeholder = "";
    }
};

function wordChosen(index) {
    let chosenWord = wordsArray[index];
    modal.style.display = "none";
    webSocket.send("A," + chosenWord);
}

