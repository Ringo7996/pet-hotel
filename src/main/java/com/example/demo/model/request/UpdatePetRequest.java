package com.example.demo.model.request;

import com.example.demo.model.enums.Sex;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePetRequest {

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String breed;

    @NotNull
    private String color;

    @NotNull
    private Sex sex;

    private boolean isVisible;
}
