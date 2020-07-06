package game;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class asd {
    private static ConcurrentHashMap<Integer, Session> map;
    public asd(ConcurrentHashMap<Integer, Session> map){
     this.map = map;
    }

    public static void MISHVELET() throws IOException {
        for(Session s : map.values())
            s.getBasicRemote().sendText("NEW SESSION CONNECTED\n");
    }
}
