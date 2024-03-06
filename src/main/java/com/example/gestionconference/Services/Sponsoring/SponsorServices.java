package com.example.gestionconference.Services.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Status;
import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Util.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorServices {

    private Connection connection;

    public SponsorServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public boolean SponsorExist(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM sponsor WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }


    public Sponsor getByEmail(String e) throws SQLException {
        Sponsor sp = new Sponsor(); // Initialize user as null
        String query = "SELECT * FROM Sponsor WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, e);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {


            sp.setId(rs.getInt("id"));
            sp.setBudget(Double.parseDouble(rs.getString("budget")));
            sp.setCause(rs.getString("cause"));




        }
        return sp;
    }

    public void add(Sponsor sponsor) throws SQLException {
        if(SponsorExist(sponsor.getEmail())){
            System.out.println("sponsor exist");
            return;
        }
        Sponsor s = null;
        String sql = "INSERT INTO sponsor (nom, email, numtel, status, budget, cause) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, sponsor.getNom());
            preparedStatement.setString(2, sponsor.getEmail());
            preparedStatement.setString(3, sponsor.getNumtel());
            preparedStatement.setString(4, sponsor.getStatus());
            preparedStatement.setDouble(5, sponsor.getBudget());
            preparedStatement.setString(6, sponsor.getCause());
            preparedStatement.executeUpdate();

            }catch (SQLException e) {
        // Handle exception
        e.printStackTrace();
    }
        s = getByEmail(sponsor.getEmail());
        System.out.println(s);
if (sponsor.getStatus() == "ACCEPTED"){
    String sql2 = "INSERT INTO sponsoraccepted ( idsponsor, budget) VALUES (?, ?)";
    try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql2)) {
        preparedStatement1.setInt(1, s.getId());
        preparedStatement1.setDouble(2, s.getBudget());

        preparedStatement1.executeUpdate();
    } catch (SQLException e) {
        // Handle exception
        e.printStackTrace();
    }
}else{
    String sql2 = "INSERT INTO sponsorrejected (idsponsor, cause) VALUES (?, ?)";
    try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql2)) {
        preparedStatement1.setInt(1, s.getId());
        preparedStatement1.setString(2, s.getCause());
        preparedStatement1.executeUpdate();
    } catch (SQLException e) {
        // Handle exception
        e.printStackTrace();
    }
}
        }


    public List<Sponsor> getByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM sponsor WHERE status = ?";
        List<Sponsor> sponsors = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Sponsor sponsor = new Sponsor();
                sponsor.setId(rs.getInt("id"));
                sponsor.setNom(rs.getString("nom"));
                sponsor.setEmail(rs.getString("email"));
                sponsor.setNumtel(rs.getString("numtel"));
                sponsor.setStatus(rs.getString("status"));
                sponsor.setBudget(rs.getDouble("budget"));
                sponsor.setCause(rs.getString("cause"));
                sponsors.add(sponsor);
            }
        }
        return sponsors;
    }



    public void update(Sponsor sponsor) throws SQLException {
        String sql = "UPDATE sponsor SET nom = ?, email = ?, numtel = ?, status = ?, budget = ?, cause = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sponsor.getNom());
        preparedStatement.setString(2, sponsor.getEmail());
        preparedStatement.setString(3, sponsor.getNumtel());
        preparedStatement.setString(4, sponsor.getStatus().toString());
        preparedStatement.setDouble(5, sponsor.getBudget());
        preparedStatement.setString(6, sponsor.getCause());
        preparedStatement.setInt(7, sponsor.getId());
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
            sponsor.setStatus(rs.getString("status"));
            sponsor.setBudget(rs.getDouble("budget"));
            sponsor.setCause(rs.getString("cause"));
            sponsors.add(sponsor);
        }
        return sponsors;
    }


    public void delete(Sponsor sponsor) throws SQLException {
        String sql = "DELETE FROM sponsor WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sponsor.getId());
        preparedStatement.executeUpdate();
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
