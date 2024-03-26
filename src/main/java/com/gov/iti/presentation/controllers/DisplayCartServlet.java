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

@WebServlet(urlPatterns = "/displayCart")
public class DisplayCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        // check if no session
        if (session != null) {

            //check if admin
            User user = (User)session.getAttribute("user");
            if (AdminValidator.IS_ADMIN(user)){
                resp.sendRedirect("displayUsers");

            } else { // not an admin
                req.getRequestDispatcher("/WEB-INF/pages/cart.html").forward(req,resp);
            }

        } else { // session is null
            resp.sendRedirect("login");
        }
    }
}
