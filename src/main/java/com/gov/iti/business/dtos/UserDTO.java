package com.gov.iti.business.dtos;

import com.gov.iti.business.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private String username;
    private LocalDate birthday;
    private String password;
    private String job;
    private String email;
    private BigDecimal creditLimit;
    private String country;
    private String city;
    private String streetName;
    private String interests;

    public UserDTO(String username,  String password, String job, String email, BigDecimal creditLimit, String country, String city, String streetName, String interests) {
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

    public UserDTO(User userEntity) {
        this.username = userEntity.getUsername();
        this.birthday = userEntity.getBirthday();
        this.password = userEntity.getPassword();
        this.job = userEntity.getJob();
        this.email = userEntity.getEmail();
        this.creditLimit = userEntity.getCreditLimit();
        this.country = userEntity.getCountry();
        this.city = userEntity.getCity();
        this.streetName = userEntity.getStreetName();
        this.interests = userEntity.getInterests();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
//                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", job='" + job + '\'' +
                ", email='" + email + '\'' +
                ", creditLimit=" + creditLimit +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", interests='" + interests + '\'' +
                '}';
    }
}
