package com.gov.iti.business.services;

import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class UserProfileUpdaterService {

    private final EntityManagerFactory entityManagerFactory;
    private final UserDao userDao = UserDao.getInstance();

    public UserProfileUpdaterService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void updateUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            userDao.update(entityManager, user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public User getUser (int userID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return userDao.findOneById(userID, entityManager).orElse(null);
    }

    public List<User> getAllUsers () {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return userDao.findAll(entityManager);
    }
}
