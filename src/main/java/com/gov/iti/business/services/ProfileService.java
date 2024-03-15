package com.gov.iti.business.services;

import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ProfileService {
    private final EntityManagerFactory entityManagerFactory;
    private final UserDao userDao = UserDao.getInstance();
    public ProfileService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User getUserData(int userId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return userDao.findOneById(userId, entityManager).orElse(null);
    }

    public void updateUser(User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userDao.update(entityManager, user);
    }
}
