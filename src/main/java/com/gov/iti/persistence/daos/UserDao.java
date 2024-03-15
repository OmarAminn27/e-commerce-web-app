package com.gov.iti.persistence.daos;

import com.gov.iti.business.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
        super(User.class);
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public Optional<User> findByEmail(String email, EntityManager entityManager) {
        List<User> resultList = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.getFirst());
    }

    public Optional<User> findUserByName(String username, EntityManager entityManager) {
        List<User> resultList = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.getFirst());
    }
}
