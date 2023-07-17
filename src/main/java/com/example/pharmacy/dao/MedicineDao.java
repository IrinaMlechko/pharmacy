package com.example.pharmacy.dao;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.DaoException;

import java.util.List;

public interface MedicineDao extends BaseDao<Integer, Medicine> {

    List<Medicine> findAll() throws DaoException;

}
