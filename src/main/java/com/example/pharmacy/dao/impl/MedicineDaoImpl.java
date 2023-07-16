package com.example.pharmacy.dao.impl;

import com.example.pharmacy.dao.BaseDao;
import com.example.pharmacy.dao.MedicineDao;
import com.example.pharmacy.dao.UserDao;
import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.Medicine;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicineDaoImpl implements MedicineDao, BaseDao<Integer, Medicine> {
    static Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL = "SELECT + FROM pharmacy.medicines";
    private static final MedicineDaoImpl instance = new MedicineDaoImpl();

    private MedicineDaoImpl() {
    }

    public static MedicineDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Medicine> findAll() throws DaoException {
        List<Medicine> medicines = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_ALL)) {
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    medicines.add(Mapper.mapMedicineFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return medicines;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean create(Medicine medicine) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public Medicine update(Medicine medicine) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation");
    }

}
