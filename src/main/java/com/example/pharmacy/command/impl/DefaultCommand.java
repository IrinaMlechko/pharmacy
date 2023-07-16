package com.example.pharmacy.command.impl;

import com.example.pharmacy.command.Command;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.pharmacy.command.constant.PageName.INDEX_PAGE;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return INDEX_PAGE;
    }
}
