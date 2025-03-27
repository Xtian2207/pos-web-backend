package com.tghtechnology.posweb.util;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.data.dto.VentaNotificationDTO;

@Service
public class KafkaVentaConsumer {

    private final NotificationStorageService storageService;

    public KafkaVentaConsumer(NotificationStorageService storageService) {
        this.storageService = storageService;
    }

    @KafkaListener(topics = "ventas-registradas", groupId = KafkaGroups.NOTIFICATIONS_GROUP)
    public void consumeVenta(VentaNotificationDTO venta) {
        storageService.addNotification(venta);
    }
    
}
