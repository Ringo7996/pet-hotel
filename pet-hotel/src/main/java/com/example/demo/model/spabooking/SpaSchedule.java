package com.example.demo.model.spabooking;


import com.example.demo.model.enums.Schedule;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "spa_schedue")
public class SpaSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "schedule")
    private Schedule schedule;
}
