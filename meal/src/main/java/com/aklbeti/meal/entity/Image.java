package com.aklbeti.meal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "image_seq", sequenceName = "image_seq")
    long id;

    @Column(name = "image_url")
    String imageUrl;
}
