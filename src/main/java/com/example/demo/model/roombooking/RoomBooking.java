package com.example.demo.model.roombooking;

import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.PaymentType;
import com.example.demo.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room_booking")
public class RoomBooking {
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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonBackReference
    private Pet pet;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "hotel_room_type_id")
    private HotelRoomType hotelRoomType;

    private PaymentType paymentType;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @PostPersist
    protected void onCreate() {
        creatdAt = LocalDateTime.now();
        cancelable = !(paymentType == PaymentType.CASH);
        status = Status.PENDING;
        user = pet.getUser();
    }

}
