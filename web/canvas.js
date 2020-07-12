
window.onload = function () {
    //TODO AFTER: make this an actual link, instead of localhost.
    var webSocket = new WebSocket("ws://localhost:8080/FINAL_PROJECT_war_exploded/WS");
    var echoText = document.getElementById("echoText");
    echoText.value = "";

    var chatInput = document.getElementById("textInput");
    var chatButton = document.getElementById("textInputButton");

    var canvas = document.getElementById("paint-canvas");
    var context = canvas.getContext("2d");
    var clearButton = document.getElementById('clear');

    // Specifications
    var mouseX = 0;
    var mouseY = 0;
    context.strokeStyle = 'black'; // initial brush color
    context.lineWidth = 1; // initial brush width
    context.filter = "url(#crisp)";
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
        //TODO: handle score updating.
        if(message.data.startsWith("T")){
            var paint = message.data.split(",");
            context.strokeStyle = paint[1];
        }
        else if (message.data.startsWith("W")){
            var width = message.data.split(",");
            context.lineWidth = width[1];
        }
        else if(message.data.startsWith("B"))
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
        webSocket.send("T," +  context.strokeStyle);
    });

    // Handle Brushes
    var brushes = document.getElementsByClassName('brushes')[0];

    brushes.addEventListener('click', function (event) {
        context.lineWidth = event.target.value || 1;
        webSocket.send("W," + context.lineWidth);
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
        if(isArtist && (mouseX < 3 || mouseX > 797 || mouseY < 3 || mouseY > 447))
        {
            webSocket.send("B," + mouseX + "," + mouseY);
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
        var boundings = canvas.getBoundingClientRect();
        var root = document.documentElement;

        mouseY = event.clientY - boundings.top - root.scrollTop;
        mouseX = event.clientX - boundings.left - root.scrollLeft;
    }

    // Handle Clear Button
    //TODO: only painter must be able to use this, send this action to server and handle it.

    clearButton.addEventListener('click', function () {
        context.clearRect(0, 0, canvas.width, canvas.height);
    });


    chatInput.addEventListener("keyup", function(event) {
        if (event.key === 'Enter') {
            sendClicked();
        }
    });
    chatButton.onclick = function(){sendClicked()};
    function sendClicked(){
        var text = chatInput.value.trim();
        if(text !== "") {
            webSocket.send("C," + text);
        }
        chatInput.value = "";
        echoText.scrollTop = echoText.scrollHeight;
    }
};
