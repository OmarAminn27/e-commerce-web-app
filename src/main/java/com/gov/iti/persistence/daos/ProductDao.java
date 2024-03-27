package com.gov.iti.persistence.daos;

import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.OrderItem;
import com.gov.iti.business.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Set;

public class ProductDao extends AbstractDao<Product>{

    private static final ProductDao INSTANCE = new ProductDao();
    private ProductDao (){
        super (Product.class);
    }
    public static ProductDao getInstance() {
        return INSTANCE;
    }

    public List<Product> getProductsByCategory(String categoryName, EntityManager entityManager) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.category = :categoryName", Product.class)
                .setParameter("categoryName", categoryName)
                .getResultList();
    }

    public Product getProductByName(String productName, EntityManager entityManager) {
        System.out.println("reached getProductByName in PRODUCTDAO");
        try {
            return entityManager.createQuery("SELECT p FROM Product p WHERE p.productName = :productName", Product.class)
                    .setParameter("productName", productName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Product with name '" + productName + "' not found");
        }
    }

    public void removeOrderItemsAndCartItemsByProductID (int productID, EntityManager entityManager){
        System.out.println("reached removeOrderItemsAndCartItemsByProductID");
        Product product = this.findOneById(productID, entityManager).orElse(null);

        Set<CartItem> cartItems = product.getCartItems();
        for (CartItem cartItem : cartItems) {
            entityManager.remove(cartItem);
        }

        Set<OrderItem> orderItems = product.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            entityManager.remove(orderItem);
        }
    }
}
