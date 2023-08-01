package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.enums.UserRole;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.service.InitUser;
import com.example.demo.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(UserController.class)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldThrowCorrectMessageExceptionWhenPostNotValidUserForm() throws Exception {
        //Given
        User user = InitUser.createUserAdmin();
        when(userService.getUser(user.getUuid())).thenReturn(UserMapper.mapToUserDto(user));
        UserForm userForm = new UserForm(
                "",
                "",
                "",
                UserRole.ADMINISTRATOR,
                "",
                "emailgmail.com",
                0
        );
        ObjectMapper objectMapper = new ObjectMapper();

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", user.getUuid().toString())
                        .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(
                        "[\"lastname: Lastname may not be blank\"," +
                                "\"login: Login may not be blank\"," +
                                "\"salaryPerHour: must be greater than 0\"," +
                                "\"email: must be a well-formed email address\"," +
                                "\"password: Password may not be blank\"," +
                                "\"firstname: Firstname may not be blank\"]"));
    }
}
