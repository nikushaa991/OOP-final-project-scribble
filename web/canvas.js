window.onload = function () {
    var webSocket = new WebSocket("ws://localhost:8080/FINAL_PROJECT_war_exploded/WS");
    var echoText = document.getElementById("echoText");
    echoText.value = "";
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
        var asd = message.data.toString().split(",");
        echoText.value += asd[0];
        echoText.value += asd[1];
        if(asd[0] == "START")
        {
            echoText.value += "VIWYEB PATHS\n";
            context.beginPath();
            context.moveTo(asd[1], asd[2]);
        }
        else if (asd[0] == "WORD"){
            echoText.value += asd[1];
        }
        else if(asd[0] != "ECHO")
        {
            echoText.value += "LINETO\n";
            context.lineTo(asd[0], asd[1]);
            context.stroke();
        }

        //TODO:
        //ONLY ARTIST CAN DRAW
        //ARTIST CANT COMMUNICATE IN CHAT
        //HANDLE BASED ON SERVER REPLY
        //CHAT, GAME ETC
    }

    var canvas = document.getElementById("paint-canvas");
    var context = canvas.getContext("2d");
    var boundings = canvas.getBoundingClientRect();

    // Specifications
    var mouseX = 0;
    var mouseY = 0;
    context.strokeStyle = 'black'; // initial brush color
    context.lineWidth = 1; // initial brush width
    var isDrawing = false;


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
        isDrawing = true;

        // Start Drawing
        context.beginPath();
        webSocket.send("START," + mouseX + ',' +  mouseY);
        context.moveTo(mouseX, mouseY);
    });

    // Mouse Move Event
    canvas.addEventListener('mousemove', function (event) {
        setMouseCoordinates(event);

        if (isDrawing) {
            context.lineTo(mouseX, mouseY);
            webSocket.send(mouseX + ',' + mouseY);
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