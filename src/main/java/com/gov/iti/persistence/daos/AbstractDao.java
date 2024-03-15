package com.gov.iti.persistence.daos;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    protected final Class<T> theClass;

    protected AbstractDao(Class<T> clazz) {
        this.theClass = clazz;
    }

    public Optional<T> findOneById(int id, EntityManager entityManager) {
        T entity = entityManager.find(theClass, id);
        return Optional.ofNullable(entity);
    }
    public List<T> findAll(EntityManager entityManager) {
        return entityManager.createQuery("FROM " + theClass.getName() + " e", theClass)
                .getResultList();
    }

    public void create(EntityManager entityManager, T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public T update(EntityManager entityManager, T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        System.out.println("From user dao: user updated");

        return entity;
    }

    public void deleteById(EntityManager entityManager,int entityId) {
        T entityToRemove = entityManager.find(theClass, entityId);
        if (entityToRemove != null) { // to avoid null pointer exceptions
            entityManager.getTransaction().begin();
            entityManager.remove(entityToRemove);
            entityManager.getTransaction().commit();
        }
    }

}