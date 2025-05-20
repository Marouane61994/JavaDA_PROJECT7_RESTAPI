package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rating")
@Data
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "moodysRating")
    private String moodysRating;

    @Column(name = "sandPRating")
    private String sandPRating;

    @Column(name = "fitchRating")
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;
}
