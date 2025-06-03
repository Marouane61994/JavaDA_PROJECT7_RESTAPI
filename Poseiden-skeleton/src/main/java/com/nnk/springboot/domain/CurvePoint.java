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
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "must be not null")
    @Column(name = "curveId")
    private Integer curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Column(name = "term")
    private Double term;


    @NotNull(message = "Value is mandatory")
    @Column(name = "value")
    private Double value;

    @Column(name = "creationDate")
    private Timestamp creationDate;
}
