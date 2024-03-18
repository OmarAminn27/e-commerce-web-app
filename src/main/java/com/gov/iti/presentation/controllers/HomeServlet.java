package com.gov.iti.presentation.controllers;

import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.utils.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet.doPost");
        HttpSession session = req.getSession(false);
        if (session != null) {
            System.out.println("session is not null");
            req.getRequestDispatcher("/pages/home.html").forward(req,resp);
        } else {
            System.out.println("session is null");
            req.getRequestDispatcher("/login").forward(req,resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet.doGet");
        doPost(req, resp);
    }
}
