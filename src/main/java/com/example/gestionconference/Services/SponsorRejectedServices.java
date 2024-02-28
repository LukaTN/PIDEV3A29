package com.example.gestionconference.Services;



import com.example.gestionconference.Models.SponsorRejected;
import com.example.gestionconference.Util.MyDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorRejectedServices  {

    private Connection connection;

    public SponsorRejectedServices() {
        connection = MyDB.getInstance().getCnx();
    }


    public void add(SponsorRejected sponsorRejected) throws SQLException {
        String sql = "INSERT INTO sponsor_rejected (nom, email, numtel, cause) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsorRejected.getNom());
        preparedStatement.setString(2, sponsorRejected.getEmail());
        preparedStatement.setInt(3, sponsorRejected.getNumtel());
        preparedStatement.setString(4, sponsorRejected.getCause());
        preparedStatement.executeUpdate();
    }


    public void update(SponsorRejected sponsorRejected) throws SQLException {
        String sql = "UPDATE sponsor_rejected SET nom = ?, email = ?, numtel = ?, cause = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsorRejected.getNom());
        preparedStatement.setString(2, sponsorRejected.getEmail());
        preparedStatement.setInt(3, sponsorRejected.getNumtel());
        preparedStatement.setString(4, sponsorRejected.getCause());
        preparedStatement.setInt(5, sponsorRejected.getId());
        preparedStatement.executeUpdate();
    }


    public void delete(SponsorRejected sponsorRejected) throws SQLException {
        String sql = "DELETE FROM sponsor_rejected WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sponsorRejected.getId());
        preparedStatement.executeUpdate();
    }


    public List<SponsorRejected> getAll() throws SQLException {
        String sql = "SELECT * FROM sponsor_rejected";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<SponsorRejected> sponsorRejecteds = new ArrayList<>();
        while (rs.next()) {
            SponsorRejected sponsorRejected = new SponsorRejected();
            sponsorRejected.setId(rs.getInt("id"));
            sponsorRejected.setNom(rs.getString("nom"));
            sponsorRejected.setEmail(rs.getString("email"));
            sponsorRejected.setNumtel(rs.getInt("numtel"));
            sponsorRejected.setCause(rs.getString("cause"));
            sponsorRejecteds.add(sponsorRejected);
        }
        return sponsorRejecteds;
    }


    public SponsorRejected getById(int id) throws SQLException {
        String sql = "SELECT nom, email, numtel, cause FROM sponsor_rejected WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            String email = resultSet.getString("email");
            int numtel = resultSet.getInt("numtel");
            String cause = resultSet.getString("cause");

            return new SponsorRejected(id, nom, email, numtel, cause);
        } else {
            return null;
        }
    }
}
