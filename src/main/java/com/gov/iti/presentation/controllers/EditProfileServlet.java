package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProfileService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

//@WebServlet(urlPatterns = "/editProfile")
public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditProfileServlet.doPost");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        ProfileService profileService = new ProfileService(emf);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        user.setUsername(jsonNode.get("username").asText());
        user.setEmail(jsonNode.get("email").asText());
        user.setJob(jsonNode.get("job").asText());
        user.setCountry(jsonNode.get("country").asText());
        user.setCity(jsonNode.get("city").asText());
        user.setStreetName(jsonNode.get("street").asText());
        user.setInterests(jsonNode.get("interests").asText());
        user.setCreditLimit(new BigDecimal(jsonNode.get("credit").asText()));
        String birthdayString = jsonNode.get("birthdate").asText();

        LocalDate birthday = LocalDate.parse(birthdayString);

        user.setBirthday(birthday);

        //password unchanged
        User sameUser = profileService.getUserData(user.getId());
        user.setPassword(sameUser.getPassword());

        profileService.updateUser(user);
        req.getRequestDispatcher("profile").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
