var webSocket;
var echoText;
var chatInput;
window.onload = function () {
    webSocket = new WebSocket("ws://localhost:8080/FINAL_PROJECT_war_exploded/WS");
    echoText = document.getElementById("echoText");
    echoText.value = "";

    chatInput = document.getElementById("textInput");
    var chatButton = document.getElementById("textInputButton");

    var canvas = document.getElementById("paint-canvas");
    var context = canvas.getContext("2d");
    var boundings = canvas.getBoundingClientRect();

    // Specifications
    var mouseX = 0;
    var mouseY = 0;
    context.strokeStyle = 'black'; // initial brush color
    context.lineWidth = 1; // initial brush width
    var isDrawing = false;
    var isArtist = false;


    webSocket.onopen = function (message) {
        wsOpen(message);
    };
    webSocket.onclose = function (message) {
        wsClose(message);
    };
    webSocket.onerror = function (message) {
        wsError(message);
    };
    //RECEIVE MESSAGES FROM SERVER ON THIS METHOD
    webSocket.onmessage = function (message) {
        wsGetMessage(message);
    };

    //SEND MESSAGES TO SERVER USING SEND

    function wsOpen(message) {
        echoText.value += "Connected ... \n";
    }

    function wsCloseConnection() {
        webSocket.close();
    }

    function wsClose(message) {
        echoText.value += "Disconnect ... \n";
    }

    function wsError(message) {
        echoText.value += "Error ... \n";
    }

    function wsGetMessage(message) {
        //TODO:
        //ONLY ARTIST CAN DRAW
        //ARTIST CANT COMMUNICATE IN CHAT
        //HANDLE BASED ON SERVER REPLY
        //CHAT, GAME ETC
        if(message.data.startsWith("B"))
        {
            var coordinates = message.data.split(",");
            context.beginPath();
            context.moveTo(coordinates[1], coordinates[2]);
        }
        else if(message.data.startsWith("L"))
        {
            var coordinates = message.data.split(",");
            context.lineTo(coordinates[1], coordinates[2]);
            context.stroke();
        }
        else if(message.data.startsWith("N"))
        {
            isArtist = false;
            context.clearRect(0, 0, canvas.width, canvas.height);
        }
        else if(message.data.startsWith("P"))
        {
            isArtist = true;
        }
        else if(message.data != "")
        {
            echoText.value += message.data.substr(2) + '\n';
        }
    }

    // Handle Colors
    var colors = document.getElementsByClassName('colors')[0];

    colors.addEventListener('click', function (event) {
        context.strokeStyle = event.target.value || 'black';
    });

    // Handle Brushes
    var brushes = document.getElementsByClassName('brushes')[0];

    brushes.addEventListener('click', function (event) {
        context.lineWidth = event.target.value || 1;
    });

    // Mouse Down Event
    canvas.addEventListener('mousedown', function (event) {
        setMouseCoordinates(event);
        if(isArtist)
        {
            isDrawing = true;
            // Start Drawing
            webSocket.send("B," + mouseX + "," + mouseY);
            context.beginPath();
            context.moveTo(mouseX, mouseY);
        }
    });

    // Mouse Move Event
    canvas.addEventListener('mousemove', function (event) {
        setMouseCoordinates(event);

        if (isDrawing && isArtist) {
            webSocket.send("L," + mouseX + "," + mouseY);
            context.lineTo(mouseX, mouseY);
            context.stroke();
        }
    });

    // Mouse Up Event
    canvas.addEventListener('mouseup', function (event) {
        setMouseCoordinates(event);
        isDrawing = false;
    });

    // Handle Mouse Coordinates
    function setMouseCoordinates(event) {
        mouseX = event.clientX - boundings.left;
        mouseY = event.clientY - boundings.top;
    }

    // Handle Clear Button
    var clearButton = document.getElementById('clear');

    clearButton.addEventListener('click', function () {
        context.clearRect(0, 0, canvas.width, canvas.height);
    });
};

function enterPressed(e){
    var code = (e.keyCode ? e.keyCode : e.which);
    if(code == 13) {
        e.preventDefault();
        sendClicked();
    }
}
function sendClicked(){
    var text = chatInput.value;
    if(text !== "") {
        chatInput.value = "";
        webSocket.send("C," + text);
        echoText.scrollTop = echoText.scrollHeight;
    }
    else echoText.value += "ELSE\n";
}
