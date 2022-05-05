package boco.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class BocoSocketTest {
    private BocoSocket socket;
    private Session session;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setup() {
        socket = new BocoSocket();
        session = mock(Session.class);
        lenient().when(session.getBasicRemote()).thenReturn(mock(RemoteEndpoint.Basic.class));
        lenient().when(session.getAsyncRemote()).thenReturn(mock(RemoteEndpoint.Async.class));
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void stepDown(){
        for (BocoSocket bs:BocoSocket.getWebSockets()) {
            BocoSocket.getWebSockets().remove(bs);
        }
        for (int i = 0; i < BocoSocket.getSessionPool().size(); i++) {
            BocoSocket.getSessionPool().remove(i+"");
        }
    }

    @Test
    void onOpen(){
        int originalPoolSize = BocoSocket.getSessionPool().size();
        int originalSocketsSize = BocoSocket.getWebSockets().size();

        socket.onOpen(session, "1");
        assertEquals("1", socket.getSessionId("1"));
        assertEquals(session, socket.getSession());
        assertEquals("[websocket message] has new connections, total unique: 1", outputStreamCaptor.toString()
                .trim());

        Map<String, List<Session>> pool = BocoSocket.getSessionPool();
        CopyOnWriteArraySet<BocoSocket> sockets = BocoSocket.getWebSockets();

        assertTrue(pool.size()>originalPoolSize);
        assertTrue(pool.get("1").size()>0);
        assertTrue(sockets.size()>originalSocketsSize);
    }

    @Test
    void closeSession() throws IOException {
        socket.onOpen(session, "1");
        session.close();
        socket.closeSession();
        assertFalse(BocoSocket.getSessionPool().get("1").contains(session));
        assertEquals("[websocket message] has new connections, total unique: 1\n" +
                "[websocket message] disconnected, total unique: 0", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void onMessage() throws IOException {
        socket.onOpen(session, "1");

        session.getAsyncRemote().sendText("async");
        session.getBasicRemote().sendText("basic");
        socket.onMessage("actual output, not mocked");
        assertEquals("[websocket message] has new connections, total unique: 1\n" +
                "[websocket message] receives client message: actual output, not mocked", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void sendAllMessage() throws IOException {
        socket.onOpen(session, "1");
        session.getAsyncRemote().sendText("async");

        socket.sendAllMessage("message");
        socket.onMessage("actual output, not mocked");
        assertEquals("[websocket message] has new connections, total unique: 1\n" +
                "[websocket message] broadcast message:message\n" +
                "[websocket message] receives client message: actual output, not mocked", outputStreamCaptor.toString()
                .trim());

    }

    @Test
    void sendOneMessage() {
        Session otherSession = mock(Session.class);
        socket.onOpen(session, "1");
        socket.onOpen(otherSession, "2");

        socket.sendOneMessage("1", "message");
        socket.onMessage("actual output, not mocked");
        assertEquals("[websocket message] has new connections, total unique: 1\n" +
                "[websocket message] has new connections, total unique: 1\n" +
                "[websocket message] receives client message: actual output, not mocked", outputStreamCaptor.toString()
                .trim());
    }


    @Test
    void getSessionId() {
        socket.onOpen(session, "1");
        assertEquals("1", socket.getSessionId("1"));
    }

    @Test
    void getSession() {
        socket.onOpen(session, "1");
        assertEquals(session, socket.getSession());
    }

    @Test
    void getWebSockets() {
        assertTrue(BocoSocket.getWebSockets().getClass().equals(CopyOnWriteArraySet.class));
        BocoSocket.getWebSockets().contains(socket);
    }

    @Test
    void getSessionPool() {
        assertTrue(BocoSocket.getSessionPool() != null);
        socket.onOpen(session, "1");
        assertTrue(BocoSocket.getSessionPool().get("1").contains(session));

    }


}