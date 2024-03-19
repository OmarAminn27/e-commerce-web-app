package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.CartItemDto;
import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.CartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ShowCartServlet.doGet");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        CartService cartService = new CartService(emf);

        //get loggedIn user
        HttpSession session = req.getSession(false);
        if (session != null){
            User loggedInUser = (User) session.getAttribute("user");
            EntityManager entityManager = emf.createEntityManager();
            entityManager.merge(loggedInUser);
            Cart cart = loggedInUser.getCart();
            Set<CartItem> cartItemSet = cartService.getCartItems(cart);

            // Convert CartItem entities to CartItemDto objects
            List<CartItemDto> cartItemDtoList = new ArrayList<>();
            for (CartItem cartItem : cartItemSet) {
                CartItemDto cartItemDto = new CartItemDto(cartItem);
                cartItemDtoList.add(cartItemDto);
            }

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(cartItemDtoList);

            System.out.println(json);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().print(json);
//
//            cartItemDtoList.stream()
//                    .forEach(System.out::println);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ShowCartServlet.doPost");
        doGet(req, resp);
    }
}

//public class ShowCartServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
//        CartService cartService = new CartService(emf);
//
//        // Get loggedIn user
//        HttpSession session = req.getSession(false);
//        if (session != null) {
//            User loggedInUser = (User) session.getAttribute("user");
//            Cart cart = loggedInUser.getCart();
//            Set<CartItem> cartItemSet = cartService.getCartItems(cart);
//
//            // Convert CartItem entities to CartItemDto objects
//            List<CartItemDto> cartItemDtoList = new ArrayList<>();
//            for (CartItem cartItem : cartItemSet) {
//                CartItemDto cartItemDto = new CartItemDto(cartItem);
//                cartItemDtoList.add(cartItemDto);
//            }
//
//            // Serialize the list of CartItemDto objects to JSON
//            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
//                    .create();
////            Gson gson = new Gson();
//            String json = gson.toJson(cartItemDtoList);
//
//            // Set response content type and write JSON to response
//            resp.setContentType("application/json");
//            resp.setCharacterEncoding("UTF-8");
//            resp.getWriter().write(json);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req, resp);
//    }
//}


