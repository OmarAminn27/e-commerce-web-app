package com.gov.iti.presentation.controllers;

import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.RegisterService;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println( "-----------------------");
        System.out.println("RegisterServlet.doPost");
        String username = req.getParameter("username");

        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        UserDao userDao = UserDao.getInstance();

        Optional<User> user = userDao.findUserByName(username, emf.createEntityManager());

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/plain");

        if (user.isEmpty()) {
            out.write("unique");
        } else {
            out.write("taken");
        }

        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegisterServlet.doGet");
    }
}
