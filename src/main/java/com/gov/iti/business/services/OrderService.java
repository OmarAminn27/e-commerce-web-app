package com.gov.iti.business.services;

import com.gov.iti.business.entities.Order;
import com.gov.iti.business.entities.OrderItem;
import com.gov.iti.persistence.daos.OrderDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;

public class OrderService {

    private final EntityManagerFactory entityManagerFactory;
    private final OrderDao orderDao = OrderDao.getInstance();

    public OrderService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Double getTotalCostForOrderByOrderID (int orderID){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return orderDao.findOrderItemsByOrderId(entityManager, orderID).stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }
}
