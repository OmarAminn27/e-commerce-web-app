package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.utils.Products;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDisplayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProductsDisplayerService productsDisplayerService = new ProductsDisplayerService(emf);
        List<Product> allProducts = productsDisplayerService.getAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        allProducts.forEach(product -> productDTOS.add(new ProductDTO(product)));


        // Convert Product object to JSON string using Gson
        Gson gson = new Gson();
        String json = gson.toJson(productDTOS);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
