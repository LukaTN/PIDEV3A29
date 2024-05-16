package com.example.gestionconference.Services.LogisticServices;


import com.example.gestionconference.Models.LogisticModels.LogisticIncome;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogisticIncomeServices {
    private Connection connection;

    public LogisticIncomeServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addLogisticIncome(LogisticIncome logisticIncome) throws SQLException {
        String req = "INSERT INTO logisticIncome (`logSponsorName`, `LogIncomeQty`, `logProof`, `logisticId`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setString(1, logisticIncome.getLogSponsorName());
            stm.setInt(2, logisticIncome.getLogIncomeQty());


            stm.setBytes(3, logisticIncome.getLogProof());
            stm.setInt(4, logisticIncome.getLogisticId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteLogistic(LogisticIncome logisticIncome) throws SQLException {
        String sql = "DELETE FROM logisticIncome WHERE `logIncomeId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, logisticIncome.getLogIncomeId());
        preparedStatement.executeUpdate();
    }

    public void updateLogistic(LogisticIncome logisticIncome) throws SQLException, FileNotFoundException {
        String sql = "UPDATE logisticIncome SET `logSponsorName` = ?, `LogIncomeQty` = ?, `logProof` = ? WHERE `logIncomeId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, logisticIncome.getLogSponsorName());
        preparedStatement.setInt(2, logisticIncome.getLogIncomeQty());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(logisticIncome.getLogProof());
        preparedStatement.setBinaryStream(3, inputStream);

        preparedStatement.executeUpdate();
    }

    public List<LogisticIncome> getAll() throws SQLException {
        String sql = "SELECT * FROM logisticIncome";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<LogisticIncome> logisticIncomes = new ArrayList<>();
        while (rs.next()) {
            LogisticIncome li = new LogisticIncome();
            li.setLogSponsorName(rs.getString("logSponsorName"));
            li.setLogIncomeQty(rs.getInt("logIncomeQty"));
            // Convert Blob to byte[]
            li.setLogProof(rs.getBytes("logProof"));
            logisticIncomes.add(li);
        }
        return logisticIncomes;
    }

    public LogisticIncome getLogisticById(int logIncomeId) throws SQLException {
        String sql = "SELECT * FROM logisticIncome WHERE `logIncomeId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, logIncomeId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            LogisticIncome li = new LogisticIncome();
            li.setLogIncomeId(resultSet.getInt("logIncomeId"));
            li.setLogSponsorName(resultSet.getString("logSponsorName"));
            li.setLogIncomeQty(resultSet.getInt("logIncomeQty"));
            // Convert Blob to byte[]
            Blob blob = resultSet.getBlob("logProof");
            int blobLength = (int) blob.length();
            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            li.setLogProof(blobAsBytes);
            return li;
        } else {
            return null;
        }
    }

    /*public LogisticIncome getLogisticBySourceId(int logisticId) throws SQLException {
        String sql = "SELECT * FROM logisticIncome WHERE `logisticId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, logisticId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            LogisticIncome li = new LogisticIncome();
            li.setLogIncomeId(resultSet.getInt("logIncomeId"));
            li.setLogSponsorName(resultSet.getString("logSponsorName"));
            li.setLogIncomeQty(resultSet.getInt("logIncomeQty"));
            // Convert Blob to byte[]
            Blob blob = resultSet.getBlob("logProof");
            int blobLength = (int) blob.length();
            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            li.setLogProof(blobAsBytes);
            return li;
        } else {
            return null;
        }
    }*/

    public ObservableList<LogisticIncome> getLogisticBySourceId(int logisticId) throws SQLException {
        String req1 = "SELECT * FROM logisticIncome WHERE logisticId = ?";
        PreparedStatement st = connection.prepareStatement(req1);
        st.setInt(1, logisticId); // Setting the parameter value

        // Execute the query
        ResultSet res = st.executeQuery(); // Remove req1 from executeQuery

        // Create an ObservableList to store logisticIncomes
        ObservableList<LogisticIncome> logisticIncomes = FXCollections.observableArrayList();

        // Iterate over the result set and populate logisticIncomes list
        while (res.next()) {
            LogisticIncome logisticIncome = new LogisticIncome();
            logisticIncome.setLogIncomeId(res.getInt("logIncomeId"));
            logisticIncome.setLogSponsorName(res.getString("topicName"));
            logisticIncome.setLogIncomeQty(res.getInt("incomeQty"));


            logisticIncome.setLogisticId(res.getInt("logisticId"));

            logisticIncomes.add(logisticIncome);
        }

        // Close the statement and result set
        st.close();
        res.close();
        System.out.println(logisticIncomes);
        // Return the list of topics
        return logisticIncomes;
    }
}
