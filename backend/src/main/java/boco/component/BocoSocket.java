package boco.component;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Notification websocket for the BocoWebsite
 */
@Component
@ServerEndpoint("/websocket/{userId}")
public class BocoSocket {
    private String sessionId;
    private Session session;
    private static CopyOnWriteArraySet<BocoSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, List<Session>> sessionPool = new HashMap<String, List<Session>>();

    /**
     * On open.
     *
     * @param session current session
     * @param userId  the user id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId){
        this.sessionId = userId;
        this.session = session;
        if (sessionPool.get(userId)==null){
            List<Session> sList = new ArrayList<>();
            sList.add(session);
            sessionPool.put(userId, sList);
        }else {
            sessionPool.get(userId).add(session);
        }
        webSockets.add(this);
        System.out.println ("[websocket message] has new connections, total unique: "+webSockets.size());
    }

    /**
     * End session
     */
    @OnClose
    public void closeSession(){
        sessionPool.get(sessionId).remove(session);
        webSockets.remove(this);
        System. out. println ("[websocket message] disconnected, total unique: "+webSockets.size());
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
        List<Session> sessionList = sessionPool.get(userId);
        for (Session session:sessionList) {
            if (session != null){
                try {
                    session.getAsyncRemote().sendText(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
