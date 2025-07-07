package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Entity class representing a credit rating.
 * Maps to the {@code rating} table in the database.
 * Contains various rating fields from different agencies as well as an order number.
 */
@Entity
@Table(name = "rating")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @Column
    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;

    @Column
    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;

    @Column
    @NotNull(message = "Order is mandatory")
    @Min(value = 1, message = "Order must be positive")
    private Integer orderNumber;
}
