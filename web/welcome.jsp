<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Canvas</title>
    <link rel="stylesheet" href="canvas.css">
    <link rel="stylesheet" href="chat.css">
    <script type="text/javascript" src="canvas.js"></script>
</head>
<body>
<<<<<<< HEAD
<main style="width: 100%">
    <div class="leaderBoard">
        <textarea disabled class="sideChart" rows="5" cols="20"></textarea>
    </div>
=======
<%--<<<<<<< HEAD--%>
<main>
>>>>>>> 6fff7d0fefff2f90e548cc3c97beafa4e9996f87
    <div class="left-block">
        <div class="colors">
            <button type="button" value="#0000ff"></button>
            <button type="button" value="#009fff"></button>
            <button type="button" value="#0fffff"></button>
            <button type="button" value="#bfffff"></button>
            <button type="button" value="#000000"></button>
            <button type="button" value="#333333"></button>
            <button type="button" value="#666666"></button>
            <button type="button" value="#999999"></button>
            <button type="button" value="#ffcc66"></button>
            <button type="button" value="#ffcc00"></button>
            <button type="button" value="#ffff00"></button>
            <button type="button" value="#ffff99"></button>
            <button type="button" value="#003300"></button>
            <button type="button" value="#555000"></button>
            <button type="button" value="#00ff00"></button>
            <button type="button" value="#99ff99"></button>
            <button type="button" value="#f00000"></button>
            <button type="button" value="#ff6600"></button>
            <button type="button" value="#ff9933"></button>
            <button type="button" value="#f5deb3"></button>
            <button type="button" value="#330000"></button>
            <button type="button" value="#663300"></button>
            <button type="button" value="#cc6600"></button>
            <button type="button" value="#deb887"></button>
            <button type="button" value="#aa0fff"></button>
            <button type="button" value="#cc66cc"></button>
            <button type="button" value="#ff66ff"></button>
            <button type="button" value="#ff99ff"></button>
            <button type="button" value="#e8c4e8"></button>
            <button type="button" value="#ffffff"></button>
        </div>
        <div class="brushes">
            <button type="button" value="1"></button>
            <button type="button" value="2"></button>
            <button type="button" value="3"></button>
            <button type="button" value="4"></button>
            <button type="button" value="5"></button>
        </div>
        <div class="buttons">
            <button id="clear" type="button">Clear</button>
        </div>
    </div>
    <canvas id="paint-canvas" width="800" height="450"></canvas>
    <div class="chat" >
        <textarea disabled id="echoText" class="sideChart" rows="5" cols="30"></textarea>
        <div class="textInputContainer">
<<<<<<< HEAD
            <textarea id="textInput"  onkeypress="enterPressed(event)"></textarea>
            <button onclick="sendClicked()" id="textInputButton">Send</button>
=======
            <textarea id="textInput"></textarea>
            <button id="textInputButton">Send</button>
>>>>>>> 6fff7d0fefff2f90e548cc3c97beafa4e9996f87
        </div>
    </div>
</main>

</body>
</html>