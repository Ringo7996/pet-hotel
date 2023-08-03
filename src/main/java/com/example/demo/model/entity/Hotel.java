package com.example.demo.model.entity;


import com.example.demo.model.roombooking.HotelRoomType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<HotelRoomType> hotelRoomTypes = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "myHotels", fetch = FetchType.EAGER,cascade = CascadeType.ALL )
    private List<User> staff = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hotel")
    private Image image;

    @PostPersist
    protected void onCreate() {
        this.status = true;
    }

}