package com.gov.iti.presentation.controllers;

import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.CheckoutService;
import com.gov.iti.business.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebServlet(urlPatterns = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reached doPost Checkout");

        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        System.out.println("User is: " + user.getUsername());

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        User user1 = new UserService(emf).getUser(user.getId());
        CheckoutService checkoutService = new CheckoutService(emf);

        if (checkoutService.canAfford(user1)){
            checkoutService.checkout(user1);
            resp.getWriter().write("Check out successful!");
        } else {
            resp.getWriter().write("You are poor!");
        }

    }
}
