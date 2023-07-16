package com.example.pharmacy.service;

import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    boolean authenticate(String userName, String password) throws ServiceException;

    Optional<String> findName(String login) throws ServiceException;

    boolean existsByLogin(String login) throws ServiceException;

    int createUser(User user) throws ServiceException;

    void createCredentials(Credentials credentials) throws ServiceException;
}
