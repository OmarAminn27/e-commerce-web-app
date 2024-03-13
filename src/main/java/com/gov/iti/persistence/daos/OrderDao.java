package com.gov.iti.persistence.daos;

import com.gov.iti.business.entities.Order;
import com.gov.iti.business.entities.OrderItem;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrderDao extends AbstractDao<Order>{

    private static final OrderDao INSTANCE = new OrderDao();
    private OrderDao (){
        super(Order.class);
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    public List<Order> getOrdersByUserId(EntityManager entityManager, int userId) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<OrderItem> findOrderItemsByOrderId (EntityManager entityManager, int orderId) {
        return entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId", OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
