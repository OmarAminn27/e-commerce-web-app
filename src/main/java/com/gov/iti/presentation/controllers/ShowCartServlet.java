package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.CartItemDto;
import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.CartService;
import com.gov.iti.business.services.UserService;
import com.gov.iti.presentation.AdminValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/showCart")
public class ShowCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ShowCartServlet.doGet");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        CartService cartService = new CartService(emf);

        //get loggedIn user
        HttpSession session = req.getSession(false);
        if (session != null){

            // check if not admin first
            User loggedInUser = (User) session.getAttribute("user");
            if (AdminValidator.IS_ADMIN(loggedInUser)) {
                resp.sendRedirect("displayUsers");
            } else {
                User user = new UserService(emf).getUser(loggedInUser.getId());
                System.out.println(user.getUsername());
                Cart cart = user.getCart();
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
            }
        } else { //session is null
            resp.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ShowCartServlet.doPost");
        doGet(req, resp);
    }
}
