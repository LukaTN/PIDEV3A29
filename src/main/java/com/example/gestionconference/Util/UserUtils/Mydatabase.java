package com.example.gestionconference.Util.UserUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mydatabase {
    private final String URL = "jdbc:mysql://localhost:3306/conferatest";
    private final String USER = "root";
    private final String PASSWORD = "";
    private Connection connection;
    private static Mydatabase instance;
    private Mydatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
    public static Mydatabase getInstance() {
        if(instance == null)
            instance = new Mydatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

