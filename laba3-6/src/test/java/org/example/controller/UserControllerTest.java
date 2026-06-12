package org.example.controller;

import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.service.UserService;
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


class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnOkForGetAll() throws Exception {
        User user = new User();
        user.setName("Иван");
        user.setEmail("ivan@example.com");
        user.setRole(UserRole.ROLE_USER);

        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Иван"))
                .andExpect(jsonPath("$[0].email").value("ivan@example.com"))
                .andExpect(jsonPath("$[0].role").value("ROLE_USER"));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        User user = new User();
        user.setName("Пётр");
        user.setEmail("petr@example.com");
        user.setRole(UserRole.ROLE_ADMIN);

        when(userService.getUserById(5L)).thenReturn(user);

        mockMvc.perform(get("/users/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Пётр"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }
}
