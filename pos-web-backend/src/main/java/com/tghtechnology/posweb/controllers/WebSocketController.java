package com.tghtechnology.posweb.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/venta")
    @SendTo("/topic/ventas")
    public String notificarVenta(String mensaje) {
        return mensaje;
    }

}
