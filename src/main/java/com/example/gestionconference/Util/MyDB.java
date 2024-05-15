package com.example.gestionconference.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    final String URL = "jdbc:mysql://192.168.1.12/conf";
    final String USER = "root";
    final String password = "";
    private static MyDB instance;


    private Connection cnx;
    private MyDB(){
        try {
            cnx = DriverManager.getConnection(URL,USER,password);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static MyDB getInstance(){
        if (instance == null){
            instance = new MyDB();
        } return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
