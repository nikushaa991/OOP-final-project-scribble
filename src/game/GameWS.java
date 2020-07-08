package game;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/WS", configurator = GameSocketConfig.class)
public class GameWS {
    private static int cnt = 0;
    private Game game;
    private int id;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException, InterruptedException {
        System.out.println("Open Connection ...");
        HttpSession sess = (HttpSession) config.getUserProperties().get("httpSession");
        game = (Game) sess.getAttribute("GAME");
        game.registerSession(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("Close Connection ...");
    }

    //CLIENT TO SERVER COMMUNICATION
    @OnMessage
    public String onMessage(String message) throws IOException {
        System.out.println("Message from the client: " + message);
        String res = "Echo from the server : " + message;
        if(message.startsWith("START"))
            game.stroke(message);
        else game.stroke(message);
        //ARTIST:
        //return chosen word
        //send stroke to server


        //GUESSERS:
        //check guess + send message to chat
        return res;
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

}