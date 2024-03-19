package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.UserDTO;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProfileService;
import com.gov.iti.business.utils.LocalDateTypeAdapter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

public class ShowProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ShowProfileServlet.doGet");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProfileService profileService = new ProfileService(emf);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println(user.getUsername());

        UserDTO userDto = new UserDTO(user);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();

        String jsonData = gson.toJson(userDto);

        resp.setContentType("application/json");
        resp.getWriter().write(jsonData);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ShowProfileServlet.doPost");
        doGet(req, resp);
    }
}