package com.example.demo.model.request;


import com.example.demo.model.entity.Pet;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotEmpty(message = "name cannot empty")
    @NotNull(message = "name cannot empty")
    private String name;

    @Email(message = "email is not valid")
    private String email;

    @Size(min = 10, max = 13, message = "phone number is not valid")
    private String phone;
}
