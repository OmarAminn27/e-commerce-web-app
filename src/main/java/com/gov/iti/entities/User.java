package com.gov.iti.entities;

import java.sql.Date;

public class User {
    private long id;
    private String username;
    private Date birthDate;
    private String password;
    private String job;
    private String email;
    private double credit_limit;
    private String country;
    private String city;
    private String street;
    private String interests;

    public User(long id, String username, Date birthDate, String password, String job, String email, double credit_limit, String country, String city, String street, String interests) {
        this.id = id;
        this.username = username;
        this.birthDate = birthDate;
        this.password = password;
        this.job = job;
        this.email = email;
        this.credit_limit = credit_limit;
        this.country = country;
        this.city = city;
        this.street = street;
        this.interests = interests;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(double credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
