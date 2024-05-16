package com.example.gestionconference.Services.BudgetServices;

import com.example.gestionconference.Models.BudgetModels.Incomes;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomesServices {
    private Connection connection;

    public IncomesServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addIncomes(Incomes incomes) throws SQLException {
        String req = "INSERT INTO `incomes`(`incomesId`,`fromWhat`,`incAmmount`) VALUES (?,?,?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, incomes.getIncomesId());
            stm.setString(2, incomes.getFromWhat());
            stm.setInt(3, incomes.getIncAmmount());

            stm.executeUpdate();
        }
    }

    public void deleteIncomes(Incomes incomes) throws SQLException {
        String sql = "delete from incomes where incomesId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, incomes.getIncomesId());
            preparedStatement.executeUpdate();
        }
    }

    public void updateIncomes(Incomes incomes) throws SQLException {
        String sql = "UPDATE `incomes` SET `fromWhat` = ?,  `incAmmount` = ? WHERE `incomesId` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, incomes.getFromWhat());
            preparedStatement.setInt(2, incomes.getIncAmmount());
            preparedStatement.setInt(3, incomes.getIncomesId());
            preparedStatement.executeUpdate();
        }
    }

    public List<Incomes> getAll() throws SQLException {
        String sql = "select * from incomes";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Incomes> incomes = new ArrayList<>();
            while (rs.next()) {
                Incomes i = new Incomes();
                i.setIncomesId(rs.getInt("incomesId"));
                i.setFromWhat(rs.getString("fromWhat"));
                i.setIncAmmount(rs.getInt("incAmmount"));


                incomes.add(i);
            }
            return incomes;
        }
    }

    public Incomes getIncomesById(int incomesId) throws SQLException {
        String sql = "SELECT `fromWhat`, `incAmmount` FROM `incomes` WHERE `incomesId` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, incomesId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String fromWhat = resultSet.getString("fromWhat");
                    int incAmmount = resultSet.getInt("incAmmount");

                    return new Incomes(fromWhat, incAmmount);
                } else {
                    return null;
                }
            }
        }
    }
}
