package com.example.gestionconference.Services;


import com.example.gestionconference.Models.CauseOfRejection;
import com.example.gestionconference.Util.MyDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CauseOfRejectionServices  {

    private Connection connection;

    public CauseOfRejectionServices() {
        connection = MyDB.getInstance().getCnx();
    }


    public void add(CauseOfRejection causeOfRejection) throws SQLException {
        String sql = "INSERT INTO cause_of_rejection (nom, email, numtel, cause, sujet_cause, description) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, causeOfRejection.getNom());
        preparedStatement.setString(2, causeOfRejection.getEmail());
        preparedStatement.setInt(3, causeOfRejection.getNumtel());
        preparedStatement.setString(4, causeOfRejection.getCause());
        preparedStatement.setString(5, causeOfRejection.getSujetCause().toString());
        preparedStatement.setString(6, causeOfRejection.getDescription());
        preparedStatement.executeUpdate();
    }


    public void update(CauseOfRejection causeOfRejection) throws SQLException {
        String sql = "UPDATE cause_of_rejection SET nom = ?, email = ?, numtel = ?, cause = ?, sujet_cause = ?, description = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, causeOfRejection.getNom());
        preparedStatement.setString(2, causeOfRejection.getEmail());
        preparedStatement.setInt(3, causeOfRejection.getNumtel());
        preparedStatement.setString(4, causeOfRejection.getCause());
        preparedStatement.setString(5, causeOfRejection.getSujetCause().toString());
        preparedStatement.setString(6, causeOfRejection.getDescription());
        preparedStatement.setInt(7, causeOfRejection.getId());
        preparedStatement.executeUpdate();
    }


    public void delete(CauseOfRejection causeOfRejection) throws SQLException {
        String sql = "DELETE FROM cause_of_rejection WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, causeOfRejection.getId());
        preparedStatement.executeUpdate();
    }


    public List<CauseOfRejection> getAll() throws SQLException {
        String sql = "SELECT * FROM cause_of_rejection";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<CauseOfRejection> causeOfRejections = new ArrayList<>();
        while (rs.next()) {
            CauseOfRejection causeOfRejection = new CauseOfRejection();
            causeOfRejection.setId(rs.getInt("id"));
            causeOfRejection.setNom(rs.getString("nom"));
            causeOfRejection.setEmail(rs.getString("email"));
            causeOfRejection.setNumtel(rs.getInt("numtel"));
            causeOfRejection.setCause(rs.getString("cause"));
          //  causeOfRejection.setSujetCause(SujetCause.valueOf(rs.getString("sujet_cause")));
            causeOfRejection.setDescription(rs.getString("description"));
            causeOfRejections.add(causeOfRejection);
        }
        return causeOfRejections;
    }


//    public CauseOfRejection getById(int id) throws SQLException {
//        String sql = "SELECT nom, email, numtel, cause, sujet_cause, description FROM cause_of_rejection WHERE id = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setInt(1, id);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        if (resultSet.next()) {
//            String nom = resultSet.getString("nom");
//            String email = resultSet.getString("email");
//            int numtel = resultSet.getInt("numtel");
//            String cause = resultSet.getString("cause");
//         //   SujetCause sujetCause = SujetCause.valueOf(resultSet.getString("sujet_cause"));
//            String description = resultSet.getString("description");
//
//            return new CauseOfRejection(id, nom, email, numtel, cause, sujetCause, description);
//        } else {
//            return null;
//        }
//    }
}

