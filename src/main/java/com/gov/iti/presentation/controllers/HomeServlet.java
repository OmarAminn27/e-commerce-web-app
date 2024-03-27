package com.gov.iti.presentation.controllers;

import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.utils.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.presentation.AdminValidator;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet.doPost");
        HttpSession session = req.getSession(false);
        if (session != null) {

            // check if admin and redirect to admin page
            User user = (User) session.getAttribute("user");
            if (AdminValidator.IS_ADMIN(user)){
                System.out.println("admin! redirecting to admin page");
                resp.sendRedirect("displayUsers");
            } else {
                System.out.println("not admin redirecing to home.html");
                req.getRequestDispatcher("/WEB-INF/pages/home.html").forward(req, resp);
            }

        } else {
            System.out.println("session is null");
            req.getRequestDispatcher("/login").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
