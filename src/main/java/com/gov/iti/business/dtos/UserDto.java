package com.gov.iti.business.dtos;

import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.Order;
import com.gov.iti.business.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
public class UserDto implements Serializable {
    Integer id;
    String username;
    LocalDate birthday;
    String password;
    String job;
    String email;
    BigDecimal creditLimit;
    String country;
    String city;
    String streetName;
    String interests;

    public UserDto(Integer id, String username, LocalDate birthday, String password, String job, String email, BigDecimal creditLimit, String country, String city, String streetName, String interests) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.password = password;
        this.job = job;
        this.email = email;
        this.creditLimit = creditLimit;
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.interests = interests;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.birthday = user.getBirthday();
        this.job = user.getJob();
        this.email = user.getEmail();
        this.creditLimit = user.getCreditLimit();
        this.country = user.getCountry();
        this.city = user.getCity();
        this.streetName = user.getStreetName();
        this.interests = user.getInterests();
    }
}