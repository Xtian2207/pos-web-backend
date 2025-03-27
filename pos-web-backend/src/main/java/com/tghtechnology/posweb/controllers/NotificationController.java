package com.tghtechnology.posweb.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tghtechnology.posweb.data.dto.VentaNotificationDTO;
import com.tghtechnology.posweb.util.NotificationStorageService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificationController {

    private final NotificationStorageService storageService;

    public NotificationController(NotificationStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<VentaNotificationDTO>> getNewNotifications() {
        return ResponseEntity.ok(storageService.getAndClearNotifications());
    }
}