package com.example.demo.model.spabooking;


import com.example.demo.model.entity.Hotel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotel_spa_schedule")
public class HotelSpaSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "slot")
    private Integer slot;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Hotel user;

    @ManyToOne
    @JoinColumn(name = "spa_schedule_id")
    private SpaSchedule spaSchedule;
}
