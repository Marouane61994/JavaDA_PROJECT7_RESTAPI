package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CurveId")
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
