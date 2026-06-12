package org.example.controller;

import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {

    private MockMvc mockMvc;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        NotificationController controller = new NotificationController(notificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnAllNotifications() throws Exception {
        User recipient = new User();
        recipient.setId(1L);

        Notification notification = new Notification();
        notification.setId(10L);
        notification.setTitle("Заголовок");
        notification.setMessage("Сообщение");
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setRecipient(recipient);

        when(notificationService.getAllNotifications()).thenReturn(List.of(notification));

        mockMvc.perform(get("/notifications/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Заголовок"))
                .andExpect(jsonPath("$[0].channel").value("EMAIL"))
                .andExpect(jsonPath("$[0].status").value("CREATED"))
                .andExpect(jsonPath("$[0].recipientId").value(1));
    }
}
