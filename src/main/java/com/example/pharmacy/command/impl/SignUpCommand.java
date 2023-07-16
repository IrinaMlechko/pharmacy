package com.example.pharmacy.command.impl;

import com.example.pharmacy.command.Command;
import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.CommandException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.service.impl.UserServiceImpl;
import com.example.pharmacy.util.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.example.pharmacy.command.constant.Message.LOGIN_ALREADY_EXISTS_MESSAGE;
import static com.example.pharmacy.command.constant.PageName.MAIN_PAGE;
import static com.example.pharmacy.command.constant.PageName.REGISTRATION_PAGE;
import static com.example.pharmacy.command.constant.RequestAttributeName.FAILED;
import static com.example.pharmacy.command.constant.RequestAttributeName.USER;
import static com.example.pharmacy.command.constant.RequestParameterName.*;

public class SignUpCommand implements Command {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    static Logger logger = LogManager.getLogger();
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String dateOfBirthStr = request.getParameter(DATE_OF_BIRTH);
        UserService userService = UserServiceImpl.getInstance();
        String page;
        try {
            if (!userService.existsByLogin(login)) {
                LocalDate dateOfBirth;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                    dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
                } catch (DateTimeParseException e) {
                    throw new CommandException(e);
                }
                User user = User.newBuilder().setFirstName(firstName).setLastName(lastName).setDateOfBirth(dateOfBirth).build();
                int userId = userService.createUser(user);
                Credentials credentials = Credentials.newBuilder().setLogin(login).setPassword(password).setUserId(userId).setRole(Role.CUSTOMER).build();
                userService.createCredentials(credentials);
                request.setAttribute(USER, firstName);
                page = MAIN_PAGE;
            } else {
                logger.info(String.format("There is already a user mit login '%s", login));
                request.setAttribute(FAILED, LOGIN_ALREADY_EXISTS_MESSAGE);
                page = REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
