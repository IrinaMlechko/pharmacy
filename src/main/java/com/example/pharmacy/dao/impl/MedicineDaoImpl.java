package com.example.pharmacy.dao.impl;

import com.example.pharmacy.dao.BaseDao;
import com.example.pharmacy.dao.MedicineDao;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.DaoException;
import com.example.pharmacy.mapper.Mapper;
import com.example.pharmacy.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDaoImpl implements MedicineDao, BaseDao<Integer, Medicine> {
    public static final String UNSUPPORTED_OPERATION_MESSAGE = "Unsupported operation";
    private static final String SELECT_ALL = "SELECT * FROM pharmacy.medicines";
    private static final MedicineDaoImpl instance = new MedicineDaoImpl();
    static Logger logger = LogManager.getLogger();

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
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Override
    public boolean create(Medicine medicine) throws DaoException {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Override
    public Medicine update(Medicine medicine) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation");
    }

}
