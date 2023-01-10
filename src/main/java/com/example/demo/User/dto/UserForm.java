package com.example.demo.User.dto;

import com.example.demo.User.enums.AppRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//import javax.validation.constraints.NotBlank;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class UserForm {

//    @NotBlank
    private UUID uuid;
//    @NotBlank
    private String login;
//    @NotBlank
    private String lastname;
//    @NotBlank
    private String firstname;
//    @NotBlank
    private AppRoles userRole;
//    @NotBlank
    private String password;
//    @NotBlank
    private String email;
//    @NotBlank
//    @Positive
    private int salaryPerHour;
}
