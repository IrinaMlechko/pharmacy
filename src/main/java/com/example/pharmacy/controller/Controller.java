package com.example.pharmacy.controller;

import com.example.pharmacy.command.Command;
import com.example.pharmacy.command.CommandType;
import com.example.pharmacy.exception.CommandException;
import com.example.pharmacy.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.example.pharmacy.command.constant.PageName.SERVER_ERROR_PAGE;
import static com.example.pharmacy.command.constant.RequestAttributeName.ERROR_MSG;
import static com.example.pharmacy.command.constant.RequestParameterName.COMMAND;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    static Logger logger = LogManager.getLogger();
    @Override
    public void init() {
        ConnectionPool.getInstance();
        logger.info("Init servlet" + this.getServletInfo());
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(COMMAND);
        Command command = CommandType.define(commandStr);
        String page;
        try {
            page = command.execute(request);
            request.getRequestDispatcher(page).forward(request, response);
        } catch (CommandException e) {
            handleServerError(request, response, e.getLocalizedMessage());
        }
    }

    private void handleServerError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException {
        request.setAttribute(ERROR_MSG, errorMsg);
        request.getRequestDispatcher(SERVER_ERROR_PAGE).forward(request, response);
    }
    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        logger.info("Servlet destroyed" + this.getServletName());
    }
}