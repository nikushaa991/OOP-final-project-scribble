var leaderBoard;
var modal;
var wordsArray;
var webSocket;
window.onload = function () {
    modal = document.getElementById("chooseW");
    //TODO AFTER: make this an actual link, instead of localhost.
    webSocket = new WebSocket("ws://localhost:8080/FINAL_PROJECT_war_exploded/WS");
    var echoText = document.getElementById("echoText");
    leaderBoard = document.getElementById("leaderBoard");
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
        if (message.data.startsWith("L")) {
            var coordinates = message.data.split(",");
            context.lineTo(coordinates[1], coordinates[2]);
            context.stroke();
        } else if (message.data.startsWith("CLEAR,")) {
            clear();
        } else if (message.data.startsWith("T")) {
            var paint = message.data.split(",");
            context.strokeStyle = paint[1];
        } else if (message.data.startsWith("W")) {
            var width = message.data.split(",");
            context.lineWidth = width[1];
        } else if (message.data.startsWith("B")) {
            var coordinates = message.data.split(",");
            context.beginPath();
            context.moveTo(coordinates[1], coordinates[2]);
        } else if (message.data.startsWith("N")) {
            isArtist = false;
            context.clearRect(0, 0, canvas.width, canvas.height);
        } else if (message.data.startsWith("P")) {
            isArtist = true;
        } else if (message.data.startsWith("S")) {
            var ls = message.data.substr(2) + '\n';
            var sp = ls.split(" ");
            sp.sort(function (b, c) {
                var w1 = b.split("-")[1];
                var w2 = c.split("-")[1];
                if (parseInt(w1) > parseInt(w2)) {
                    return -1;
                }
                if (parseInt(w2) > parseInt(w1)) {
                    return 1;
                }
                return 0;
            });
            //got sorted array now just show it;
            displayLeaderBoard(sp);
        } else if (message.data.startsWith("A")) {
            var ls = message.data.substr(2) + '\n';
            var sp = ls.split(" ");
            sp = sp.slice(1, sp.length - 1);
            chooseWordDisplay(sp);
        } else if (message.data != "") {
            echoText.value += message.data.substr(2) + '\n';
        }
    }

    function chooseWordDisplay(posWords) { //TODO: make this disappear after 5 seconds.
        var posW1 = document.getElementById("posWord1");
        var posW2 = document.getElementById("posWord2");
        var posW3 = document.getElementById("posWord3");
        posW1.textContent = posWords[0];
        posW2.textContent = posWords[1];
        posW3.textContent = posWords[2];
        modal.style.display = "block";
        wordsArray = posWords;
        console.log(wordsArray);
    }


    function displayLeaderBoard(chart) {
        leaderBoard.value = "";
        for (let i = 0; i < chart.length - 1; i++) {
            var place = i + 1;
            leaderBoard.value += place + ". " + chart[i] + "\n";
        }
        wordsArray = null;
        modal.style.display = "none";
    }

    // Handle Colors
    var colors = document.getElementsByClassName('colors')[0];

    colors.addEventListener('click', function (event) {
        context.strokeStyle = event.target.value || 'black';
        webSocket.send("T," + context.strokeStyle);
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
        if (isArtist) {
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
        if (isArtist && (mouseX < 3 || mouseX > 797 || mouseY < 3 || mouseY > 447)) {
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
        var bounds = canvas.getBoundingClientRect();
        var root = document.documentElement;

        mouseY = event.clientY - bounds.top - root.scrollTop;
        mouseX = event.clientX - bounds.left - root.scrollLeft;
    }

    // Handle Clear Button
    //TODO: only painter must be able to use this, send this action to server and handle it.

    clearButton.addEventListener('click', function () {
        if (isArtist) {
            clear();
            webSocket.send("CLEAR,")
        }
    });

    function clear() {
        context.clearRect(0, 0, canvas.width, canvas.height);
    }

    chatInput.addEventListener("keyup", function (event) {
        if (event.key === 'Enter') {
            sendClicked();
        }
    });
    chatButton.onclick = function () {
        sendClicked()
    };

    function sendClicked() {
        var text = chatInput.value.trim();
        if (text !== "") {
            webSocket.send("C," + text);
        }
        chatInput.value = "";
        echoText.scrollTop = echoText.scrollHeight;
    }
};

function wordChoosen(index) {
    var choosenWord = wordsArray[index];

    console.log(choosenWord);
    modal.style.display = "none";
    webSocket.send("A," + choosenWord);
}
