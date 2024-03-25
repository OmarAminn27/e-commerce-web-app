package com.gov.iti.business.services;

import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

public class RegisterService {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    UserDao userDao = UserDao.getInstance();

    public RegisterService(EntityManagerFactory emf) {
        this.emf = emf;
        em = emf.createEntityManager();
    }

    public boolean checkUsername(String username) {
        Optional<User> user = userDao.findUserByName(username, em);
        return user.isEmpty();  // true if unique
    }

    public boolean checkEmail(String email) {
        Optional<User> user = userDao.findByEmail(email, em);
        return user.isEmpty();  // true if unique
    }

    public void registerUser(User user) {
        System.out.println("RegisterService.registerUser");
        em.getTransaction().begin();
        userDao.create(em, user);
        em.getTransaction().commit();
        System.out.println(user.toString());
        System.out.println("User registered successfully");
    }

}
