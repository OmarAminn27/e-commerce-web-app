package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProfileService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditServlet");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProfileService profileService = new ProfileService(emf);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());
//        System.out.println("jsonNode " + jsonNode.get("username").asText());
        User user = new User();
        user.setId(1);
        user.setUsername(jsonNode.get("username").asText());
        user.setEmail(jsonNode.get("email").asText());
        user.setJob(jsonNode.get("job").asText());
        user.setCountry(jsonNode.get("country").asText());
        user.setCity(jsonNode.get("city").asText());
        user.setStreetName(jsonNode.get("street").asText());
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        profileService.updateUser(user);
        req.getRequestDispatcher("profile").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
