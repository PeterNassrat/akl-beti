package com.aklbeti.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class City {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(name = "city_seq", sequenceName = "city_seq")
    private int id;

    @Column(name = "name")
    private String name;
}
