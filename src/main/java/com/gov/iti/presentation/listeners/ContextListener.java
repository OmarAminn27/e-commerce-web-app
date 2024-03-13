package com.gov.iti.presentation.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.List;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerce");
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("emf", emf);
        System.out.println();
        System.out.println("Context Initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("emf");
        emf.close();
        System.out.println();
        System.out.println("Context Destroyed");
        try {
            AbandonedConnectionCleanupThread.uncheckedShutdown();
        } catch (Exception e) {
            System.err.println("SEVERE: Failed to stop the MySQL AbandonedConnectionCleanupThread");
        }
    }
}
