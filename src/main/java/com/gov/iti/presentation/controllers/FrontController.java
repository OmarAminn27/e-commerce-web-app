package com.gov.iti.presentation.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("WELCOME TO THE FRONT CONTROLLER");
        String pageRequested = (String) req.getAttribute("pageRequested");

        if (pageRequested.contains("login")){
            req.getServletContext().setAttribute("canAccessHTMLs", true);
            resp.sendRedirect("pages/login.html");
        } else {
            req.getServletContext().setAttribute("canAccessHTMLs", true);
            resp.sendRedirect("pages/home.html");
        }
    }
}
