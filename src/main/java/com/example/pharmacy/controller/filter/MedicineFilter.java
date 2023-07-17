package com.example.pharmacy.controller.filter;

import com.example.pharmacy.command.impl.ReadAllMedicinesCommand;
import com.example.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.example.pharmacy.command.constant.SessionAttributeName.USER_NAME;

@WebFilter(dispatcherTypes = {DispatcherType.FORWARD },urlPatterns = "/main.jsp")
public class MedicineFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute(USER_NAME) != null) {
            ReadAllMedicinesCommand command = new ReadAllMedicinesCommand();
            String nextPage;
            try {
                nextPage = command.execute(httpRequest);
            } catch (CommandException e) {
                //TODO
                throw new RuntimeException(e);
            }
            httpResponse.sendRedirect(httpRequest.getContextPath() + nextPage);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}

