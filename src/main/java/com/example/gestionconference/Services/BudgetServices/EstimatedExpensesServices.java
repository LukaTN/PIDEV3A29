package com.example.gestionconference.Services.BudgetServices;

import com.example.gestionconference.Models.BudgetModels.EstimatedExpenses;

import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstimatedExpensesServices {
    private Connection connection;

    public EstimatedExpensesServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addEstimatedExpenses(EstimatedExpenses estimatedExpenses) throws SQLException {
        String req = "INSERT INTO `estimatedExpenses`(`estimatedExpensesId`,`ExpensesWay`,`pessimisticExpenses`,`realisticExpenses`,`optimisticExpenses`) VALUES (?,?,?,?,?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, estimatedExpenses.getEstimatedExpensesId());
            stm.setString(2, estimatedExpenses.getExpensesWay());
            stm.setDouble(3, estimatedExpenses.getPessimisticExpenses());
            stm.setDouble(4, estimatedExpenses.getRealisticExpenses());
            stm.setDouble(5, estimatedExpenses.getOptimisticExpenses());

            stm.executeUpdate();
        }
    }


    public void deleteEstimatedExpenses(EstimatedExpenses estimatedExpenses) throws SQLException {
        String sql = "delete from estimatedExpenses where estimatedExpensesId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, estimatedExpenses.getEstimatedExpensesId());
            preparedStatement.executeUpdate();
        }
    }

    public void updateEstimatedExpenses(EstimatedExpenses estimatedExpenses)  throws SQLException {
        String sql = "UPDATE `estimatedExpenses` SET `expensesWay` = ?,  `pessimisticExpenses` = ?,  `realisticExpenses` = ?,  `optimisticExpenses` = ? WHERE `estimatedExpensesId` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, estimatedExpenses.getExpensesWay());
            preparedStatement.setDouble(2, estimatedExpenses.getPessimisticExpenses());
            preparedStatement.setDouble(3, estimatedExpenses.getRealisticExpenses());
            preparedStatement.setDouble(4, estimatedExpenses.getOptimisticExpenses());
            preparedStatement.setInt(5,estimatedExpenses.getEstimatedExpensesId());
            preparedStatement.executeUpdate();
        }
    }

    public List<EstimatedExpenses> getAll() throws SQLException {
        String sql = "select * from estimatedExpenses";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<EstimatedExpenses> estimatedExpenses = new ArrayList<>();
            while (rs.next()) {
                EstimatedExpenses ee = new EstimatedExpenses();
                ee.setEstimatedExpensesId(rs.getInt("estimatedExpensesId"));
                ee.setExpensesWay(rs.getString("expensesWay"));
                ee.setPessimisticExpenses(rs.getDouble("PessimisticExpenses"));
                ee.setRealisticExpenses(rs.getDouble("realisticExpenses"));
                ee.setOptimisticExpenses(rs.getDouble("optimisticExpenses"));


                estimatedExpenses.add(ee);
            }
            return estimatedExpenses;
        }
    }


}

