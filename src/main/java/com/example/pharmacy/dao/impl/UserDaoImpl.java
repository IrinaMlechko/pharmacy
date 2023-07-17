package com.example.pharmacy.dao.impl;

import com.example.pharmacy.dao.BaseDao;
import com.example.pharmacy.dao.UserDao;
import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.DaoException;
import com.example.pharmacy.mapper.Mapper;
import com.example.pharmacy.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao, BaseDao<Integer, Credentials> {
    static Logger logger = LogManager.getLogger();
    public static final String UNSUPPORTED_OPERATION_MESSAGE = "Unsupported operation";
    private static final String SELECT_PASSWORD = "SELECT password FROM pharmacy.credentials WHERE login = ?";
    private static final String SELECT_FIRST_NAME = "SELECT first_name FROM pharmacy.users us JOIN pharmacy.credentials cred ON us.id = cred.user_id WHERE login = ?";
    private static final String SELECT_LOGIN = "SELECT login FROM pharmacy.credentials WHERE login = ?";
    private static final String INSERT_USER = "INSERT INTO pharmacy.users (first_name, last_name, date_of_birth) VALUES (?, ?, ?)";
    private static final String INSERT_CREDENTIALS = "INSERT INTO pharmacy.credentials (login, password, role, user_id) VALUES (?, ?, ?, ?)";
    private static final UserDaoImpl instance = new UserDaoImpl();


    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_PASSWORD)) {
            statement.setString(1, login);
            try (var resultSet = statement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(1);
                    match = password.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public Optional<String> findUserFirstNameByLogin(String login) throws DaoException {
        Optional<String> firstName = Optional.empty();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_FIRST_NAME)) {
            statement.setString(1, login);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    firstName = Optional.of(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return firstName;
    }

    @Override
    public boolean existsByLogin(String login) throws DaoException {
        boolean match = false;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_LOGIN)) {
            statement.setString(1, login);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    match = true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public int createUser(User user) throws DaoException {
        int id = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            Mapper.mapUserToStatement(user, statement);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException("Error creating user", e);
        }
    }

    @Override
    public void createCredentials(Credentials credentials) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CREDENTIALS)) {
            Mapper.mapCredentialsToStatement(credentials, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating credentials: " + e.getLocalizedMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Credentials> findAll() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Override
    public boolean create(Credentials credentials) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Override
    public Credentials update(Credentials credentials) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
    }
}
