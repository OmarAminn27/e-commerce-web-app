package com.gov.iti.business.services;

import com.gov.iti.business.entities.*;
import com.gov.iti.persistence.daos.CartDao;
import com.gov.iti.persistence.daos.ProductDao;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class CartService {
    private final EntityManagerFactory entityManagerFactory;
    private final CartDao cartDao = CartDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();

    public CartService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addCart(Cart cart) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            cartDao.create(entityManager, cart);

            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            System.out.println("CartService: addCart failed!");
            e.printStackTrace();
        }
    }

    public void addCartItemToCart(Cart cart, Product product, Integer quantity, Double totalPrice) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

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

        cart.getCartItems().add(cartItem);

        entityManager.merge(cartItem);
        entityManager.merge(cart);

        transaction.commit();
        entityManager.close();
    }


    public void updateCartItem(Cart cart, Product product, Integer userQuantity, Double price) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(product);

        CartItemId cartItemId = new CartItemId();
        cartItemId.setCartId(cart.getId());
        cartItemId.setProductId(product.getId());

        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
        cartItem.setQuantity(cartItem.getQuantity() + userQuantity);
        cartItem.setTotalPrice(cartItem.getTotalPrice() + price);

        entityManager.merge(cartItem);
        transaction.commit();
        entityManager.close();
    }

    public void updateCartItemFromCart(Cart cart, Product product, Integer userQuantity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(product);

        CartItemId cartItemId = new CartItemId();
        cartItemId.setCartId(cart.getId());
        cartItemId.setProductId(product.getId());

        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
        cartItem.setQuantity(userQuantity);
        cartItem.setTotalPrice(product.getPrice().doubleValue() * userQuantity);

        entityManager.merge(cartItem);
        transaction.commit();
        entityManager.close();
    }

    public void removeCartItem(Cart cart, Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CartItemId cartItemId = new CartItemId();
        cartItemId.setCartId(cart.getId());
        cartItemId.setProductId(product.getId());

        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);

        if (cartItem != null) {
            cart.getCartItems().remove(cartItem);
            entityManager.remove(cartItem);
        }

        transaction.commit();
        entityManager.close();
    }

    public Set<CartItem> getCartItems(Cart cart) {
        return cart.getCartItems();
    }

    public void mergeLocalStorageIntoUserCart(HashMap<Integer, Integer> idToQuantityFromClient, Integer userID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = userDao.findOneById(userID, entityManager).orElse(null);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new LinkedHashSet<>());
        entityManager.persist(cart);
        Integer cartId = cart.getId();

        LinkedHashSet<CartItem> cartItems = idToQuantityFromClient.entrySet().stream()
                .map(entry -> {
                    Integer productID = entry.getKey();
                    System.out.println(productID);
                    Integer quantity = entry.getValue();

                    CartItemId cartItemId = new CartItemId();
                    cartItemId.setCartId(cartId);
                    cartItemId.setProductId(productID);

                    CartItem cartItem = new CartItem();
                    cartItem.setId(cartItemId);
                    cartItem.setQuantity(quantity);
                    cartItem.setCart(cart);
                    Product product = productDao.findOneById(productID, entityManager).orElse(null);
                    System.out.println("retrieve product with id " + productID);
                    cartItem.setProduct(product);
                    cartItem.setTotalPrice(quantity * product.getPrice().doubleValue());

                    entityManager.persist(cartItem);

                    return cartItem;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

//        cart.setCartItems(cartItems);
        cartDao.update(entityManager, cart);

        transaction.commit();
    }
}