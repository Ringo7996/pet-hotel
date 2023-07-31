package com.example.demo.model.request;

import com.example.demo.model.entity.PaymentType;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.roombooking.HotelRoomType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHotelRequest {
    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String disctrict;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

}
