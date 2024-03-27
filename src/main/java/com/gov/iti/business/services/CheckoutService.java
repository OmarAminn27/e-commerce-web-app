package com.gov.iti.business.services;

import com.gov.iti.business.entities.*;
import com.gov.iti.persistence.daos.CartDao;
import com.gov.iti.persistence.daos.ProductDao;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckoutService {

    private final EntityManagerFactory entityManagerFactory;
    private final UserDao userDao = UserDao.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();

    public CheckoutService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public void checkout(User user1) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = userDao.findUserByName(user1.getUsername(), entityManager).orElse(null);

        // get cart items
        System.out.println("trying to get car");
        Cart cart = user.getCart();
        System.out.println("got cart");
        Set<CartItem> cartItems = cart.getCartItems();
        System.out.println("got cart items");

        // deduct user credit
        BigDecimal cartTotal = BigDecimal.valueOf(cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum());
        deductFromCredit(user, cartTotal);

        // creating an order and persisting it
        Order order = new Order();
        order.setOrderedAt(LocalDate.now());
        order.setUser(user);
        entityManager.persist(order);
        System.out.println("order persisted succesffullyyy");

        // mapping every cart item to an order item and persisting it using stream and map
        Set<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(order.getId());
            orderItemId.setProductId(cartItem.getProduct().getId());

            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemId);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            // update product quantity
            Product product = orderItem.getProduct();
            product.setQuantity(product.getQuantity() - orderItem.getQuantity());
            entityManager.merge(product);

            return orderItem;
        }).collect(Collectors.toSet());

        System.out.println("order items created succesfully");

        emptyCart(entityManager, cart.getId());
        cart.setCartItems(new HashSet<>());

        // persisting changes to db
        orderItems.forEach(entityManager::merge);
        entityManager.merge(user);
        transaction.commit();
    }

    private void emptyCart(EntityManager entityManager, int cartID) {
        String jpql = "DELETE FROM CartItem ci WHERE ci.cart.id = :cartId";
        entityManager.createQuery(jpql)
                .setParameter("cartId", cartID)
                .executeUpdate();
    }

    private void deductFromCredit(User user, BigDecimal cartTotal) {
        user.setCreditLimit(user.getCreditLimit().subtract(cartTotal));
    }

    public boolean canAfford(User user) {
        BigDecimal cartTotal = BigDecimal.valueOf(user.getCart()
                .getCartItems()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum());

        BigDecimal creditLimit = user.getCreditLimit();

        return creditLimit.compareTo(cartTotal) >= 0;
    }

    public boolean hasEnoughQuantity(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Integer> idList = user.getCart().getCartItems().stream().map(cartItem -> cartItem.getProduct().getId()).toList();
        List<Product> productList = idList.stream().map(id -> productDao.findOneById(id, entityManager).orElse(null)).toList();

        for (Product productFromDB : productList) {

            Integer productQuantityFromCart = user.getCart()
                    .getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getProductName().equals(productFromDB.getProductName()))
                    .findFirst()
                    .map(CartItem::getQuantity)
                    .orElse(0);

            if (productFromDB.getQuantity() < productQuantityFromCart) {
                return false;
            }
        }
        return true;
    }
}
