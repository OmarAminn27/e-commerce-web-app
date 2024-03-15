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
import java.time.LocalDate;

public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditProfileServlet.doPost");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProfileService profileService = new ProfileService(emf);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());

        User user = new User();

        user.setId(35);
        System.out.println(user.getId());
        user.setUsername(jsonNode.get("username").asText());
        System.out.println("jsonNode " + jsonNode.get("username").asText());
        user.setEmail(jsonNode.get("email").asText());
        System.out.println("jsonNode " + jsonNode.get("email").asText());
        user.setJob(jsonNode.get("job").asText());
        System.out.println("jsonNode " + jsonNode.get("job").asText());
        user.setCountry(jsonNode.get("country").asText());
        System.out.println("jsonNode " + jsonNode.get("country").asText());
        user.setCity(jsonNode.get("city").asText());
        System.out.println("jsonNode " + jsonNode.get("city").asText());
        user.setStreetName(jsonNode.get("street").asText());
        System.out.println("jsonNode " + jsonNode.get("street").asText());
        user.setInterests(jsonNode.get("interests").asText());
        System.out.println("jsonNode " + jsonNode.get("interests").asText());
        String birthdayString = jsonNode.get("birthdate").asText();
        LocalDate birthday = LocalDate.parse(birthdayString);
        System.out.println("jsonNode " + jsonNode.get("birthdate").asText());
        user.setBirthday(birthday);

        profileService.updateUser(user);
        req.getRequestDispatcher("profile").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
