package com.room_scout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "room_type")
@Entity
@Data
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int numberOfBeds;

    @Column(nullable = false)
    private int numberOfRooms;

    @Column(nullable = false)
    private int guestCapacity;

    @Column(nullable = false)
    private double basePrice;


    @Column(name = "property_id")
    private Long propertyId;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
