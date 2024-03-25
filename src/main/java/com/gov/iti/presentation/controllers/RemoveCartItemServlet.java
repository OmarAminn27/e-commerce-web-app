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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/removeCartItem")
public class RemoveCartItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RemoveItemServlet .doPost");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        CartService cartService = new CartService(emf);
        ProductsDisplayerService productService = new ProductsDisplayerService(emf);

        try{
            //get loggedIn User
            HttpSession session = req.getSession(false);
            User loggedInUser = (User) session.getAttribute("user");
            User user = new UserService(emf).getUser(loggedInUser.getId());

            Cart cart = user.getCart();

            //get json data
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(req.getInputStream());
            int productId = jsonNode.get("itemId").asInt();
            System.out.println("item to be removed" + productId);
            Product product = productService.findProductById(productId);
            cartService.removeCartItem(cart, product);
        }catch (Exception e){
            e.printStackTrace();
        }
}
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
