package com.example.demo.model.request;


import com.example.demo.model.entity.Pet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String password;

    @NotNull
    private List<Pet> pets;
}
