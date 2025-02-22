package com.tghtechnology.posweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(String message) {
        System.out.println("Mensaje: " + message);
        messagingTemplate.convertAndSend("topic/ventas",message);
    }
    
}
