package com.example.gestionconference.Services;


import com.example.gestionconference.Models.Status;
import com.example.gestionconference.Models.Venue;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueServices {
    private Connection connection;

    public VenueServices() {
        connection = MyDB.getInstance().getCnx();
    }




    public void addVenue(Venue venue) throws SQLException {
        String req = "INSERT INTO `venue`(`id`,`venueName`,`email`,`phone`,`city`,`adresse`,`output`,`status`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(req);
            stm.setInt(1,venue.getId());
            stm.setString(2,venue.getVenueName());
            stm.setString(3,venue.getEmail());
            stm.setString(4,venue.getPhone());
            stm.setString(5,venue.getCity());
            stm.setString(6,venue.getAdresse());
            stm.setString(7,venue.getOutput());
            stm.setString(8, venue.getStatus().getStatus());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteVenue(Venue venue) throws SQLException {
        String sql = "delete from venue where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, venue.getId());
        preparedStatement.executeUpdate();
    }
    public void updateVenue(Venue venue)throws SQLException{
        String sql = "UPDATE `venue` SET `venueName` = ?,  `email` = ?,  `phone` = ?,  `city` = ?,  `adresse` = ? ,  `output` = ?,  `status` = ? WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,venue.getVenueName());
        preparedStatement.setString(2,venue.getEmail());
        preparedStatement.setString(3,venue.getPhone());
        preparedStatement.setString(4,venue.getCity());
        preparedStatement.setString(5,venue.getAdresse());
        preparedStatement.setString(6,venue.getOutput());
        preparedStatement.setString(7,venue.getStatus().getStatus());
        preparedStatement.setInt(8,venue.getId());
        preparedStatement.executeUpdate();
    }




    public List<Venue> getAllVenue() throws SQLException {
        String sql = "select * from venue";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Venue> venues = new ArrayList<>();
        while (rs.next()) {
            Venue v = new Venue();
            v.setId(rs.getInt("id"));
            v.setVenueName(rs.getString("venueName"));
            v.setEmail(rs.getString("email")); // Correction ici
            v.setPhone(rs.getString("phone")); // Correction ici
            v.setCity(rs.getString("city"));
            v.setAdresse(rs.getString("adresse"));
            v.setOutput(rs.getString("output"));
            boolean status = rs.getBoolean("status"); // Correction ici

            // Convertit le statut en "approved" ou "rejected"
            v.setStatus(Status.valueOf(status ? "approved" : "rejected"));

            venues.add(v);
        }
        return venues;
    }


    public Venue getVenueById(int idVenue) throws SQLException {
        String sql = "SELECT `id`,`venueName`,`email`,`phone`,`city`,`adresse`,`output`,`status` FROM `venue` WHERE `id` = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idVenue);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String venueName = resultSet.getString("venueName");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String city = resultSet.getString("city");
            String adresse = resultSet.getString("adresse");
            String output = resultSet.getString("output");
            Status status = resultSet.getString("status");

            Venue v = new Venue(venueName, email, phone, city, adresse, output, status);
            System.out.println(v);
            return v;
        } else {
            return null;
        }
    }

}
