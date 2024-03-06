package com.example.gestionconference.Services.FinancialIncomesServices;

import com.example.gestionconference.Models.FinancialIncomesModels.FinancialIncomes;
import com.example.gestionconference.Models.LogisticModels.LogisticIncome;
import com.example.gestionconference.Util.MyDB;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinancialIncomesServices {
    private Connection connection;

    public FinancialIncomesServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addFinancialIncomes(FinancialIncomes financialIncomes) throws SQLException {
        String req = "INSERT INTO `financialIncomes`(`financialIncomesId`,`sponsorName`,`cashIn`,`proof`) VALUES (?,?,?,?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, financialIncomes.getFinancialIncomesId());
            stm.setString(2, financialIncomes.getSponsorName());
            stm.setInt(3, financialIncomes.getCashIn());
            stm.setBytes(4, financialIncomes.getProof());

            stm.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFinancialIncomes(FinancialIncomes financialIncomes) throws SQLException {
        String sql = "delete from financialIncomes where financialIncomesId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, financialIncomes.getFinancialIncomesId());
        preparedStatement.executeUpdate();
    }

    public void updateFinancialIncomes(FinancialIncomes financialIncomes)throws SQLException{
        String sql = "UPDATE `financialIncomes` SET `sponsorName` = ?,  `cashIn` = ?,  `proof` = ? WHERE `financialIncomesId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,financialIncomes.getSponsorName());
        preparedStatement.setInt(2, financialIncomes.getCashIn());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(financialIncomes.getProof());
        preparedStatement.setBinaryStream(3, inputStream);
        preparedStatement.setInt(4, financialIncomes.getFinancialIncomesId());
        preparedStatement.executeUpdate();
    }

    public List<FinancialIncomes> getAll() throws SQLException {
        String sql = "select * from financialIncomes";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<FinancialIncomes> financialIncomes = new ArrayList<>();
        while (rs.next()) {
            FinancialIncomes f = new FinancialIncomes();
            f.setFinancialIncomesId(rs.getInt("financialIncomesId"));
            f.setSponsorName(rs.getString("sponsorName"));
            f.setCashIn(rs.getInt("cashIn"));
            f.setProof(rs.getBytes("proof"));
            financialIncomes.add(f);
        }
        return financialIncomes;
    }

    /*public FinancialIncomes getFinancialIncomesById(int financialIncomesID) throws SQLException {
        String sql = "SELECT `financialIncomesId`,`sponsorName`,`cashIn`,`proof` FROM `financialIncomes` WHERE `financialIncomesId` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, financialIncomesID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            FinancialIncomes f = new FinancialIncomes();
            f.setFinancialIncomesId(resultSet.getInt("financialIncomesId"));
            f.setSponsorName(resultSet.getString("sponsorName"));
            f.setCashIn(resultSet.getInt("cashIn"));
            // Convert Blob to byte[]
            Blob blob = resultSet.getBlob("proof");
            int blobLength = (int) blob.length();
            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            f.setProof(blobAsBytes);
            return f;
        } else {
            return null;
        }
    }*/
}

