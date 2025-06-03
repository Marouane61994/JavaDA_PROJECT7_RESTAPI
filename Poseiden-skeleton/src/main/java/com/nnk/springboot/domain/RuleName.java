package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entity class representing a rule used in the application.
 * Each {@code RuleName} contains metadata including a name, description,
 * JSON structure, SQL template, and associated SQL strings.
 */
@Entity
@Table(name = "rulename")
@Data
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    @Size(max = 125, message = "Name cannot be longer than 125 characters")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Description is mandatory")
    @Size(max = 125, message = "Name cannot be longer than 125 characters")
    private String description;

     @Column(name = "json")
     @NotBlank(message = "Json is mandatory")
     @Size(max = 125, message = "Json cannot be longer than 125 characters")
     private String json;

    @Column(name = "template")
    @NotBlank(message = "Template is mandatory")
    @Size(max = 512, message = "Template cannot be longer than 512 characters")
    private String template;

    @Column(name = "sqlStr")
    @NotBlank(message = "SQL is mandatory")
    @Size(max = 125, message = "SQL cannot be longer than 125 characters")
    private String sqlStr;

    @Column(name = "sqlPart")
    @NotBlank(message = "SQL Part is mandatory")
    @Size(max = 125, message = "SQL Part cannot be longer than 125 characters")
    private String sqlPart;
}
