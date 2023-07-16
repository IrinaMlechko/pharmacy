package com.example.pharmacy.mapper;

import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.util.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

public class Mapper {
    public static User mapUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(DBFields.USER_ID);
        String firstName = resultSet.getString(DBFields.USER_FIRST_NAME);
        String lastName = resultSet.getString(DBFields.USER_LAST_NAME);
        LocalDate dateOfBirth = resultSet.getDate(DBFields.USER_DATE_OF_BIRTH).toLocalDate();
        return User.newBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setDateOfBirth(dateOfBirth)
                .build();
    }

    public static Credentials mapCredentialsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(DBFields.CREDENTIALS_ID);
        String login = resultSet.getString(DBFields.CREDENTIALS_LOGIN);
        String password = resultSet.getString(DBFields.CREDENTIALS_PASSWORD);
        Role role = Role.valueOf(resultSet.getString(DBFields.CREDENTIALS_ROLE));
        int userId = resultSet.getInt(DBFields.CREDENTIALS_USER_ID);
        return Credentials.newBuilder()
                .setId(id)
                .setLogin(login)
                .setPassword(password)
                .setRole(role)
                .setUserId(userId)
                .build();
    }

    public static void mapUserToResultSet(User user, ResultSet resultSet) throws SQLException {
        resultSet.updateInt(DBFields.USER_ID, user.getId());
        resultSet.updateString(DBFields.USER_FIRST_NAME, user.getFirstName());
        resultSet.updateString(DBFields.USER_LAST_NAME, user.getLastName());
        resultSet.updateDate(DBFields.USER_DATE_OF_BIRTH, java.sql.Date.valueOf(user.getDateOfBirth()));
    }

    public static void mapCredentialsToResultSet(Credentials credentials, ResultSet resultSet) throws SQLException {
        resultSet.updateInt(DBFields.CREDENTIALS_ID, credentials.getId());
        resultSet.updateString(DBFields.CREDENTIALS_LOGIN, credentials.getLogin());
        resultSet.updateString(DBFields.CREDENTIALS_PASSWORD, credentials.getPassword());
        resultSet.updateString(DBFields.CREDENTIALS_ROLE, credentials.getRole().toString());
        resultSet.updateInt(DBFields.CREDENTIALS_USER_ID, credentials.getUserId());
    }

    public static void mapUserToStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setDate(3, java.sql.Date.valueOf(user.getDateOfBirth()));
    }

    public static void mapCredentialsToStatement(Credentials credentials, PreparedStatement statement) throws SQLException {
        statement.setString(1, credentials.getLogin());
        statement.setString(2, credentials.getPassword());
        statement.setObject(3, credentials.getRole(), Types.OTHER);
        statement.setInt(4, credentials.getUserId());
    }
}
