package org.example.service;

import org.example.model.dto.RegisterRequest;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserWithRoleUserAndEncodedPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Иван");
        request.setEmail("ivan@example.com");
        request.setPassword("qwerty123");

        when(userRepository.existsByEmail("ivan@example.com")).thenReturn(false);
        when(passwordEncoder.encode("qwerty123")).thenReturn("ENCODED");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = authService.register(request);

        assertEquals("ivan@example.com", saved.getEmail());
        assertEquals("ENCODED", saved.getPassword());
        assertEquals(UserRole.ROLE_USER, saved.getRole());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());
        assertEquals("ENCODED", captor.getValue().getPassword());
    }

    @Test
    void shouldRegisterAdminWithRoleAdmin() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Root");
        request.setEmail("root@example.com");
        request.setPassword("rootpass");

        when(userRepository.existsByEmail("root@example.com")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("HASH");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = authService.registerAdmin(request);

        assertEquals(UserRole.ROLE_ADMIN, saved.getRole());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Дубль");
        request.setEmail("ivan@example.com");
        request.setPassword("anything");

        when(userRepository.existsByEmail("ivan@example.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authService.register(request));

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
