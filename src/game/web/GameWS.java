package game.web;

import game.classes.Game;
import login.classes.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/WS", configurator = GameSocketConfig.class)
public class GameWS {
    private static ConcurrentHashMap<Session, PlayerInfo> map = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<HttpSession, Session> sessMap = new ConcurrentHashMap<>();

    @OnOpen
    synchronized public void onOpen(Session session, EndpointConfig config) throws IOException {
        HttpSession sess = (HttpSession) config.getUserProperties().get("httpSession");
        boolean isInGame = (boolean) sess.getAttribute("INGAME");
        Game game = (Game) sess.getAttribute("GAME");
        if(!isInGame)
        {
            int id = game.registerSession(session, (User) sess.getAttribute("USER"));
            map.put(session, new PlayerInfo(sess, id, game));
            sess.setAttribute("INGAME", true);
            sessMap.put(sess, session);
        } else
        {
            Session oldSess = sessMap.get(sess);
            sessMap.remove(sess);
            sessMap.put(sess, session);
            map.put(session, map.remove(oldSess));
            game.reconnect(map.get(session).getId(), session);
        }
    }


    @OnClose
    synchronized public void onClose(Session session) {
        PlayerInfo info = map.get(session);
        info.getGame().unregister(info.getId());
        if(info.getGame().getActivePlayerCount() == 0)
        {
            for(Session sess : map.keySet())
            {
                if(map.get(sess).getGame().equals(info.getGame()))
                {
                    HttpSession hsess = map.get(sess).getSess();
                    hsess.setAttribute("INGAME", false);
                    map.remove(sess);
                }
            }
        }
    }

    //CLIENT TO SERVER COMMUNICATION
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        PlayerInfo info = map.get(session);
        if(message.startsWith("L") || message.startsWith("B") || message.startsWith("T") || message.startsWith("W"))
        {
            info.getGame().stroke(message, info.getId());
        } else if(message.startsWith("A"))
        {
            String word = message.substring(2);
            info.getGame().SetHiddenWord(word);
        } else info.getGame().CheckGuessFromGame(info.getId(), message.substring(2));

        //TODO: return chosen word from artist
        //TODO: maybe echo something useful back to client?
        //TODO: handle clear canvas action
        //TODO: handle stroke color and size.
    }

    //TODO: unregister player and session on error
    @OnError
     synchronized public void onError(Throwable e, Session session) {
        e.printStackTrace();
        PlayerInfo info = map.get(session);
        info.getGame().unregister(info.getId());
        if(info.getGame().getActivePlayerCount() == 0)
        {
            for(Session sess : map.keySet())
            {
                if(map.get(sess).getGame().equals(info.getGame()))
                {
                    HttpSession hsess = map.get(sess).getSess();
                    hsess.setAttribute("INGAME", false);
                    map.remove(sess);
                }
            }
        }
    }

}
