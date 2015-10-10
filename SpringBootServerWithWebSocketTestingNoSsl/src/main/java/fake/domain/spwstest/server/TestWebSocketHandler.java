package fake.domain.spwstest.server;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TestWebSocketHandler
    extends TextWebSocketHandler
{
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session)
//        throws Exception
//    {
//        super.afterConnectionEstablished(session);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
//        throws Exception
//    {
//        super.afterConnectionClosed(session, status);
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
//        throws Exception
//    {
//        super.handleMessage(session, message);
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage)
        throws Exception
    {
        String receivedMessageText = textMessage.getPayload();

        session.sendMessage(new TextMessage("ECHO[" + receivedMessageText + "]"));

        session.close();
    }
}
