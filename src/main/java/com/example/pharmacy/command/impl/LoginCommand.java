package com.example.pharmacy.command.impl;

import com.example.pharmacy.command.Command;
import com.example.pharmacy.exception.CommandException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.example.pharmacy.command.constant.Message.LOGIN_FAILED_MESSAGE;
import static com.example.pharmacy.command.constant.PageName.INDEX_PAGE;
import static com.example.pharmacy.command.constant.PageName.MAIN_PAGE;
import static com.example.pharmacy.command.constant.RequestAttributeName.FAILED;
import static com.example.pharmacy.command.constant.RequestAttributeName.USER;
import static com.example.pharmacy.command.constant.RequestParameterName.LOGIN;
import static com.example.pharmacy.command.constant.RequestParameterName.PASSWORD;

public class LoginCommand implements Command {
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        String page;
        String name;
        try {
            if (userService.authenticate(login, password)) {
                Optional<String> firstName = userService.findName(login);
                name = firstName.orElse(login);
                request.setAttribute(USER, name);
                page = MAIN_PAGE;
            } else {
                request.setAttribute(FAILED, LOGIN_FAILED_MESSAGE);
                page = INDEX_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
