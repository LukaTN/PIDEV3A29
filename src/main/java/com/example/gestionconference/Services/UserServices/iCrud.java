package com.example.gestionconference.Services.UserServices;

import java.sql.SQLException;
import java.util.List;

public interface iCrud<T> {


        List<T> getAll() throws SQLException;
        boolean add(T t) throws SQLException;
        boolean delete(T t) throws SQLException;
        boolean delete(String username) throws SQLException;
        boolean update(T t) throws SQLException;

    }


