package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.services.ProductUpdaterService;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.utils.ByteArrayToBase64TypeAdapter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ProductAdderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        System.out.println("PRODUCT ADDER SERVLET");

        // Parse JSON into ProductDTO object
        ProductDTO productDTO = parseJsonToProduct(jsonBody.toString());

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
//        product.setProductImage(productDTO.getProductImage());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerce");
        ProductUpdaterService productUpdaterService = new ProductUpdaterService(emf);

        productUpdaterService.addProduct(product);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private ProductDTO parseJsonToProduct(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
                .create();
        return gson.fromJson(json, ProductDTO.class);
    }
}
