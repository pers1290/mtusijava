package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.service.NotificationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        Notification response = notificationService.createNotification(request);
        return mapToDto(response);
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        Notification response = notificationService.getNotificationById(id);
        return mapToDto(response);
    }

    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id, @RequestBody @Valid NotificationDto request) {
        Notification response = notificationService.updateNotification(id, request);
        return mapToDto(response);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }

    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getNotificationsByChannel(channel).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId).stream()
                .map(this::mapToDto)
                .toList();
    }

    private NotificationDto mapToDto(Notification response) {
        return NotificationDto.builder()
                .title(response.getTitle())
                .message(response.getMessage())
                .channel(response.getChannel())
                .status(response.getStatus())
                .createdAt(response.getCreatedAt())
                .sentAt(response.getSentAt())
                .recipientId(response.getRecipient().getId())
                .build();
    }
}
