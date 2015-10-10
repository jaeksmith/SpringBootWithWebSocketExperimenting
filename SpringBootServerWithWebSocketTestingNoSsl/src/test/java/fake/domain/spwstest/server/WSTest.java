package fake.domain.spwstest.server;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Test;

public class WSTest
{
    private static final String serviceUrl = "ws://localhost:8080/wstest";
//    private static final String serviceUrl = "wss://dragon:8443/wstest";

    private enum State { Connecting, Connected, Disconnected, Error };

    private static class TestEndpoint
        extends Endpoint
    {
        private State state = State.Connecting;

        private TestMessageHandler testMessageHandler;

        @Override
        public void onOpen(Session session, EndpointConfig config)
        {
            testMessageHandler = new TestMessageHandler();
            session.addMessageHandler(testMessageHandler);

            setState(State.Connected);
        }

        @Override
        public void onClose(Session session, CloseReason closeReason)
        {
            setState(State.Disconnected);
        }

        @Override
        public void onError(Session session, Throwable thr)
        {
            setState(State.Error);
        }

        private synchronized void setState(State newState)
        {
            if (state == State.Error)
            {
                return;
            }

            state = newState;
            notifyAll();
        }

        public synchronized State waitTillNoLongerInState(State waitState)
            throws Exception
        {
            while (state == waitState)
            {
                wait();
            }

            return state;
        }

        public List<String> getMessages()
        {
            return testMessageHandler.getMessages();
        }
    }

    private static class TestMessageHandler
        implements MessageHandler.Whole<String>
    {
        private List<String> messages = new ArrayList<>();

        @Override
        public void onMessage(String message)
        {
            messages.add(message);
        }

        public List<String> getMessages()
        {
            return messages;
        }
    }

    @Test
    public void testHelloWorld()
        throws Exception
    {
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        TestEndpoint testEndpoint = new TestEndpoint();
        Session session = container.connectToServer(testEndpoint, clientEndpointConfig, new URI(serviceUrl));

        {
            State state = testEndpoint.waitTillNoLongerInState(State.Connecting);
            assertEquals(State.Connected, state);
        }

        String message = "Just some Test Text " + System.currentTimeMillis();
        session.getBasicRemote().sendText(message);

        {
            State state = testEndpoint.waitTillNoLongerInState(State.Connected);
            assertEquals(State.Disconnected, state);
        }

        List<String> messages = testEndpoint.getMessages();
        assertEquals(1, messages.size());
        assertEquals("ECHO[" + message + "]", messages.get(0)); 
        
//        ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
//        ClientManager client = ClientManager.createClient();
//        client.connectToServer(new Endpoint() {
        
    }
}
