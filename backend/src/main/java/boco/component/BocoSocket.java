package boco.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(BocoSocket.class);

    /**
     * Adds new session to session pool
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
        logger.info ("[websocket message] has new connections, total unique: "+webSockets.size());
    }

    /**
     * End session, removes disconected session
     */
    @OnClose
    public void closeSession(){
        //ON close does not work in testing, this is not used elsewhere
        sessionPool.get(sessionId).remove(session);
        webSockets.remove(this);
        logger.info("[websocket message] disconnected, total unique: "+webSockets.size());
    }

    /**
     * On message prints message on server.
     *
     * @param message message
     */
    @OnMessage
    public void onMessage(String message){
        logger.info("[websocket message] receives client message: "+message);
    }

    /**
     * Send message to every active session
     *
     * @param message the message
     */
    public void sendAllMessage(String message){
        for (BocoSocket socket:webSockets){
            logger.info("[websocket message] broadcast message:"+message);
            try {
                //socket.session.getAsyncRemote().sendText(message);
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
        for (Session s:sessionList) {
            if (s != null){
                try {
                    s.getAsyncRemote().sendText(message);
                }catch (Exception ignored){
                }
            }
        }
    }


    public String getSessionId(String s) {
        return sessionId;
    }

    public Session getSession() {
        return session;
    }

    public static CopyOnWriteArraySet<BocoSocket> getWebSockets() {
        return webSockets;
    }

    public static Map<String, List<Session>> getSessionPool() {
        return sessionPool;
    }
}
