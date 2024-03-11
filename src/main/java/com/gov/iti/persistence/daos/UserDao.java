package com.gov.iti.persistence.daos;

import  com.gov.iti.business.entities.User;
import jakarta.persistence.EntityManager;

public class UserDao extends Dao<User>{

    public UserDao(EntityManager entityManager) {
        super(entityManager, User.class);
    }


}
