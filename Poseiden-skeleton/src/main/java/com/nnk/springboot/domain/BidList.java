package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Represents a BidList entity in the application.
 * Contains information related to market bid entries.
 */

@Entity
@Table(name = "Bidlist")
@Data
public class BidList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Column
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Column
    private String type;

    @NotNull(message = "Bid Quantity is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Bid Quantity must be greater than 0")
    @Column
    private Double bidQuantity;

    @Column
    private Double askQuantity;

    @Column
    private Double bid;

    @Column
    private Double ask;

    @Column
    private String benchmark;

    @Column
    private Timestamp bidListDate;

    @Column
    private String commentary;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

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

    @Column
    private String dealType;

    @Column
    private String sourceListId;

    @Column
    private String side;
}


