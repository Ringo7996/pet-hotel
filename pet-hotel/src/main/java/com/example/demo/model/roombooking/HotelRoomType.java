package com.example.demo.model.roombooking;


import com.example.demo.model.entity.Hotel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "hotel_room_type")
public class HotelRoomType {
    // Cần đặt emmbed id cho hotel-roomtype để ko bị trùng

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "hotel_ID")
    private Hotel hotel;

    @Column(name = "total_room_number")
    private Integer totalRoomNumber;

    @JsonBackReference
    @OneToMany(mappedBy = "hotelRoomType", fetch = FetchType.EAGER)
    private List<RoomBooking> roomBookings = new ArrayList<>();

}
