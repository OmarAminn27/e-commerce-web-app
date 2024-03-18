package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.iti.business.entities.*;
import com.gov.iti.business.services.CartService;
import com.gov.iti.business.services.ProductsDisplayerService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AddToCartServlet .doPost");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        CartService cartService = new CartService(emf);

        //getting json data
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());
        int productId = jsonNode.get("productId").asInt();
        System.out.println("JsonNode productId: "+ jsonNode.get("productId").asInt());
        int userQuantity = jsonNode.get("userQuantity").asInt();
        System.out.println("JsonNode userQuantity: "+ jsonNode.get("userQuantity").asInt());
        Double price = jsonNode.get("totalPrice").asDouble();
        System.out.println("JsonNode totalPrice: "+ jsonNode.get("totalPrice").asDouble());

        //get the product
        ProductsDisplayerService productService = new ProductsDisplayerService(emf);
        Product product = productService.findProductById(productId);
        System.out.println("AddToCartServlet: productId = " + product.getId());

        //get the user session
        HttpSession session = req.getSession(false);
        if (session !=null){
            User loggedInUser = (User) session.getAttribute("user");
            System.out.println("LoggedIn userId: " + loggedInUser.getId());
            if (loggedInUser.getCart() == null){
                Cart cart = new Cart();
                cart.setUser(loggedInUser);
                cartService.addCart(cart);
                addCartItems(emf, cart, product, userQuantity, price);
            }
            else {
                System.out.println("User already has cart "+ loggedInUser.getCart().getId());
                addCartItems(emf, loggedInUser.getCart(), product, userQuantity, price);
            }
        }else {
            //Manage this case
            System.out.println("no user logged in");
            resp.sendRedirect("/login");
        }
    }

//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        resp.sendRedirect("/home");
//        doPost(req, resp);
//    }

    private void addCartItems(EntityManagerFactory emf, Cart cart, Product product, int userQuantity, Double price){
        CartService cartService = new CartService(emf);
        cartService.addCartItemToCart(cart, product, userQuantity, price);
        System.out.println("AddToCartServlet : Cart items added");
//        boolean cartContainsProduct =
//                cart.getCartItems()
//                        .stream()
//                        .anyMatch(cartItem -> cartItem.getProduct().getId().equals(product.getId()));
//        System.out.println(cartContainsProduct);
//        if (cartContainsProduct){
//            cartService.updateCartItem(cart, product, userQuantity, price);
//            System.out.println("UpdateItemUpdated : Cart items updated");
//        }else{
//            cartService.addCartItemToCart(cart, product, userQuantity, price);
//            System.out.println("AddToCartServlet : Cart items added");
//        }
    }
}
