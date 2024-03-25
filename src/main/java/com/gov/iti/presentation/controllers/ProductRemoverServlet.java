package com.gov.iti.presentation.controllers;

import com.gov.iti.business.services.ProductUpdaterService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

//@WebServlet(urlPatterns = "/removeProduct")
public class ProductRemoverServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");
        System.out.println(productName);
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        ProductUpdaterService productUpdaterService = new ProductUpdaterService(emf);
        productUpdaterService.deleteProductByName(productName);
    }
}
