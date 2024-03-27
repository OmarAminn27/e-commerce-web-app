package com.gov.iti.business.services;

import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDataService {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    UserDao userDao = UserDao.getInstance();

    public UserDataService(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = emf.createEntityManager();
    }

    public User getUserById(int id) {
        return userDao.findOneById(id,em).orElse(null);
    }
}
