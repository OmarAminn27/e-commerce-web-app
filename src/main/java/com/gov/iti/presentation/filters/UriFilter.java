package com.gov.iti.presentation.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UriFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        System.out.println("URI filter called");

        String requestURI = httpServletRequest.getRequestURI();
        Object canAccessHTMLs = httpServletRequest.getServletContext().getAttribute("canAccessHTMLs");

        if (canAccessHTMLs != null) {
            httpServletRequest.setAttribute("pageRequested", requestURI);
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            System.out.println("cannot access HTMLs");
            httpServletRequest.setAttribute("pageRequested", requestURI);
            httpServletRequest.getRequestDispatcher("/home").forward(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
