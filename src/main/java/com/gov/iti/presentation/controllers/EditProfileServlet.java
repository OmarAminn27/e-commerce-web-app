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
import java.math.BigDecimal;
import java.time.LocalDate;

public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditProfileServlet.doPost");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProfileService profileService = new ProfileService(emf);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());

        User user = new User();

        user.setId(17);
        System.out.println(user.getId());

        user.setUsername(jsonNode.get("username").asText());
        user.setEmail(jsonNode.get("email").asText());
        user.setJob(jsonNode.get("job").asText());
        user.setCountry(jsonNode.get("country").asText());
        user.setCity(jsonNode.get("city").asText());
        user.setStreetName(jsonNode.get("street").asText());
        user.setInterests(jsonNode.get("interests").asText());
        user.setCreditLimit(new BigDecimal(jsonNode.get("credit").asText()));
        System.out.println("json credit" + jsonNode.get("credit").asText());
        System.out.println("user credit" + user.getCreditLimit());
        String birthdayString = jsonNode.get("birthdate").asText();

        LocalDate birthday = LocalDate.parse(birthdayString);

        System.out.println("jsonNode " + jsonNode.get("birthdate").asText());
        user.setBirthday(birthday);

        //password unchanged
        User sameUser = profileService.getUserData(1);
        user.setPassword(sameUser.getPassword());

        profileService.updateUser(user);
        req.getRequestDispatcher("profile").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
