package com.aklbeti.meal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "meal")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "meal_seq", sequenceName = "meal_seq")
    long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", length = 1023)
    String description;

    @Column(name = "account_id", nullable = false)
    long accountId;

    @Column(name = "city", nullable = false)
    String city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "meal_id")
    List<Image> images;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    Date createdDate;
}
