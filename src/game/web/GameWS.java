package game.web;

import game.classes.Game;
import home.classes.Matchmaker;
import login.classes.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/WS", configurator = GameSocketConfig.class)
public class GameWS {
    private static final ConcurrentHashMap<Session, PlayerInfo> map = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<HttpSession, Session> sessMap = new ConcurrentHashMap<>();

    @OnOpen
    synchronized public void onOpen(Session session, EndpointConfig config) throws IOException, SQLException {
        HttpSession sess = (HttpSession) config.getUserProperties().get("httpSession");
        boolean isInGame = (boolean) sess.getAttribute("INGAME");
        Game game = (Game) sess.getAttribute("GAME");
        if(!isInGame)
        {
            if(game.isOver())
            {
                session.getBasicRemote().sendText("M,You have attempted to reconnect to a finished game");
                session.getBasicRemote().sendText("M,Starting a new unranked game...");
                Matchmaker mm = (Matchmaker) sess.getServletContext().getAttribute("MATCHMAKER");
                game = mm.addToQueue();
                sess.setAttribute("GAME", game);
            }
            int id = game.registerSession(session, (User) sess.getAttribute("USER"));
            map.put(session, new PlayerInfo(sess, id, game));
            sess.setAttribute("INGAME", true);
            sessMap.put(sess, session);
        } else
        {
            Session oldSess = sessMap.get(sess);
            sessMap.put(sess, session);
            map.put(session, map.remove(oldSess));
            game.reconnect(map.get(session).getId(), session);
        }
    }

    //CLIENT TO SERVER COMMUNICATION
    @OnMessage
    synchronized public void onMessage(String message, Session session) throws IOException, SQLException {
        PlayerInfo info = map.get(session);
        if(message.startsWith("L") || message.startsWith("B") || message.startsWith("T") || message.startsWith("W") || message.startsWith("CLEAR"))
        {
            info.getGame().stroke(message, info.getId());
        } else if(message.startsWith("A"))
        {
            String word = message.substring(2);
            info.getGame().SetHiddenWord(word);
        } else info.getGame().CheckGuessFromGame(info.getId(), message.substring(2));
    }

    @OnClose
    synchronized public void onClose(Session session) {
        PlayerInfo info = map.get(session);
        info.getGame().unregister(info.getId());
        if(info.getGame().isOver())
        {
            HttpSession hsess = map.get(session).getSess();
            hsess.setAttribute("INGAME", false);
            sessMap.remove(hsess);
            map.remove(session);

        }
    }

    @OnError
    synchronized public void onError(Throwable e, Session session) {
        e.printStackTrace();
        onClose(session);
    }
}
