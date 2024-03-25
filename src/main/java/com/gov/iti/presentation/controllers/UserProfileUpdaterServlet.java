package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.UserDTO;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.UserService;
import com.gov.iti.business.utils.LocalDateTypeAdapter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@WebServlet(urlPatterns = "/updateUserProfile")
public class UserProfileUpdaterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        UserService userService = new UserService(emf);
        List<User> allUsers = userService.getAllUsers();

        List<UserDTO> userDTOs = new ArrayList<>();
        allUsers.forEach(user -> userDTOs.add(new UserDTO(user)));
        System.out.println("reached do Get user profile updater");
        userDTOs.forEach(userDTO -> System.out.println(userDTO.toString()));

        // Convert User object to JSON string using Gson
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        String json = gson.toJson(userDTOs);
        System.out.println(json);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
