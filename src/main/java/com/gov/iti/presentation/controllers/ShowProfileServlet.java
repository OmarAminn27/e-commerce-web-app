package com.gov.iti.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gov.iti.business.dtos.UserDto;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProfileService;
import com.gov.iti.business.utils.LocalDateTypeAdapter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ShowProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProfileService profileService = new ProfileService(emf);

        User user = profileService.getUserData(1);
        System.out.println(user.getUsername());

        UserDto userDto = new UserDto(user);
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        String jsonData = gson.toJson(userDto);

        // Set content type and write JSON data to response
        resp.setContentType("application/json");
        resp.getWriter().print(jsonData);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}