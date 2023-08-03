package com.example.demo.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomTypeRequest {

    @NotNull
    private Integer roomTypeId;

    @NotNull
    private Integer totalRoom;

}
