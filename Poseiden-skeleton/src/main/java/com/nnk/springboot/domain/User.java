package com.nnk.springboot.domain;


import com.nnk.springboot.config.UserConstraint;
import com.nnk.springboot.config.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory",groups = UserConstraint.class)
    private String username;

    @Column(nullable = false)
    @ValidPassword(groups = UserConstraint.class)
    @NotBlank(message = "Password is mandatory",groups = UserConstraint.class)
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "FullName is mandatory",groups = UserConstraint.class)
    private String fullname;

    @Column(nullable = false)
    @NotBlank(message = "Role is mandatory",groups = UserConstraint.class)
    private String role;

}
