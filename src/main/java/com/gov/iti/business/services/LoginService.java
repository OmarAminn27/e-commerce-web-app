package com.gov.iti.business.services;

import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginService {

    private final EntityManagerFactory entityManagerFactory;
    private final UserDao userDao = UserDao.getInstance();
    public LoginService (EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User findUserById (int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return userDao.findOneById(id, entityManager).orElse(null);
    }

    public User findUserByEmail (String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return userDao.findByEmail(email, entityManager).orElse(null);
    }

    public boolean passwordMatches (User user, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }
}
