package com.example.pharmacy.command;

import com.example.pharmacy.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignUpCommand()),
    READ_ALL_MEDICINES (new ReadAllMedicinesCommand()),
    DEFAULT(new DefaultCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        Command command;
        if (commandStr != null) {
            try {
                CommandType commandType = CommandType.valueOf(commandStr.toUpperCase().replace("_", ""));
                command = commandType.command;
            } catch (IllegalArgumentException e) {
                command = DEFAULT.command;
            }
        } else {
            command = DEFAULT.command;
        }
        return command;
    }
}
