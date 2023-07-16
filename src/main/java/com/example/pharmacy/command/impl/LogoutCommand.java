package com.example.pharmacy.command.impl;

import com.example.pharmacy.command.Command;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.pharmacy.command.constant.PageName.INDEX_PAGE;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return INDEX_PAGE;
    }
}
