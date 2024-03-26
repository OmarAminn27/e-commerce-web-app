package com.gov.iti.presentation.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gov.iti.business.entities.User;
import com.gov.iti.business.services.ProfileService;
import com.gov.iti.business.services.RegisterService;
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
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/editProfile")
public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditProfileServlet.doPost");

        EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("emf");

        RegisterService registerService = new RegisterService(emf);
        ProfileService profileService = new ProfileService(emf);

        String type = req.getParameter("type");
        System.out.println("type = " + type);

        switch (type) {
            case "username" -> checkUsername(req, resp, emf, registerService);
            case "email" -> checkEmail(req, resp, emf, registerService);
            case null -> updateUser(req, resp, emf, profileService);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditProfileServlet.doGet");
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

    private void updateUser(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, ProfileService profileService) throws IOException {
        System.out.println("EditProfileServlet.updateUser");
        User currentUser = (User) req.getSession().getAttribute("user");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());
        System.out.println("jsonNode = " + jsonNode);

        String username = jsonNode.get("username").asText();
        String email = jsonNode.get("email").asText();
        String job = jsonNode.get("job").asText();
        String country = jsonNode.get("country").asText();
        String city = jsonNode.get("city").asText();
        String street = jsonNode.get("street").asText();
        String interests = jsonNode.get("interests").asText();
        BigDecimal credit = new BigDecimal(jsonNode.get("credit").asText());
        String birthDateStr = jsonNode.get("birthdate").asText();
        LocalDate birthDate = LocalDate.parse(birthDateStr);
        System.out.println("birthDate = " + birthDate);

        if(currentUser == null){
            System.out.println("currentUser is null");
        }else{
            System.out.println("currentUser is not null");
        }

        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setJob(job);
        currentUser.setCountry(country);
        currentUser.setCity(city);
        currentUser.setStreetName(street);
        currentUser.setInterests(interests);
        currentUser.setCreditLimit(credit);
        currentUser.setBirthday(birthDate);


        profileService.updateUser(currentUser);

        System.out.println("userUpdated");
        try {
            resp.sendRedirect("home");  // need to be updated
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkEmail(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, RegisterService registerService) {
        System.out.println("RegisterServlet.checkEmail");
        String email = req.getParameter("email");
//        Optional<User> user = userDao.findByEmail(email, emf.createEntityManager());
        boolean isUnique = registerService.checkEmail(email);
        User currentUser = (User) req.getSession().getAttribute("user");

        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/plain");
            if (isUnique || currentUser.getEmail().equals(email)) {
                out.write("unique");
                System.out.println("unique");
            } else {
                out.write("taken");
                System.out.println("taken");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUsername(HttpServletRequest req, HttpServletResponse resp, EntityManagerFactory emf, RegisterService registerService) {
        System.out.println("RegisterServlet.checkUsername");
        String username = req.getParameter("username");

        boolean isUnique = registerService.checkUsername(username);
        User currentUser = (User) req.getSession().getAttribute("user");


        System.out.println("currentUser.getUsername() = " + currentUser.getUsername());
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/plain");
            if (isUnique || currentUser.getUsername().equals(username)) {
                out.write("unique");
                System.out.println("unique");
            } else {
                out.write("taken");
                System.out.println("taken");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
