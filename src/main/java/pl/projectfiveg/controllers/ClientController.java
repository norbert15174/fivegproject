package pl.projectfiveg.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.projectfiveg.services.WebSocketClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final WebSocketClientService webSocketClientService;

    public ClientController(WebSocketClientService webSocketClientService) {
        this.webSocketClientService = webSocketClientService;
    }

}
