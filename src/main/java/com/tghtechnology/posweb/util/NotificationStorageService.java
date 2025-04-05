package com.tghtechnology.posweb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.data.dto.VentaNotificationDTO;

@Service
public class NotificationStorageService {

    private final Queue<VentaNotificationDTO> unreadNotifications = new ConcurrentLinkedQueue<>();

    public void addNotification(VentaNotificationDTO notification) {
        unreadNotifications.add(notification);
    }

    public List<VentaNotificationDTO> getAndClearNotifications() {
        List<VentaNotificationDTO> currentNotifications = new ArrayList<>(unreadNotifications);
        unreadNotifications.clear();
        return currentNotifications;
    }
    public List<VentaNotificationDTO> getNotifications() {
        return new ArrayList<>(unreadNotifications); // No borra las notificaciones
    }
    public void clearAllNotifications() {
        unreadNotifications.clear();
    }
    
    

}
