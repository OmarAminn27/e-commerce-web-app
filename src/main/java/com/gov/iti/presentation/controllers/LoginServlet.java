package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.CartService;
import com.gov.iti.business.services.LoginService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gov.iti.presentation.AdminValidator.ADMIN_USERNAME;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reached post method of login servlet");

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        LoginService loginService = new LoginService(emf);
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = loginService.findUserByEmail(email);

        if (user != null) {
            System.out.println("user: " + user);
            boolean passwordMatches = loginService.passwordMatches(user, password);
            if (passwordMatches) {
                System.out.println("the password matches");
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);

                // check if admin
                if (user.getUsername().equals(ADMIN_USERNAME)) {
                    resp.sendRedirect("displayUsers");
                } else { // not an admin
                    if (user.getCart() == null) { // no cart
                        Cookie[] cookies = req.getCookies();
                        if (cookies != null) {
                            // Iterate through each cookie
                            for (Cookie cookie : cookies) {
                                if ("cartMap".equals(cookie.getName())) {
                                    String decodedValue = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
                                    // Parse JSON string into HashMap
                                    ObjectMapper mapper = new ObjectMapper();
                                    HashMap<String, Integer> cartMap = mapper.readValue(decodedValue, HashMap.class);

                                    HashMap<Integer, Integer> newMap = new HashMap<>();
                                    for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                                        Integer key = Integer.parseInt(entry.getKey());
                                        Integer value = entry.getValue();
                                        newMap.put(key, value);
                                    }

                                    System.out.println(newMap);

                                    // merge local to db
                                    CartService cartService = new CartService(emf);
                                    cartService.mergeLocalStorageIntoUserCart(newMap, user.getId());

                                    cookie.setMaxAge(0);
                                    break;
                                }
                            }
                        } else {
                            resp.sendRedirect("home");
                        }
                    } else {
                        resp.sendRedirect("home");
                    }
                }
            } else { // password does not match
                System.out.println("the password does not match1");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/login.html");
                dispatcher.forward(req, resp);
            }
        } else { // session is null
            System.out.println("user is null");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/login.html");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reached get method of login servlet");
        HttpSession session = req.getSession(false);
        if (session == null) {
            System.out.println("doGet, user is null");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/login.html");
            dispatcher.forward(req, resp);
        } else {
            System.out.println("doGet, user not null");
            System.out.println("dispatching to home");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/home");
            dispatcher.forward(req, resp);
        }
    }

}