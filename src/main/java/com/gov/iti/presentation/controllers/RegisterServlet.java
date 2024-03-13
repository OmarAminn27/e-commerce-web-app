package com.gov.iti.presentation.controllers;

import com.gov.iti.business.entities.User;
import com.gov.iti.persistence.daos.UserDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class RegisterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------------------");
        System.out.println("RegisterServlet.doPost");

        EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("emf");
        UserDao userDao = UserDao.getInstance();

        String type = req.getParameter("type");
        System.out.println("type = " + type);

        switch (type) {
            case "username" -> checkUsername(req, resp, emf, userDao);
            case "email" -> checkEmail(req, resp, emf, userDao);
            case null -> registerUser(req, resp, emf, userDao);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, UserDao userDao) {
        System.out.println("RegisterServlet.registerUser");

        String username = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String birthDateStr = req.getParameter("birthday");
        LocalDate birthDate = LocalDate.parse(birthDateStr);
        String job = req.getParameter("job");
        String creditStr = req.getParameter("credit-limit");
        BigDecimal credit = new BigDecimal(creditStr);
        String interests = req.getParameter("interests");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String street = req.getParameter("street");


        User user = new User(username, email, password, birthDate, job, credit, country, city, street, interests);
        userDao.create(emf.createEntityManager(), user);

        try {
            resp.sendRedirect("pages/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkEmail(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, UserDao userDao) {
        System.out.println("RegisterServlet.checkEmail");
        String email = req.getParameter("email");
        Optional<User> user = userDao.findByEmail(email, emf.createEntityManager());
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/plain");
            if (user.isEmpty()) {
                out.write("unique");
            } else {
                out.write("taken");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUsername(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, UserDao userDao) {
        System.out.println("RegisterServlet.checkUsername");
        String username = req.getParameter("username");
        Optional<User> user = userDao.findUserByName(username, emf.createEntityManager());
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/plain");
            if (user.isEmpty()) {
                out.write("unique");
            } else {
                out.write("taken");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegisterServlet.doGet");
    }
}
