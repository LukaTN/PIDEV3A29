package com.example.gestionconference.Services.BudgetServices;

import com.example.gestionconference.Models.BudgetModels.Expenses;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpensesServices {
    private Connection connection;

    public ExpensesServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addExpenses(Expenses expenses) throws SQLException {
        String req = "INSERT INTO `expenses`(`expensesId`,`onWhat`,`expAmmount`) VALUES (?,?,?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, expenses.getExpensesId());
            stm.setString(2, expenses.getOnWhat());
            stm.setInt(3, expenses.getExpAmmount());

            stm.executeUpdate();
        }
    }

    public void deleteExpenses(Expenses expenses) throws SQLException {
        String sql = "delete from expenses where expensesId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, expenses.getExpensesId());
            preparedStatement.executeUpdate();
        }
    }

    public void updateExpenses(Expenses expenses) throws SQLException {
        String sql = "UPDATE `expenses` SET `onWhat` = ?,  `expAmmount` = ? WHERE `expensesId` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, expenses.getOnWhat());
            preparedStatement.setInt(2, expenses.getExpAmmount());
            preparedStatement.setInt(3, expenses.getExpensesId());
            preparedStatement.executeUpdate();
        }
    }

    public List<Expenses> getAll() throws SQLException {
        String sql = "select * from expenses";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Expenses> expenses = new ArrayList<>();
            while (rs.next()) {
                Expenses e = new Expenses();
                e.setExpensesId(rs.getInt("expensesId"));
                e.setOnWhat(rs.getString("onWhat"));
                e.setExpAmmount(rs.getInt("expAmmount"));


                expenses.add(e);
            }
            return expenses;
        }
    }

    public Expenses getById(int expensesId) throws SQLException {
        String sql = "SELECT `onWhat`, `expAmmount` FROM `expenses` WHERE `expensesId` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, expensesId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String onWhat = resultSet.getString("onWhat");
                    int expAmmount = resultSet.getInt("expAmmount");

                    return new Expenses(onWhat, expAmmount);
                } else {
                    return null;
                }
            }
        }
    }
}
