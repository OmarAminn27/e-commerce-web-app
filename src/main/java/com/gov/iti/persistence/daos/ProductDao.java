package com.gov.iti.persistence.daos;

import com.gov.iti.business.entities.Product;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductDao extends AbstractDao<ProductDao>{

    private static final ProductDao INSTANCE = new ProductDao();
    private ProductDao (){
        super (ProductDao.class);
    }
    public static ProductDao getInstance() {
        return INSTANCE;
    }

    public List<Product> getProductsByCategory(String categoryName, EntityManager entityManager) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.category = :categoryName", Product.class)
                .setParameter("categoryName", categoryName)
                .getResultList();
    }
}
