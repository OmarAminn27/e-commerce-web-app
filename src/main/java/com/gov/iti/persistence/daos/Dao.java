package com.gov.iti.persistence.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public abstract class Dao<T> {
    protected final Class<T> persistentClass;
    protected final EntityManager entityManager;

    public  Dao(EntityManager entityManager,Class<T> persistentClass){
        this.entityManager = entityManager;
        this.persistentClass = persistentClass;
    }

    public  List<T> find(int id){
        return entityManager.createQuery("SELECT t FROM " + persistentClass.getName() + " t WHERE t.id = :id", persistentClass)
                .setParameter("id", id)
                .getResultList();
    }

    public  List<T> findAll(){
        return entityManager.createQuery("SELECT t FROM " + persistentClass.getName() + " t", persistentClass)
                .getResultList();
    }

    public void create(T entity){
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void update(T entity){
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    public void delete(T entity){
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }
}
