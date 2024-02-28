package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Participant;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantServices {

    private Connection connection;

    public ParticipantServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public void addParticipant(Participant participant) throws SQLException {
        String req = "INSERT INTO `participant`(`id`,`nom`,`cin`,`DateN`,`numTel`,`email`) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, participant.getId());
            stm.setString(2, participant.getNom());
            stm.setString(3, participant.getCin());
            stm.setDate(4, participant.getDateN());
            stm.setString(5, participant.getNumTel());
            stm.setString(6, participant.getEmail());

            stm.executeUpdate();
        }
    }

    public void delete(Participant participant) throws SQLException {
        String sql = "delete from participant where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, participant.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void updateParticipant(Participant participant) throws SQLException {
        String sql = "UPDATE `participant` SET `nom` = ?,  `cin` = ?,  `dateN` = ?,  `numTel` = ?,  `email` = ? WHERE `id` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, participant.getNom());
            preparedStatement.setString(2, participant.getCin());
            preparedStatement.setDate(3, participant.getDateN());
            preparedStatement.setString(4, participant.getNumTel());
            preparedStatement.setString(5, participant.getEmail());
            preparedStatement.setInt(6, participant.getId());
            preparedStatement.executeUpdate();
        }
    }

    public List<Participant> getAll() throws SQLException {
        String sql = "select * from participant";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Participant> participants = new ArrayList<>();
            while (rs.next()) {
                Participant u = new Participant();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setCin(rs.getString("cin"));
                u.setDateN(rs.getDate("dateN"));
                u.setNumTel(rs.getString("numTel"));
                u.setEmail(rs.getString("email"));

                participants.add(u);
            }
            return participants;
        }
    }

    public Participant getById(int idParticipant) throws SQLException {
        String sql = "SELECT `nom`, `cin`, `dateN`, `numTel`, `email` FROM `participant` WHERE `id` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idParticipant);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String cin = resultSet.getString("cin");
                    String numTel = resultSet.getString("numTel");
                    String email = resultSet.getString("email");
                    Date dateN = resultSet.getDate("dateN");
                    return new Participant(nom, cin, dateN, numTel, email);
                } else {
                    return null;
                }
            }
        }
    }
    public Participant searchByName(String name) {
        String sql = "SELECT `nom`, `cin`, `dateN`, `numTel`, `email` FROM `participant` WHERE `nom` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String cin = resultSet.getString("cin");
                    String numTel = resultSet.getString("numTel");
                    String email = resultSet.getString("email");
                    Date dateN = resultSet.getDate("dateN");
                    return new Participant(name, cin, dateN, numTel, email);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching participant by name: " + e.getMessage());
            return null;
        }
    }
}


