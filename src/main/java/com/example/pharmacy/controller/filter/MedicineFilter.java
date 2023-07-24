package com.example.pharmacy.controller.filter;

import com.example.pharmacy.command.impl.ReadAllMedicinesCommand;
import com.example.pharmacy.exception.CommandException;
import com.example.pharmacy.util.AttributeCaptureRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.example.pharmacy.command.constant.SessionAttributeName.USER_NAME;

//@WebFilter(urlPatterns = {"/pages/main.jsp"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
@WebFilter(urlPatterns = {"/pages/main.jsp"}, dispatcherTypes = {DispatcherType.FORWARD})
public class MedicineFilter implements Filter {

    static Logger logger = LogManager.getLogger();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("----------> MedicineFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("----------> MedicineFilter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        if (session != null && session.getAttribute(USER_NAME) != null) {
            ReadAllMedicinesCommand command = new ReadAllMedicinesCommand();
            String nextPage;
            try {
                command.execute(httpRequest);
            } catch (CommandException e) {
                //TODO
                throw new RuntimeException(e);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

