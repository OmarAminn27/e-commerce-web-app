package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.OrderDTO;
import com.gov.iti.business.entities.Order;
import com.gov.iti.business.services.OrderService;
import com.gov.iti.business.services.UserService;
import com.gov.iti.business.utils.LocalDateTypeAdapter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class OrderHistoryFetcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getServletContext().getAttribute("orderHistoryUsername");
        if (username != null) {
            System.out.println("retrieving order history for user " + username);
            EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
            UserService userService = new UserService(emf);
            List<Order> ordersByUsername = userService.getOrdersByUsername(username);

            List<OrderDTO> orderDTOs = ordersByUsername.stream()
                    .map(order -> {
                        OrderDTO orderDTO = new OrderDTO();
                        orderDTO.setUsername(username);
                        orderDTO.setOrderID(order.getId());
                        orderDTO.setOrderedAt(order.getOrderedAt());
                        orderDTO.setTotalCost(new OrderService(emf)
                                .getTotalCostForOrderByOrderID(order.getId()));

                        return orderDTO;
                    })
                    .toList();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                    .create();
            String json = gson.toJson(orderDTOs);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }
}
