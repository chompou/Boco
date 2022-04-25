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

@Component
@ServerEndpoint("/websocket/{userId}")
public class BocoSocket {
    private Session session;
    private CopyOnWriteArraySet<BocoSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId){
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userId, session);
        System.out.println ("[websocket message] has new connections, total: "+webSockets.size());
    }

    @OnClose
    public void closeSession(){
        webSockets.remove(this);
        System. out. println ("[websocket message] disconnected, total: "+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println ("[websocket message] receives client message: "+message);
    }

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
