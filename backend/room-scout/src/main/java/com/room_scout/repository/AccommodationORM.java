package com.room_scout.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "accommodation")
@Entity
@Data
@NoArgsConstructor

public class AccommodationORM
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String location;
    @Column
    private String country;
    @Column
    private LocalDate creation;
    @Column
    private String city;

}
