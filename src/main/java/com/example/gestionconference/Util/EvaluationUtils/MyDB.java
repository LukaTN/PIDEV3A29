package com.example.gestionconference.Util.EvaluationUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyDB {


    final String URL = "jdbc:mysql://localhost:3306/pidev";
    final String USER = "root";
    final  String PWD = "";

    private static MyDB instance;


    private Connection cnx;

    public MyDB(){

        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }



    public static MyDB getInstance(){

        if (instance == null){
            instance = new MyDB ();

        } return  instance ;

    }



    public Connection getCnx()
    {
        return cnx;
    }
}

