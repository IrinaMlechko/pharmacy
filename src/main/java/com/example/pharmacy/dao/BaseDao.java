package com.example.pharmacy.dao;

import com.example.pharmacy.entity.BaseEntity;
import com.example.pharmacy.exception.DaoException;

import java.util.List;

public interface BaseDao<K, T extends BaseEntity> {
    List<T> findAll() throws DaoException;

    boolean delete(K id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;

}
