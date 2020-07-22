<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Game</title>
    <link rel="stylesheet" href="styles/game.css">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">
</head>

<body>
<div id="header">
    <img id="xvliki" src="xvliki.png">
</div>

<div id="login">
    <div>
        <label for="leaderBoard"></label><textarea disabled class="sideChart" id="leaderBoard" rows="5"
                                                   cols="20"></textarea>
    </div>
    <div class="bottom-block">
        <div class="colors">
            <button type="button" value="#000000"></button>
            <button type="button" value="#666666"></button>
            <button type="button" value="#0000ff"></button>
            <button type="button" value="#0fffff"></button>
            <button type="button" value="#ffcc00"></button>
            <button type="button" value="#00ff00"></button>
            <button type="button" value="#f00000"></button>
            <button type="button" value="#ff9933"></button>
            <button type="button" value="#663300"></button>
            <button type="button" value="#aa0fff"></button>
            <button type="button" value="#ff66ff"></button>
            <button type="button" value="#ffffff"></button>
        </div>
        <div class="brushes">
            <button type="button" value="2"></button>
            <button type="button" value="4"></button>
            <button type="button" value="6"></button>
            <button type="button" value="8"></button>
            <button type="button" value="10"></button>
        </div>
        <div class="buttons">
            <button id="clear" class=bttn type="button">Clear</button>
        </div>
    </div>
    <canvas id="paint-canvas"></canvas>
    <div class="right-block" id="right-block">
        <div id="chat"></div>
    </div>
    <div class="textInputContainer">
        <label for="textInput"></label><textarea id="textInput"></textarea>
    </div>
    <div id="chooseW" class="chooseWord">
        <div class="chooseWordContent">
            <button id="posWord1" class="posWord" onclick="wordChosen(0)"></button>
            <button id="posWord2" class="posWord" onclick="wordChosen(1)"></button>
            <button id="posWord3" class="posWord" onclick="wordChosen(2)"></button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="scripts/game.js"></script>

</html>