package pl.projectfiveg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.projectfiveg.models.ChatMessage;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebSocketTextController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketTextController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/devicews/{id}")
    @SendTo("/topic/{id}")
    public ChatMessage get(@Payload ChatMessage chatMessage , @DestinationVariable String uuid) {
        return chatMessage;
    }
}
