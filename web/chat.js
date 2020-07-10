
const echoText = document.getElementById("echoText");
const chatInput = document.getElementById("textInput");
var webSocket = new WebSocket("ws://localhost:8080/FINAL_PROJECT_war_exploded/WS");
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
    console.log("Connected chat ... \n");
}

function wsCloseConnection() {
    webSocket.close();
}

function wsClose(message) {
    console.log("Disconnect chat ... \n");
}

function wsError(message) {
    console.log("Error chat ... \n");
}

function wsGetMessage(message) {
    //TODO:
    //ONLY ARTIST CAN DRAW
    //ARTIST CANT COMMUNICATE IN CHAT
    //HANDLE BASED ON SERVER REPLY
    //CHAT, GAME ETC
    // echoText.value += "Message received from to the server : " + message.data + "\n";
}

function enterPressed(e) {
    var code = (e.keyCode ? e.keyCode : e.which);
    if(code == 13) {
        e.preventDefault();
        sendClicked()
    }
}
function sendClicked(){
    var text = chatInput.value;
    if(text != "") {
        chatInput.value = "";
        echoText.value += text + "\n";
        echoText.scrollTop = echoText.scrollHeight;
        text = "chat " + text;
        webSocket.send(text);
    }
}



