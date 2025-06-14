package com.makeart.makeart_server.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "rg", nullable = false, length = 12, unique = true)
    private String rg;

    @Column(name = "cpf", nullable = false, length = 14, unique = true )
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "access", nullable = false, length = 1)
    private Long access;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference("user-phone")
    private List<Phone> phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference("user-address")
    private List<Address> address;
}
