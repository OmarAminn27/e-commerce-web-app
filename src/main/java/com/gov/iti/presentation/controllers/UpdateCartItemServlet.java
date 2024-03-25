package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.CartService;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.services.UserService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/updateCartItem")
public class UpdateCartItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UpdateCartItem .doPost");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        CartService cartService = new CartService(emf);

        //getting json data
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());
        int productId = jsonNode.get("productId").asInt();
        int userQuantity = jsonNode.get("userQuantity").asInt();

        HttpSession session = req.getSession(false);
        User loggedInUser = (User) session.getAttribute("user");
        System.out.println("LoggedIn userId: " + loggedInUser.getId());
        User user = new UserService(emf).getUser(loggedInUser.getId());

        //get the product
        ProductsDisplayerService productService = new ProductsDisplayerService(emf);
        Product product = productService.findProductById(productId);
        System.out.println("UpdateCartItemServlet: productId = " + product.getId());

        cartService.updateCartItemFromCart(user.getCart(), product, userQuantity);
        req.getRequestDispatcher("showCart").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
