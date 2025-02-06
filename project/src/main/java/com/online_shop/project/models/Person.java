package com.online_shop.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;


}
