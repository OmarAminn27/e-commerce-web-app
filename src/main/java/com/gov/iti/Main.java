package com.gov.iti;

import com.gov.iti.business.entities.*;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.utils.EmailSender;
import com.gov.iti.business.utils.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerce");
//        EntityManager entityManager = emf.createEntityManager();
//
//        User testUser = new User();
//        testUser.setId(31);
//        testUser.setUsername("test");
//        testUser.setBirthday(LocalDate.of(1990, 5, 15));
//        testUser.setPassword("test");
//        testUser.setEmail("test@gmail.com");
//        testUser.setCreditLimit(new BigDecimal("5000.00"));
//
//
//        UserDao.getInstance().update(entityManager, testUser);
//
//        entityManager.close();
    }


//    private static void truncateTables(EntityManager entityManager) {
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//        // Truncate tables first to avoid duplication
//        entityManager.createNativeQuery("DELETE FROM order_item").executeUpdate();
//        entityManager.createNativeQuery("DELETE FROM cart_item").executeUpdate();
//        entityManager.createNativeQuery("DELETE FROM cart").executeUpdate();
//        entityManager.createNativeQuery("DELETE FROM orders").executeUpdate();
//        entityManager.createNativeQuery("DELETE FROM user").executeUpdate();
//        entityManager.createNativeQuery("Delete FROM product").executeUpdate();
//
//        transaction.commit();
//    }
//
//    private static void addData(EntityManager entityManager) throws IOException {
//
//        Product product1 = new Product();
//        product1.setProductName(Products.APPLE.name());
//        product1.setQuantity(10);
//        product1.setPrice(new BigDecimal("22.1"));
//        product1.setCategory(Category.FRUITS.name());
//        product1.setProductImage(readImageBytes(Products.APPLE.name()));
//
//        Product product2 = new Product();
//        product2.setProductName(Products.CARROT.name());
//        product2.setQuantity(30);
//        product2.setPrice(new BigDecimal("7.34"));
//        product2.setCategory(Category.VEGETABLES.name());
//        product2.setProductImage(readImageBytes(Products.CARROT.name()));
//
//        Product product3 = new Product();
//        product3.setProductName(Products.POTATO.name());
//        product3.setQuantity(20);
//        product3.setPrice(new BigDecimal("15.22"));
//        product3.setCategory(Category.VEGETABLES.name());
//        product3.setProductImage(readImageBytes(Products.POTATO.name()));
//
//        Product product4 = new Product();
//        product4.setProductName(Products.TOMATO.name());
//        product4.setQuantity(50);
//        product4.setPrice(new BigDecimal("23.30"));
//        product4.setCategory(Category.VEGETABLES.name());
//        product4.setProductImage(readImageBytes(Products.TOMATO.name()));
//
//        Product product5 = new Product();
//        product5.setProductName(Products.CUCUMBER.name());
//        product5.setQuantity(50);
//        product5.setPrice(new BigDecimal("16.23"));
//        product5.setCategory(Category.VEGETABLES.name());
//        product5.setProductImage(readImageBytes(Products.CUCUMBER.name()));
//
//        Product product6 = new Product();
//        product6.setProductName(Products.ONION.name());
//        product6.setQuantity(60);
//        product6.setPrice(new BigDecimal("17.55"));
//        product6.setCategory(Category.VEGETABLES.name());
//        product6.setProductImage(readImageBytes(Products.ONION.name()));
//
//        Product product7 = new Product();
//        product7.setProductName(Products.GARLIC.name());
//        product7.setQuantity(15);
//        product7.setPrice(new BigDecimal("15.5"));
//        product7.setCategory(Category.VEGETABLES.name());
//        product7.setProductImage(readImageBytes(Products.GARLIC.name()));
//
//        Product product8 = new Product();
//        product8.setProductName(Products.BROCCOLI.name());
//        product8.setQuantity(17);
//        product8.setPrice(new BigDecimal("17.4"));
//        product8.setCategory(Category.VEGETABLES.name());
//        product8.setProductImage(readImageBytes(Products.BROCCOLI.name()));
//
//        Product product9 = new Product();
//        product9.setProductName(Products.SPINACH.name());
//        product9.setQuantity(23);
//        product9.setPrice(new BigDecimal("3.78"));
//        product9.setCategory(Category.VEGETABLES.name());
//        product9.setProductImage(readImageBytes(Products.SPINACH.name()));
//
//        Product product10 = new Product();
//        product10.setProductName(Products.LETTUCE.name());
//        product10.setQuantity(20);
//        product10.setPrice(new BigDecimal("4.44"));
//        product10.setCategory(Category.VEGETABLES.name());
//        product10.setProductImage(readImageBytes(Products.LETTUCE.name()));
//
//        Product product11 = new Product();
//        product11.setProductName(Products.PEPPER.name());
//        product11.setQuantity(2);
//        product11.setPrice(new BigDecimal("6.63"));
//        product11.setCategory(Category.VEGETABLES.name());
//        product11.setProductImage(readImageBytes(Products.PEPPER.name()));
//
//        Product product12 = new Product();
//        product12.setProductName(Products.EGGPLANT.name());
//        product12.setQuantity(3);
//        product12.setPrice(new BigDecimal("13.8"));
//        product12.setCategory(Category.VEGETABLES.name());
//        product12.setProductImage(readImageBytes(Products.EGGPLANT.name()));
//
//        Product product13 = new Product();
//        product13.setProductName(Products.ZUCCHINI.name());
//        product13.setQuantity(7);
//        product13.setPrice(new BigDecimal("12.0"));
//        product13.setCategory(Category.VEGETABLES.name());
//        product13.setProductImage(readImageBytes(Products.ZUCCHINI.name()));
//
//        Product product14 = new Product();
//        product14.setProductName(Products.ARTICHOKE.name());
//        product14.setQuantity(2);
//        product14.setPrice(new BigDecimal("14.99"));
//        product14.setCategory(Category.VEGETABLES.name());
//        product14.setProductImage(readImageBytes(Products.ARTICHOKE.name()));
//
//        Product product15 = new Product();
//        product15.setProductName(Products.CELERY.name());
//        product15.setQuantity(1);
//        product15.setPrice(new BigDecimal("11.2"));
//        product15.setCategory(Category.VEGETABLES.name());
//        product15.setProductImage(readImageBytes(Products.CELERY.name()));
//
//        Product product16 = new Product();
//        product16.setProductName(Products.RADISH.name());
//        product16.setQuantity(7);
//        product16.setPrice(new BigDecimal("8.56"));
//        product16.setCategory(Category.VEGETABLES.name());
//        product16.setProductImage(readImageBytes(Products.RADISH.name()));
//
//        Product product17 = new Product();
//        product17.setProductName(Products.GREEN_BEAN.name());
//        product17.setQuantity(8);
//        product17.setPrice(new BigDecimal("8.56"));
//        product17.setCategory(Category.VEGETABLES.name());
//        product17.setProductImage(readImageBytes(Products.GREEN_BEAN.name()));
//
//        //Herbs
//        Product product18 = new Product();
//        product18.setProductName(Products.ROSEMARY.name());
//        product18.setQuantity(4);
//        product18.setPrice(new BigDecimal("10.44"));
//        product18.setCategory(Category.HERBS.name());
//        product18.setProductImage(readImageBytes(Products.ROSEMARY.name()));
//
//        Product product19 = new Product();
//        product19.setProductName(Products.THYME.name());
//        product19.setQuantity(3);
//        product19.setPrice(new BigDecimal("8.56"));
//        product19.setCategory(Category.HERBS.name());
//        product19.setProductImage(readImageBytes(Products.THYME.name()));
//
//        Product product20 = new Product();
//        product20.setProductName(Products.MINT.name());
//        product20.setQuantity(4);
//        product20.setPrice(new BigDecimal("6.61"));
//        product20.setCategory(Category.HERBS.name());
//        product20.setProductImage(readImageBytes(Products.MINT.name()));
//
//        Product product21 = new Product();
//        product21.setProductName(Products.PARSLEY.name());
//        product21.setQuantity(10);
//        product21.setPrice(new BigDecimal("12.33"));
//        product21.setCategory(Category.HERBS.name());
//        product21.setProductImage(readImageBytes(Products.PARSLEY.name()));
//
//        Product product22 = new Product();
//        product22.setProductName(Products.CILANTRO.name());
//        product22.setQuantity(5);
//        product22.setPrice(new BigDecimal("5.42"));
//        product22.setCategory(Category.HERBS.name());
//        product22.setProductImage(readImageBytes(Products.CILANTRO.name()));
//
//        Product product23 = new Product();
//        product23.setProductName(Products.DILL.name());
//        product23.setQuantity(5);
//        product23.setPrice(new BigDecimal("4.6"));
//        product23.setCategory(Category.HERBS.name());
//        product23.setProductImage(readImageBytes(Products.DILL.name()));
//
//        Product product24 = new Product();
//        product24.setProductName(Products.CHIVES.name());
//        product24.setQuantity(5);
//        product24.setPrice(new BigDecimal("2.45"));
//        product24.setCategory(Category.HERBS.name());
//        product24.setProductImage(readImageBytes(Products.CHIVES.name()));
//
//        //Fruits
//        Product product25 = new Product();
//        product25.setProductName(Products.LEMON.name());
//        product25.setQuantity(3);
//        product25.setPrice(new BigDecimal("2.45"));
//        product25.setCategory(Category.VEGETABLES.name());
//        product25.setProductImage(readImageBytes(Products.LEMON.name()));
//
//        Product product26 = new Product();
//        product26.setProductName(Products.ORANGE.name());
//        product26.setQuantity(3);
//        product26.setPrice(new BigDecimal("2.45"));
//        product26.setCategory(Category.FRUITS.name());
//        product26.setProductImage(readImageBytes(Products.ORANGE.name()));
//
//        Product product27 = new Product();
//        product27.setProductName(Products.BANANA.name());
//        product27.setQuantity(7);
//        product27.setPrice(new BigDecimal("7.33"));
//        product27.setCategory(Category.FRUITS.name());
//        product27.setProductImage(readImageBytes(Products.BANANA.name()));
//
//        Product product28 = new Product();
//        product28.setProductName(Products.GRAPES.name());
//        product28.setQuantity(5);
//        product28.setPrice(new BigDecimal("10.0"));
//        product28.setCategory(Category.FRUITS.name());
//        product28.setProductImage(readImageBytes(Products.GRAPES.name()));
//
//        Product product29 = new Product();
//        product29.setProductName(Products.STRAWBERRY.name());
//        product29.setQuantity(6);
//        product29.setPrice(new BigDecimal("5.3"));
//        product29.setCategory(Category.FRUITS.name());
//        product29.setProductImage(readImageBytes(Products.STRAWBERRY.name()));
//
//        Product product30 = new Product();
//        product30.setProductName(Products.WATERMELON.name());
//        product30.setQuantity(2);
//        product30.setPrice(new BigDecimal("10.41"));
//        product30.setCategory(Category.FRUITS.name());
//        product30.setProductImage(readImageBytes(Products.WATERMELON.name()));
//
//        Product product31 = new Product();
//        product31.setProductName(Products.MELON.name());
//        product31.setQuantity(3);
//        product31.setPrice(new BigDecimal("6.32"));
//        product31.setCategory(Category.FRUITS.name());
//        product31.setProductImage(readImageBytes(Products.MELON.name()));
//
//        Product product32 = new Product();
//        product32.setProductName(Products.PINEAPPLE.name());
//        product32.setQuantity(3);
//        product32.setPrice(new BigDecimal("6.34"));
//        product32.setCategory(Category.FRUITS.name());
//        product32.setProductImage(readImageBytes(Products.PINEAPPLE.name()));
//
//        Product product33 = new Product();
//        product33.setProductName(Products.MANGO.name());
//        product33.setQuantity(10);
//        product33.setPrice(new BigDecimal("15.2"));
//        product33.setCategory(Category.FRUITS.name());
//        product33.setProductImage(readImageBytes(Products.MANGO.name()));
//
//        Product product34 = new Product();
//        product34.setProductName(Products.PEACH.name());
//        product34.setQuantity(4);
//        product34.setPrice(new BigDecimal("7.86"));
//        product34.setCategory(Category.FRUITS.name());
//        product34.setProductImage(readImageBytes(Products.PEACH.name()));
//
//        Product product35 = new Product();
//        product35.setProductName(Products.APRICOT.name());
//        product35.setQuantity(3);
//        product35.setPrice(new BigDecimal("4.6"));
//        product35.setCategory(Category.FRUITS.name());
//        product35.setProductImage(readImageBytes(Products.APRICOT.name()));
//
//        Product product36 = new Product();
//        product36.setProductName(Products.AVOCADO.name());
//        product36.setQuantity(4);
//        product36.setPrice(new BigDecimal("12.5"));
//        product36.setCategory(Category.FRUITS.name());
//        product36.setProductImage(readImageBytes(Products.AVOCADO.name()));
//
//        User user1 = new User();
//        user1.setUsername("john_doe");
//        user1.setBirthday(LocalDate.of(1990, 5, 15));
//        user1.setPassword("password123");
//        user1.setJob("Software Engineer");
//        user1.setEmail("john.doe@example.com");
//        user1.setCreditLimit(new BigDecimal("5000.00"));
//        user1.setCountry("USA");
//        user1.setCity("New York");
//        user1.setStreetName("Broadway");
//        user1.setInterests("Reading, Coding");
//
//        User user2 = new User();
//        user2.setUsername("alice_smith");
//        user2.setBirthday(LocalDate.of(1985, 8, 20));
//        user2.setPassword("pass456");
//        user2.setJob("Marketing Manager");
//        user2.setEmail("alice.smith@example.com");
//        user2.setCreditLimit(new BigDecimal("8000.50"));
//        user2.setCountry("Canada");
//        user2.setCity("Toronto");
//        user2.setStreetName("Queen Street");
//        user2.setInterests("Traveling, Photography");
//
//        User user3 = new User();
//        user3.setUsername("emma_watson");
//        user3.setBirthday(LocalDate.of(1992, 4, 15));
//        user3.setPassword("passEmma");
//        user3.setJob("Actress");
//        user3.setEmail("emma.watson@example.com");
//        user3.setCreditLimit(new BigDecimal("10000.75"));
//        user3.setCountry("UK");
//        user3.setCity("London");
//        user3.setStreetName("Oxford Street");
//        user3.setInterests("Acting, Women's Rights");
//
//        User user4 = new User();
//        user4.setUsername("david_beckham");
//        user4.setBirthday(LocalDate.of(1975, 5, 2));
//        user4.setPassword("soccer123");
//        user4.setJob("Retired Footballer");
//        user4.setEmail("david.beckham@example.com");
//        user4.setCreditLimit(new BigDecimal("15000.25"));
//        user4.setCountry("England");
//        user4.setCity("Manchester");
//        user4.setStreetName("Old Trafford");
//        user4.setInterests("Football, Fashion");
//
//        Cart cart = new Cart();
//        cart.setUser(user1);
//
//        CartItemId cartItemId = new CartItemId();
//        cartItemId.setCartId(cart.getId());
//        cartItemId.setProductId(product1.getId());
//
//        CartItem cartItem = new CartItem();
//        cartItem.setId(cartItemId);
//        cartItem.setCart(cart);
//        cartItem.setProduct(product1);
//        cartItem.setQuantity(4);
//        cartItem.setTotalPrice(400.2);
//
//        Order order = new Order();
//        order.setUser(user1);
//        order.setOrderedAt(LocalDate.of(2002, 7, 8));
//
//        OrderItemId orderItemId = new OrderItemId();
//        orderItemId.setProductId(product1.getId());
//        orderItemId.setOrderId(order.getId());
//
//        OrderItem orderItem = new OrderItem();
//        orderItem.setProduct(product1);
//        orderItem.setOrder(order);
//        orderItem.setId(orderItemId);
//        orderItem.setQuantity(4);
//        orderItem.setTotalPrice(400.2);
//
//        entityManager.getTransaction().begin();
//        entityManager.persist(product1);
//        entityManager.persist(product2);
//        entityManager.persist(product3);
//        entityManager.persist(product4);
//        entityManager.persist(product5);
//        entityManager.persist(product6);
//        entityManager.persist(product7);
//        entityManager.persist(product8);
//        entityManager.persist(product9);
//        entityManager.persist(product10);
//        entityManager.persist(product11);
//        entityManager.persist(product12);
//        entityManager.persist(product13);
//        entityManager.persist(product14);
//        entityManager.persist(product15);
//        entityManager.persist(product16);
//        entityManager.persist(product17);
//        entityManager.persist(product18);
//        entityManager.persist(product19);
//        entityManager.persist(product20);
//        entityManager.persist(product21);
//        entityManager.persist(product22);
//        entityManager.persist(product23);
//        entityManager.persist(product24);
//        entityManager.persist(product25);
//        entityManager.persist(product26);
//        entityManager.persist(product27);
//        entityManager.persist(product28);
//        entityManager.persist(product29);
//        entityManager.persist(product30);
//        entityManager.persist(product31);
//        entityManager.persist(product32);
//        entityManager.persist(product33);
//        entityManager.persist(product34);
//        entityManager.persist(product35);
//        entityManager.persist(product36);
//        entityManager.persist(user1);
//        entityManager.persist(user2);
//        entityManager.persist(user3);
//        entityManager.persist(user4);
//        entityManager.persist(cart);
//        entityManager.persist(cartItem);
//        entityManager.persist(order);
//        entityManager.persist(orderItem);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }
  
}
