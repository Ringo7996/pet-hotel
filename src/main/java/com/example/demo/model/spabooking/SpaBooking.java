package com.example.demo.model.spabooking;


import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "spa_booking")
public class SpaBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime creatdAt;

    @Column(name = "status")
    private Status status;

    @Column(name = "cancelable")
    private boolean cancelable;

    // quan he 1- nhieu
    @ManyToOne
    @JoinColumn(name = "hotel_spa_schedule")
    private HotelSpaSchedule hotelSpaSchedule;

    // quan he 1- nhieu
    @Column(name = "bookable")
    private boolean bookable;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
