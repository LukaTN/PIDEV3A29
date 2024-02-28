package com.example.gestionconference.Services;



import com.example.gestionconference.Models.SponsorAccepted;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorAcceptedServices {

    private Connection connection;

    public SponsorAcceptedServices() {
        connection = MyDB.getInstance().getCnx();
    }


    public void add(SponsorAccepted sponsorAccepted) throws SQLException {
        String sql = "INSERT INTO sponsor_accepted (nom, email, numtel, budget) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsorAccepted.getNom());
        preparedStatement.setString(2, sponsorAccepted.getEmail());
        preparedStatement.setInt(3, sponsorAccepted.getNumtel());
        preparedStatement.setDouble(4, sponsorAccepted.getBudget());
        preparedStatement.executeUpdate();
    }


    public void update(SponsorAccepted sponsorAccepted) throws SQLException {
        String sql = "UPDATE sponsor_accepted SET nom = ?, email = ?, numtel = ?, budget = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsorAccepted.getNom());
        preparedStatement.setString(2, sponsorAccepted.getEmail());
        preparedStatement.setInt(3, sponsorAccepted.getNumtel());
        preparedStatement.setDouble(4, sponsorAccepted.getBudget());
        preparedStatement.setInt(5, sponsorAccepted.getId());
        preparedStatement.executeUpdate();
    }


    public void delete(SponsorAccepted sponsorAccepted) throws SQLException {
        String sql = "DELETE FROM sponsor_accepted WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sponsorAccepted.getId());
        preparedStatement.executeUpdate();
    }


    public List<SponsorAccepted> getAll() throws SQLException {
        String sql = "SELECT * FROM sponsor_accepted";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<SponsorAccepted> sponsors = new ArrayList<>();
        while (rs.next()) {
            SponsorAccepted sponsor = new SponsorAccepted();
            sponsor.setId(rs.getInt("id"));
            sponsor.setNom(rs.getString("nom"));
            sponsor.setEmail(rs.getString("email"));
            sponsor.setNumtel(rs.getInt("numtel"));
            sponsor.setBudget(rs.getDouble("budget"));
            sponsors.add(sponsor);
        }
        return sponsors;
    }


    public SponsorAccepted getById(int id) throws SQLException {
        String sql = "SELECT nom, email, numtel, budget FROM sponsor_accepted WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            String email = resultSet.getString("email");
            int numtel = resultSet.getInt("numtel");
            double budget = resultSet.getDouble("budget");

            return new SponsorAccepted(id, nom, email, numtel, budget);
        } else {
            return null;
        }
    }
}
