package com.gov.iti.business.services;

import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.CartItemId;
import com.gov.iti.business.entities.Product;
import com.gov.iti.persistence.daos.CartDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Set;


public class CartService {
    private final EntityManagerFactory entityManagerFactory;
    private final CartDao cartDao = CartDao.getInstance();
    public CartService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addCart (Cart cart) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            cartDao.create(entityManager, cart);

            transaction.commit();
            entityManager.close();
        }catch (Exception e){
            System.out.println("CartService: addCart failed!");
            e.printStackTrace();
        }
    }

    public void addCartItemToCart(Cart cart, Product product, Integer quantity, Double totalPrice) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(cart);
        entityManager.merge(product);

        CartItemId cartItemId = new CartItemId();
        cartItemId.setCartId(cart.getId()); // Use managed cart ID
        cartItemId.setProductId(product.getId()); // Use managed product ID
        System.out.println("CartItemId created successfully");

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setCart(cart); // Set managed cart entity
        cartItem.setProduct(product); // Set managed product entity
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(totalPrice);
        System.out.println("CartItem created successfully");

        entityManager.merge(cartItem);

        cart.getCartItems().add(cartItem);

        transaction.commit();
        entityManager.close();
    }


    public Set<CartItem> getCartItems(Cart cart){
        return cart.getCartItems();
    }

    public void updateCartItem(Cart cart, Product product, Integer userQuantity, Double price){
        System.out.println("Product " + product.getProductName() + " already exists in cart");
    }
}