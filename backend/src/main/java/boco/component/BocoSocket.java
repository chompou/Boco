package boco.component;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Notification websocket for the BocoWebsite
 */
@Component
@ServerEndpoint("/websocket/{userId}")
public class BocoSocket {
    private Session session;
    private static CopyOnWriteArraySet<BocoSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();

    /**
     * On open.
     *
     * @param session current session
     * @param userId  the user id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId){
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userId, session);
        System.out.println ("[websocket message] has new connections, total: "+webSockets.size());
    }

    /**
     * End session
     */
    @OnClose
    public void closeSession(){
        webSockets.remove(this);
        System. out. println ("[websocket message] disconnected, total: "+webSockets.size());
    }

    /**
     * On message prints message on server.
     *
     * @param message message
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println ("[websocket message] receives client message: "+message);
    }

    /**
     * Send message to every active session
     *
     * @param message the message
     */
    public void sendAllMessage(String message){
        for (BocoSocket socket:webSockets){
            System.out.println ("[websocket message] broadcast message:"+message);
            try {
                socket.session.getAsyncRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Send message to specific user
     *
     * @param userId  the user id
     * @param message the message
     */
    public void sendOneMessage(String userId, String message){
        Session session = sessionPool.get(userId);
        if (session != null){
            try {
                session.getAsyncRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
