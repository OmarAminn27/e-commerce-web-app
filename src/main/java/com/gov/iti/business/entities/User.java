package com.gov.iti.business.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "job", length = 100)
    private String job;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "credit_limit", precision = 15, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "street_name", length = 100)
    private String streetName;

    @Column(name = "interests")
    private String interests;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    public User() {
    }

    public User(String username, String email, String password, LocalDate birthday, String job, BigDecimal creditLimit, String country, String city, String streetName, String interests) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.job = job;
        this.creditLimit = creditLimit;
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.interests = interests;
    }

}