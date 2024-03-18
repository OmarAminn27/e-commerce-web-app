package com.gov.iti.business.services;

import com.gov.iti.business.entities.Product;
import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.ProductDao;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class ProductsDisplayerService {
    private final EntityManagerFactory entityManagerFactory;
    private final ProductDao productDao = ProductDao.getInstance();
    public ProductsDisplayerService (EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Product findProductByName (String productName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return productDao.getProductByName(productName, entityManager);
    }

    public List<Product> getAllProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return productDao.findAll(entityManager);
    }

//    public Optional<Product> getProductById(int id){
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        return productDao.findOneById(id, entityManager);
//    }

    public Product findProductById (int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return productDao.findOneById(id, entityManager).orElse(null);
    }
}
