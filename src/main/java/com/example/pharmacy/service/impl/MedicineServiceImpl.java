package com.example.pharmacy.service.impl;

import com.example.pharmacy.dao.impl.MedicineDaoImpl;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.DaoException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.service.MedicineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
        MedicineDaoImpl medicineDao = MedicineDaoImpl.getInstance();
        List<Medicine> medicines;
        try {
            medicines = medicineDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return medicines;
    }

}
