package com.example.gestionconference.Test.UserMain;


import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.UserServices.UserService;

import java.sql.SQLException;

public class Main {
   //generate main method
    public static void main(String[] args) {
        UserService us = new UserService();

        User u = new User();

        try {
            us.add(u);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
