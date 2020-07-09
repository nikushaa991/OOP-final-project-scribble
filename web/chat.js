
// window.onload = function () {
//     var webSocket = new WebSocket("ws://localhost:8080/FINAL_PROJECT_war_exploded/WS");
//
//
//     webSocket.onopen = function (message) {
//         wsOpen(message);
//     };
//     webSocket.onclose = function (message) {
//         wsClose(message);
//     };
//     webSocket.onerror = function (message) {
//         wsError(message);
//     };
//
//     //RECEIVE MESSAGES FROM SERVER ON THIS METHOD
//     webSocket.onmessage = function (message) {
//         wsGetMessage(message);
//     };
//
//     //SEND MESSAGES TO SERVER USING SEND
//
//     function wsOpen(message) {
//     }
//
//     function wsCloseConnection() {
//         webSocket.close();
//     }
//
//     function wsClose(message) {
//     }
//
//     function wsError(message) {
//     }
//
//     function wsGetMessage(message) {
//         //TODO:
//         //ONLY ARTIST CAN DRAW
//         //ARTIST CANT COMMUNICATE IN CHAT
//         //HANDLE BASED ON SERVER REPLY
//         //CHAT, GAME ETC
//     }
//
//
// }
const echoText = document.getElementById("echoText");
const chatInput = document.getElementById("textInput");
// while(true){
//     if (document.readyState === 'complete'){
//
//         break;
//     }
// }
function enterPressed(e) {
    var code = (e.keyCode ? e.keyCode : e.which);
    if(code == 13) {
        e.preventDefault();
        sendClicked()
    }
}

function sendClicked(){
    console.log("WTF1");
    var text = chatInput.value;
    if(text != "") {
        chatInput.value = "";
        echoText.value += text + "\n";
        echoText.scrollTop = echoText.scrollHeight;
    }
}



