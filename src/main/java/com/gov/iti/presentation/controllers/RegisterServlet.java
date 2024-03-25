package com.gov.iti.presentation.controllers;

import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.RegisterService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegisterServlet.doPost");

        EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("emf");

        RegisterService registerService = new RegisterService(emf);
//        UserDao userDao = UserDao.getInstance();

        String type = req.getParameter("type");
        System.out.println("type = " + type);

        switch (type) {
            case "username" -> checkUsername(req, resp, emf, registerService);
            case "email" -> checkEmail(req, resp, emf, registerService);
            case null -> registerUser(req, resp, emf, registerService);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, RegisterService registerService) {
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


        User user = new User(username, email, new BCryptPasswordEncoder().encode(password), birthDate, job, credit, country, city, street, interests);
//        userDao.create(emf.createEntityManager(), user);
        registerService.registerUser(user);
        System.out.println("userRegistered");
        try {
            resp.sendRedirect("login");  // need to be updated
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkEmail(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, RegisterService registerService) {
        System.out.println("RegisterServlet.checkEmail");
        String email = req.getParameter("email");
//        Optional<User> user = userDao.findByEmail(email, emf.createEntityManager());
        boolean isUnique = registerService.checkEmail(email);

        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/plain");
            if (isUnique) {
                out.write("unique");
                System.out.println("unique");
            } else {
                out.write("taken");
                System.out.println("taken");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUsername(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, RegisterService registerService) {
        System.out.println("RegisterServlet.checkUsername");
        String username = req.getParameter("username");

        boolean isUnique = registerService.checkUsername(username);

        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/plain");
            if (isUnique) {
                out.write("unique");
                System.out.println("unique");
            } else {
                out.write("taken");
                System.out.println("taken");
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
