package game;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/* info from:
 * https://www.codota.com/code/java/methods/javax.websocket.server.HandshakeRequest/getHttpSession
 */
public class GameSocketConfig extends ServerEndpointConfig.Configurator  {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        sec.getUserProperties().put("httpSession", httpSession);
    }
}
