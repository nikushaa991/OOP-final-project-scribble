package game;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/WS")
public class GameWS {
    private static ConcurrentHashMap<Integer, Session> map = new ConcurrentHashMap<>();
    private static int order = 0;
    private static asd AE = new asd(map);
    @OnOpen
    public void onOpen(Session session) throws IOException {
        //get game instance
        //id = game.registerSocket(this)
        map.put(order, session);
        order++;
        AE.MISHVELET();
        System.out.println("Open Connection ...");

    }

    @OnClose
    public void onClose(){
        System.out.println("Close Connection ...");
    }

    @OnMessage
    public String onMessage(String message){
        //BOTH:
        //get artist
        //get score
        //

        //ARTIST:
        //get possible word list
        //return chosen word
        //send stroke to server

        //OTHERS:
        //check guess + send message to chat
        //

        //if message == coordinate
        //{
        //
        //}

        System.out.println("Message from the client: " + message);
        String echoMsg = "Echo from the server : " + message;
        return echoMsg;
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }

}
