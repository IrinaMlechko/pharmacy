package com.example.pharmacy.command;

import com.example.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    String execute (HttpServletRequest request) throws CommandException;
}
