package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @Column(name = "sandPRating")
    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;

    @Column(name = "fitchRating")
    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;

    @Column(name = "orderNumber")
    @NotNull(message = "Order is mandatory")
    @Min(value = 1, message = "Order must be positive")
    private Integer orderNumber;
}
