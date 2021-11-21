package pl.projectfiveg.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.projectfiveg.models.ChatMessage;

@Slf4j
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
