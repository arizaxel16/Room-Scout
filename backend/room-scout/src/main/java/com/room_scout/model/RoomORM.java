package com.room_scout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "room")
@Entity
@Data
@NoArgsConstructor
public class RoomORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long hotelId;

    @Column
    private Integer quantity;

    @Column
    private String description;

    @Column
    private String type;
}
