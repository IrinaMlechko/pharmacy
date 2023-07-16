package com.example.pharmacy.service.impl;

import com.example.pharmacy.dao.impl.MedicineDaoImpl;
import com.example.pharmacy.dao.impl.UserDaoImpl;
import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.DaoException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.util.PasswordEncryptor;
import com.example.pharmacy.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class MedicineServiceImpl implements MedicineService {
    static Logger logger = LogManager.getLogger();
    private static MedicineServiceImpl instance;

    private MedicineServiceImpl() {
    }

    public static MedicineServiceImpl getInstance() {

        if (instance == null) {
            instance = new MedicineServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Medicine> findAll() throws ServiceException {
        logger.info("Get all medicines");
        MedicineDaoImpl userDao = MedicineDaoImpl.getInstance();
        List<Medicine>  medicines;
        try {
            medicines = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return medicines;
    }

}
