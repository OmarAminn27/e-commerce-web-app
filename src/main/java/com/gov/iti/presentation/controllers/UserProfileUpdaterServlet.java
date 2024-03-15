package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gov.iti.business.dtos.ProductDTO;
import com.gov.iti.business.dtos.UserDTO;
import com.gov.iti.business.entities.Product;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProductsDisplayerService;
import com.gov.iti.business.services.UserProfileUpdaterService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserProfileUpdaterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        UserProfileUpdaterService userProfileUpdaterService = new UserProfileUpdaterService(emf);
        List<User> allUsers = userProfileUpdaterService.getAllUsers();

        List<UserDTO> userDTOs = new ArrayList<>();
        allUsers.forEach(user -> userDTOs.add(new UserDTO(user)));
        System.out.println("reached do Get user profile updater");

        // Convert User object to JSON string using Gson
        Gson gson = new Gson();
        String json = gson.toJson(userDTOs);
        System.out.println(json);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
