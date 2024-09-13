package com.room_scout.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor

public class UserORM
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer identification;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;

}
