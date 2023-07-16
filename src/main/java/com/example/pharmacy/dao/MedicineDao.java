package com.example.pharmacy.dao;

import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface MedicineDao extends BaseDao <Integer, Medicine> {

    List<Medicine> findAll() throws DaoException;

}
