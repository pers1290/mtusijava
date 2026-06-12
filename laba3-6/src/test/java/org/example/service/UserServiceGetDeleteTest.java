package org.example.service;

import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceGetDeleteTest {

    @Mock
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("Иван");
        user.setEmail("ivan@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals("Иван", result.getName());
        assertEquals("ivan@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenUserByIdNotFound() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(42L));
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        user.setId(7L);

        when(userRepository.findById(7L)).thenReturn(Optional.of(user));

        userService.deleteUser(7L);

        verify(userRepository, times(1)).findById(7L);
        verify(userRepository, times(1)).delete(user);
    }
}
