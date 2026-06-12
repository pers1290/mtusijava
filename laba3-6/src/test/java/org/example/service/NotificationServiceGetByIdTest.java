package org.example.service;

import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.repository.NotificationRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceGetByIdTest {

    @Mock
    private NotificationRepository notificationRepository;

    @SuppressWarnings("unused")
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldReturnNotificationById() {
        User recipient = new User();
        recipient.setId(1L);

        Notification notification = new Notification();
        notification.setId(10L);
        notification.setTitle("Заголовок");
        notification.setChannel(NotificationChannel.SMS);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setRecipient(recipient);

        when(notificationRepository.findById(10L)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(10L);

        assertEquals("Заголовок", result.getTitle());
        assertEquals(NotificationChannel.SMS, result.getChannel());
    }

    @Test
    void shouldThrowWhenNotificationNotFound() {
        when(notificationRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> notificationService.getNotificationById(404L));
    }
}
