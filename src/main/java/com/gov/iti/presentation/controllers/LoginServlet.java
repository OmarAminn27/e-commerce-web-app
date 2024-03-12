package com.gov.iti.presentation.controllers;

import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.LoginService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reached post method of login servlet");
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        LoginService loginService = new LoginService(emf);
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = loginService.findUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("everything is working fine");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/home.html");
            dispatcher.forward(req, resp);
        } else {
            System.out.println("password is incorrect");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reached get method of login servlet");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/login.html");
        dispatcher.forward(req, resp);
    }

}
