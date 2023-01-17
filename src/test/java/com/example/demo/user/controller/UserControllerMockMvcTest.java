package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.service.InitUser;
import com.example.demo.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(UserController.class)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldGetUsers() throws Exception {
        //Given
        User user = InitUser.createUserAdmin();
        when(userService.getUser(user.getUuid())).thenReturn(UserMapper.mapToUserDto(user));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user/uuid/{uuid}", user.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.login", Matchers.is(user.getLogin())));
    }
}
