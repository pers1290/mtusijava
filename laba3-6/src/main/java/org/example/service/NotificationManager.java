package org.example.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationManager {
    private final List<MessageService> messageServices;

    public NotificationManager(List<MessageService> messageServices) {
        this.messageServices = messageServices;
    }

    public void notify(String message, String recipient) {
        messageServices.forEach(service -> service.sendMessage(message, recipient));
    }
}
