package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "trade")
@Data
public class Trade {

    /**
     * Entity class that maps to the "trade" table in the database.
     * Represents a financial trade with its various attributes.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer tradeId;

    @Column
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Column
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column
    @NotNull(message = "Buy quantity is mandatory")
    @Positive(message = "Buy quantity must be positive")
    private Double buyQuantity;

    @Column
    private Double sellQuantity;

    @Column
    private Double buyPrice;

    @Column
    private Double sellPrice;

    @Column
    private Timestamp tradeDate;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String benchmark;

    @Column
    private String book;

    @Column
    private String creationName;

    @Column
    private Timestamp creationDate;

    @Column
    private String revisionName;

    @Column
    private Timestamp revisionDate;

    @Column
    private String dealName;

    @Column(name = "dealType")
    private String dealType;

    @Column(name = "sourceListId")
    private String sourceListId;

    @Column(name = "side")
    private String side;
}
