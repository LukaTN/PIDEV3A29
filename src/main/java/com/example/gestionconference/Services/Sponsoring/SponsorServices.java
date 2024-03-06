package com.example.gestionconference.Services.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Util.EvaluationUtils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorServices {

    private Connection connection;

    public SponsorServices() {
        connection = MyDB.getInstance().getCnx();
    }


    public void add(Sponsor sponsor) throws SQLException {
        String sql = "INSERT INTO sponsor (nom, email, numtel, status) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsor.getNom());
        preparedStatement.setString(2, sponsor.getEmail());
        preparedStatement.setString(3, sponsor.getNumtel() );
        preparedStatement.setString(4, sponsor.getStatus());
        preparedStatement.executeUpdate();
    }


    public void update(Sponsor sponsor) throws SQLException {
        String sql = "UPDATE sponsor SET nom = ?, email = ?, numtel = ?, status = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsor.getNom());
        preparedStatement.setString(2, sponsor.getEmail());
        preparedStatement.setInt(3, Integer.parseInt(sponsor.getNumtel()));
        preparedStatement.setString(4, sponsor.getStatus());
        preparedStatement.setInt(4, sponsor.getId());
        preparedStatement.executeUpdate();
    }


    public void delete(Sponsor sponsor) throws SQLException {
        String sql = "DELETE FROM sponsor WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sponsor.getId());
        preparedStatement.executeUpdate();
    }


    public List<Sponsor> getAll() throws SQLException {
        String sql = "SELECT * FROM sponsor";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Sponsor> sponsors = new ArrayList<>();
        while (rs.next()) {
            Sponsor sponsor = new Sponsor();
            sponsor.setId(rs.getInt("id"));
            sponsor.setNom(rs.getString("nom"));
            sponsor.setEmail(rs.getString("email"));
            sponsor.setNumtel(rs.getString("numtel"));
            sponsors.add(sponsor);
        }
        return sponsors;
    }


    public Sponsor getById(int id) throws SQLException {
        String sql = "SELECT nom, email, numtel FROM sponsor WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            String email = resultSet.getString("email");
            String numtel = resultSet.getString("numtel");

            return new Sponsor(id, nom, email, numtel);
        } else {
            return null;
        }
    }
}
