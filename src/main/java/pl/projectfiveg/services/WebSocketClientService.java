package pl.projectfiveg.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import pl.projectfiveg.configuration.MyStompSessionHandler;
import pl.projectfiveg.models.ChatMessage;
import pl.projectfiveg.models.DeviceStatusMessage;
import pl.projectfiveg.models.UserMessage;

import java.util.concurrent.ExecutionException;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketClientService {

    private final static String USER_CONNECT = "/userws";
    private final static String DEVICE_CONNECT = "/devicews";
    private final static String USER_SEND = "/app/userws/user_";
    private final static String DEVICE_SEND = "/app/devicews/device_";
    private final static String GLOBAL_SEND = "/app/devicews/global";
    @Value("${Admin-token}")
    private String TOKEN;
    @Value("${application-url-ws}")
    private String WS_URL;

    public WebSocketClient webSocketDevice(ChatMessage message , String uuid) throws ExecutionException, InterruptedException {
        return prepareClient(WS_URL + DEVICE_CONNECT , DEVICE_SEND + uuid , message);
    }

    public WebSocketClient webSocketUser(UserMessage message , Long id) throws ExecutionException, InterruptedException {
        return prepareClient(WS_URL + USER_CONNECT , USER_SEND + id , message);
    }

    public WebSocketClient webSocketGlobal(DeviceStatusMessage message) throws ExecutionException, InterruptedException {
        return prepareClient(WS_URL + USER_CONNECT , GLOBAL_SEND , message);
    }

    private WebSocketClient prepareClient(String url , String sendUrl , ChatMessage message) throws ExecutionException, InterruptedException {
        final WebSocketClient client = new StandardWebSocketClient();
        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connect(url , new WebSocketHttpHeaders() , prepareStompHeaders() , new MyStompSessionHandler()).get().send(sendUrl , message);
        return client;
    }

    private WebSocketClient prepareClient(String url , String sendUrl , DeviceStatusMessage message) throws ExecutionException, InterruptedException {
        final WebSocketClient client = new StandardWebSocketClient();
        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connect(url , new WebSocketHttpHeaders() , prepareStompHeaders() , new MyStompSessionHandler()).get().send(sendUrl , message);
        return client;
    }

    private WebSocketClient prepareClient(String url , String sendUrl , UserMessage message) throws ExecutionException, InterruptedException {
        final WebSocketClient client = new StandardWebSocketClient();
        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connect(url , new WebSocketHttpHeaders() , prepareStompHeaders() , new MyStompSessionHandler()).get().send(sendUrl , message);
        return client;
    }

    private StompHeaders prepareStompHeaders() {
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("Authorization" , TOKEN);
        return stompHeaders;
    }

}

