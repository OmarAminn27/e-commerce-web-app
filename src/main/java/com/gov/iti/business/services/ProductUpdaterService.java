package com.gov.iti.business.services;

import com.gov.iti.business.entities.Product;
import com.gov.iti.persistence.daos.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;

public class ProductUpdaterService {
    private final EntityManagerFactory entityManagerFactory;
    private final ProductDao productDao = ProductDao.getInstance();

    public ProductUpdaterService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void updateProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        productDao.update(entityManager, product);

        transaction.commit();
        entityManager.close();
    }

    public Product findProductByName (String productName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return productDao.getProductByName(productName, entityManager);
    }

    public void addProduct (Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        productDao.create(entityManager, product);

        transaction.commit();
        entityManager.close();
    }

    public void deleteProductByName (String productName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Product productByName = productDao.getProductByName(productName, entityManager);
        System.out.println(productByName.getId());
        entityManager.remove(productByName);

        transaction.commit();
        entityManager.close();
    }
}
