package fake.domain.spwstest.server;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import org.slf4j.Logger;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableWebSocket
public class Application
    implements WebSocketConfigurer
{
    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
logger.debug("XYZ123 >> registerWebSocketHandlers");
        TestWebSocketHandler testWebSocketHandler = new TestWebSocketHandler();
        WebSocketHandlerRegistration registration = registry.addHandler(testWebSocketHandler, "/wstest");
//        SockJsServiceRegistration sockJsRegistration = registration.withSockJS();
logger.info("XYZ123 << registerWebSocketHandlers");
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
