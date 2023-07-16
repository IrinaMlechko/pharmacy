package com.example.pharmacy.command.impl;

import com.example.pharmacy.command.Command;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.CommandException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.service.impl.MedicineServiceImpl;
import com.example.pharmacy.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.example.pharmacy.command.constant.Message.LOGIN_FAILED_MESSAGE;
import static com.example.pharmacy.command.constant.Message.NO_MEDICINES_FOUND_MESSAGE;
import static com.example.pharmacy.command.constant.PageName.INDEX_PAGE;
import static com.example.pharmacy.command.constant.PageName.MAIN_PAGE;
import static com.example.pharmacy.command.constant.RequestAttributeName.*;
import static com.example.pharmacy.command.constant.RequestParameterName.LOGIN;
import static com.example.pharmacy.command.constant.RequestParameterName.PASSWORD;
import static com.example.pharmacy.command.constant.SessionAttributeName.USER_NAME;

public class ReadAllMedicinesCommand implements Command {
    private MedicineServiceImpl medicineService = MedicineServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            List<Medicine> medicines = medicineService.findAll();
            if (!medicines.isEmpty()) {
                request.setAttribute(MEDICINES_LIST, medicines);
            } else {
                request.setAttribute(NO_MEDICINES_FOUND, NO_MEDICINES_FOUND_MESSAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return MAIN_PAGE;
    }
}
