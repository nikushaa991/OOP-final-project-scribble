package game;

import login.User;
import main.java.Pair;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/WS", configurator = GameSocketConfig.class)
public class GameWS {
    private static ConcurrentHashMap<Session, Pair<Game, Integer>> map = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException, InterruptedException {
        System.out.print("NEW CONNECTION\n");
        HttpSession sess = (HttpSession) config.getUserProperties().get("httpSession");
        Game game = (Game) sess.getAttribute("GAME");
        int id = game.registerSession(session, (User) sess.getAttribute("USER"));
        map.put(session, new Pair<>(game, id));
    }

    @OnClose
    public void onClose(Session session) {
        Pair<Game, Integer> p = map.get(session);
        p.getFirst().unregister(p.getSecond());
        map.remove(session);
    }

    //CLIENT TO SERVER COMMUNICATION
    @OnMessage
    public String onMessage(String message, Session session) throws IOException {
        System.out.print(message + " NEW MESSAGE\n");
        Pair<Game, Integer> p = map.get(session);
        if(message.startsWith("L") || message.startsWith("B"))
            p.getFirst().stroke(message, p.getSecond());
        else p.getFirst().CheckGuessFromGame(p.getSecond(), message.substring(2));
        //ARTIST:
        //return chosen word
        //send stroke to server


        //GUESSERS:
        //check guess + send message to chat
        return "";
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

}
