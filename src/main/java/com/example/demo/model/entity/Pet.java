package com.example.demo.model.entity;


import com.example.demo.model.enums.Sex;
import com.example.demo.model.roombooking.RoomBooking;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "breed")
    private String breed;

    @Column(name = "color")
    private String color;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "is_visible")
    private boolean isVisible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    private List<RoomBooking> roomBookings;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pet")
    private Image image;

}
