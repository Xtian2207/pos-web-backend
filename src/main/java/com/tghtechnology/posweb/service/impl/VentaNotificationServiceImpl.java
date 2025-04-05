package com.tghtechnology.posweb.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.data.dto.VentaNotificationDTO;
import com.tghtechnology.posweb.service.VentaNotificationService;

@Service
public class VentaNotificationServiceImpl implements VentaNotificationService {

    private static final String TOPIC_VENTAS = "ventas-registradas";
    
    private final KafkaTemplate<String, VentaNotificationDTO> kafkaTemplate;

    public VentaNotificationServiceImpl(KafkaTemplate<String, VentaNotificationDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarNotificacionVenta(VentaNotificationDTO ventaNotificationDTO) {
        kafkaTemplate.send(TOPIC_VENTAS, ventaNotificationDTO);
    }


}
