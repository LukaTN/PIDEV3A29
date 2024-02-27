package test;

import models.User;
import services.UserService;

import java.sql.SQLException;

public class Main {
   //generate main method
    public static void main(String[] args) {
        UserService us = new UserService();

        User u = new User("1", "zayeti","hamza", "zayatihamza@gmail.com ", "0000",29699660);

        try {
            us.add(u);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
