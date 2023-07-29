package com.example.demo.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String password;

}
