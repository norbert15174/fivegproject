package pl.projectfiveg.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.projectfiveg.models.DeviceStatusMessage;
import pl.projectfiveg.models.ChatMessage;
import pl.projectfiveg.models.UserMessage;

@Slf4j
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebSocketTextController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketTextController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/devicews/{uuid}")
    @SendTo("/topic/{uuid}")
    public ChatMessage get(@Payload ChatMessage chatMessage , @DestinationVariable String uuid) {
        return chatMessage;
    }

    @MessageMapping("/userws/{user_uuid}")
    @SendTo("/topic/{user_uuid}")
    public UserMessage user(@Payload UserMessage chatMessage , @DestinationVariable String user_uuid) {
        return chatMessage;
    }

    @MessageMapping("/devicews/global")
    @SendTo("/topic/global")
    public DeviceStatusMessage user(@Payload DeviceStatusMessage message) {
        return message;
    }


}
