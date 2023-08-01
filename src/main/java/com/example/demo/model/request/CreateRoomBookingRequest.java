package com.example.demo.model.request;


import com.example.demo.model.entity.PaymentType;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.roombooking.HotelRoomType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomBookingRequest {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Pet pet;

    @NotNull
    private HotelRoomType hotelRoomType;

    @NotNull
    private String paymentType;


}
