package com.example.gestionconference.Services.BudgetServices;

import com.example.gestionconference.Models.BudgetModels.EstimatedIncomes;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstimatedIncomesServices {
    private Connection connection;

    public EstimatedIncomesServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addEstimatedIncomes(EstimatedIncomes estimatedIncomes) throws SQLException {
        String req = "INSERT INTO `estimatedIncomes`(`estimatedIncomesId`,`incomeSource`,`pessimisticIncome`,`realisticIncome`,`optimisticIncome`) VALUES (?,?,?,?,?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, estimatedIncomes.getEstimatedIncomesId());
            stm.setString(2, estimatedIncomes.getIncomeSource());
            stm.setDouble(3, estimatedIncomes.getPessimisticIncome());
            stm.setDouble(4, estimatedIncomes.getRealisticIncome());
            stm.setDouble(5, estimatedIncomes.getOptimisticIncome());

            stm.executeUpdate();
        }
    }


    public void deleteEstimatedIncomes(EstimatedIncomes estimatedIncomes) throws SQLException {
        String sql = "delete from estimatedIncomes where estimatedIncomesId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, estimatedIncomes.getEstimatedIncomesId());
            preparedStatement.executeUpdate();
        }
    }

    public void updateEstimatedIncomes(EstimatedIncomes estimatedIncomes) throws SQLException {
        String sql = "UPDATE `estimatedIncomes` SET `incomeSource` = ?,  `pessimisticIncome` = ?,  `realisticIncome` = ?,  `optimisticIncome` = ? WHERE `estimatedIncomesId` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, estimatedIncomes.getIncomeSource());
            preparedStatement.setDouble(2, estimatedIncomes.getPessimisticIncome());
            preparedStatement.setDouble(3, estimatedIncomes.getRealisticIncome());
            preparedStatement.setDouble(4, estimatedIncomes.getOptimisticIncome());
            preparedStatement.setInt(5,estimatedIncomes.getEstimatedIncomesId());
            preparedStatement.executeUpdate();
        }
    }

    public List<EstimatedIncomes> getAll() throws SQLException {
        String sql = "select * from estimatedIncomes";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<EstimatedIncomes> estimatedIncomes = new ArrayList<>();
            while (rs.next()) {
                EstimatedIncomes ei = new EstimatedIncomes();
                ei.setEstimatedIncomesId(rs.getInt("estimatedIncomesId"));
                ei.setIncomeSource(rs.getString("incomeSource"));
                ei.setPessimisticIncome(rs.getDouble("PessimisticIncome"));
                ei.setRealisticIncome(rs.getDouble("realisticIncome"));
                ei.setOptimisticIncome(rs.getDouble("optimisticIncome"));


                estimatedIncomes.add(ei);
            }
            return estimatedIncomes;
        }
    }


}
