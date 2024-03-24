package com.gov.iti.business.services;

import com.gov.iti.business.entities.*;
import com.gov.iti.persistence.daos.CartDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckoutService {

    private final EntityManagerFactory entityManagerFactory;
    private final CartDao cartDao = CartDao.getInstance();

    public CheckoutService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public void checkout(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // get cart items
        Cart cart = user.getCart();
        Set<CartItem> cartItems = cart.getCartItems();

        // deduct user credit
        BigDecimal cartTotal = BigDecimal.valueOf(cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum());
        deductFromCredit(user, cartTotal);

        // creating an order and persisting it
        Order order = new Order();
        order.setOrderedAt(LocalDate.now());
        order.setUser(user);
        entityManager.persist(order);

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

        emptyCart(entityManager, cart.getId());
        cart.setCartItems(new HashSet<>());

        // persisting changes to db
        orderItems.forEach(entityManager::merge);
        entityManager.merge(user);
        transaction.commit();
    }

    private void emptyCart (EntityManager entityManager, int cartID) {
        String jpql = "DELETE FROM CartItem ci WHERE ci.cart.id = :cartId";
        entityManager.createQuery(jpql)
                .setParameter("cartId", cartID)
                .executeUpdate();
    }

    private void deductFromCredit (User user, BigDecimal cartTotal) {
        user.setCreditLimit(user.getCreditLimit().subtract(cartTotal));
    }

    public boolean canAfford (User user) {
        BigDecimal cartTotal = BigDecimal.valueOf(user.getCart()
                .getCartItems()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum());

        BigDecimal creditLimit = user.getCreditLimit();

        return creditLimit.compareTo(cartTotal) >= 0;
    }
}