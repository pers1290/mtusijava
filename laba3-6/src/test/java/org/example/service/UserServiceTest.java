package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        UserDto dto = UserDto.builder()
                .name("Иван Иванов")
                .email("ivan@example.com")
                .phone("+79990001122")
                .deviceToken("device-token-123")
                .telegramChatId("123456789")
                .password("qwerty123")
                .build();

        User savedUser = new User();
        savedUser.setName(dto.getName());
        savedUser.setEmail(dto.getEmail());
        savedUser.setPhone(dto.getPhone());
        savedUser.setDeviceToken(dto.getDeviceToken());
        savedUser.setTelegramChatId(dto.getTelegramChatId());
        savedUser.setPassword("ENCODED");
        savedUser.setRole(UserRole.ROLE_USER);

        when(passwordEncoder.encode(anyString())).thenReturn("ENCODED");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("Иван Иванов", result.getName());
        assertEquals("ivan@example.com", result.getEmail());
        assertEquals("ENCODED", result.getPassword());
        assertEquals(UserRole.ROLE_USER, result.getRole());
    }
}
