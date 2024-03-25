package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.services.ProductUpdaterService;
import com.gov.iti.business.services.ProductsDisplayerService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

//@WebServlet(urlPatterns = "/updateProduct")
public class ProductUpdaterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reached here");
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        ProductDTO productDTO = parseJsonToProduct(jsonBody.toString());
        System.out.println("Received productDTO: " + productDTO.toString());
        EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("emf");
        ProductUpdaterService productUpdaterService = new ProductUpdaterService(emf);

        Product productFromDB = productUpdaterService.findProductByName(productDTO.getProductName());

        productFromDB.setDescription(productDTO.getDescription());
        productFromDB.setQuantity(productDTO.getQuantity());
        productFromDB.setPrice(productDTO.getPrice());
        productFromDB.setCategory(productDTO.getCategory());
        System.out.println(productFromDB.toString());

        productUpdaterService.updateProduct(productFromDB);

    }

    private ProductDTO parseJsonToProduct(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ProductDTO.class);
    }
}
