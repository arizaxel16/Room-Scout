package com.room_scout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "property")
@Entity
@Data
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private List<RoomType> roomTypes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private List<AddOn> addOns;
}
