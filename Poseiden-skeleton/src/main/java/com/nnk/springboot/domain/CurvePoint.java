package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
/**
 * Entity representing a point on a curve, used for financial modeling or rate curves.
 * Each point includes identifiers, time-based values, and metadata for auditing.
 */
@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @NotNull(message = "must be not null")
    @Column
    private Integer curveId;

    @Column
    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Column
    private Double term;


    @NotNull(message = "Value is mandatory")
    @Column
    private Double value;

    @Column
    private Timestamp creationDate;
}
