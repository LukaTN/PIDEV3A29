package com.example.gestionconference.Services.LogisticServices;

import com.example.gestionconference.Models.LogisticModels.Logistic;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogisticServices {
    private Connection connection;

    public LogisticServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addLogistic(Logistic logistic) throws SQLException {
        String req = "INSERT INTO logistic(`ProvidedLog`, `quantity`) VALUES (?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setString(1, logistic.getProvidedLog());
            stm.setInt(2, logistic.getQuantity());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteLogistic(Logistic logistic) throws SQLException {
        String sql = "DELETE FROM Logistic WHERE `logisticId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, logistic.getLogisticId());
        preparedStatement.executeUpdate();
    }

    public void updateLogistic(Logistic logistic) throws SQLException {
        String sql = "UPDATE logistic SET `ProvidedLog` = ?, `quantity` = ? WHERE `logisticId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, logistic.getProvidedLog());
        preparedStatement.setInt(2, logistic.getQuantity());
        preparedStatement.setInt(3, logistic.getLogisticId());
        preparedStatement.executeUpdate();
    }

    public List<Logistic> getAll() throws SQLException {
        String sql = "SELECT * FROM logistic";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Logistic> logistics = new ArrayList<>();
        while (rs.next()) {
            Logistic l = new Logistic();
            l.setLogisticId(rs.getInt("logisticId"));
            l.setProvidedLog(rs.getString("ProvidedLog"));
            l.setQuantity(rs.getInt("quantity"));
            logistics.add(l);
        }
        return logistics;
    }

    public Logistic getLogisticById(int logisticId) throws SQLException {
        String sql = "SELECT * FROM Logistics WHERE `logisticId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, logisticId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Logistic l = new Logistic();
            l.setLogisticId(resultSet.getInt("logisticId"));
            l.setProvidedLog(resultSet.getString("ProvidedLog"));
            l.setQuantity(resultSet.getInt("quantity"));
            return l;
        } else {
            return null;
        }
    }
}