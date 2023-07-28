package com.example.demo.model.roombooking;



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
@Table(name = "room_type")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;


    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "roomType")
    @JsonBackReference
    private List<HotelRoomType> hotelRoomTypes = new ArrayList<>();

}
