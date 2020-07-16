<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Game</title>
        <link rel="stylesheet" href="styles/game.css">
    </head>

    <body>
        <div id="header">
            OOP
        </div>

        <div id="login">
            <div>
                <textarea disabled class="sideChart" id="leaderBoard" rows="5" cols="20"></textarea>
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
                    <button type="button" value="1"></button>
                    <button type="button" value="2"></button>
                    <button type="button" value="3"></button>
                    <button type="button" value="4"></button>
                    <button type="button" value="5"></button>
                </div>
                <div class="buttons">
                    <button id="clear" class=bttn type="button">Clear</button>
                </div>
            </div>
            <canvas id="paint-canvas"></canvas>
            <div class="chat">
                <textarea disabled id="echoText" class="sideChart" rows="5" cols="30"></textarea>
                <div class="textInputContainer">
                    <textarea id="textInput"></textarea>
                </div>
            </div>
            <div id="chooseW" class="chooseWord">
                <div class="chooseWordContent">
                    <button id="posWord1" class="posWord" onclick="wordChoosen(0)"></button>
                    <button id="posWord2" class="posWord" onclick="wordChoosen(1)"></button>
                    <button id="posWord3" class="posWord" onclick="wordChoosen(2)"></button>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript" src="scripts/game.js"></script>

    </html>