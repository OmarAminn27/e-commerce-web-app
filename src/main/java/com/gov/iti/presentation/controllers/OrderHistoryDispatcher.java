package com.gov.iti.presentation.controllers;

import com.gov.iti.business.entities.User;
import com.gov.iti.presentation.AdminValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.http.HttpClient;

@WebServlet(urlPatterns = "/orderHistory")
public class OrderHistoryDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        //check if session is not null
        if (session != null) {
            User user = (User) session.getAttribute("user");

            // check if admin
            if (AdminValidator.IS_ADMIN(user)) {
                req.getRequestDispatcher("/WEB-INF/pages/adminOrderHistory.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("home").forward(req, resp);
            }
        } else { // session is null
            resp.sendRedirect("login");
        }

    }
}
