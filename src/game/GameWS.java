package game;

import login.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/WS")
public class GameWS {
    private static int cnt = 0;
    private Game game;
    private int id;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        System.out.println("Open Connection ...");
        //game = get game instance from http session
        //id = game.registerSession(session)

    }

    @OnClose
    public void onClose() {
        System.out.println("Close Connection ...");
    }

    //CLIENT TO SERVER COMMUNICATION
    @OnMessage
    public String onMessage(String message) {
        System.out.println("Message from the client: " + message);
        String res = "Echo from the server : " + message;

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
