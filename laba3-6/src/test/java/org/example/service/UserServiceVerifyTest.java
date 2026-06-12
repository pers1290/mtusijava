package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceVerifyTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCallSaveOnRepository() {
        UserDto dto = UserDto.builder()
                .name("Иван")
                .email("ivan@example.com")
                .password("qwerty123")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("ENCODED");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.createUser(dto);

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("qwerty123");
    }
}
