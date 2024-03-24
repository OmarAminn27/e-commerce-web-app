package com.gov.iti.presentation.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoginFilter called!");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        if (!requestURI.endsWith("login")) {
            if (httpRequest.getSession(false) == null || httpRequest.getSession().getAttribute("user") == null) {
                httpResponse.sendRedirect("login");
                return;
            }
            chain.doFilter(httpRequest, httpResponse);
        }
        chain.doFilter(httpRequest, httpResponse);

    }
}
