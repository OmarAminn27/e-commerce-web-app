package com.gov.iti.presentation.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                    break;
                }
            }
        }
        resp.sendRedirect("login");
    }
}
