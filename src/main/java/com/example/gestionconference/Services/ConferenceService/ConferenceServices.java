package com.example.gestionconference.Services.ConferenceService;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;


import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.gestionconference.Services.ConferenceService.LieuServices.isNumeric;

public class ConferenceServices {
    private Connection cnx;

    public ConferenceServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addConference(Conference conference) throws SQLException {


        String req = "INSERT INTO `conference` (`nom`,`date`,`sujet`,`budget`,`typeConf`,`image`,`emplacement`,`organisateur`) VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, conference.getName());
        stm.setDate(2, conference.getDate());
        stm.setString(3,conference.getSubject());
        stm.setDouble(4,conference.getBudget());
        stm.setBoolean(5,conference.getType());
        stm.setString(6,conference.getImage());
        stm.setInt(7,conference.getConferenceLocation());
        stm.setInt(8,conference.getOrganisateur());
        
        stm.executeUpdate();
    }
    public void deleteConference(Conference conference) throws SQLException {
        String req = "DELETE FROM `conference` WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, conference.getId());
        stm.executeUpdate();

//        String req1= "DELETE FROM emplacement WHERE id = ?";
//        PreparedStatement stm1 = cnx.prepareStatement(req1);
//        stm1.setInt(1, conference.getConferenceLocation());
//        stm1.executeUpdate();
    }
    public static ConferenceType transform(String conferenceTypeString) {

        if ("PUBLIC".equals(conferenceTypeString)) {
            return ConferenceType.PUBLIC;
        } else if ("PRIVATE".equals(conferenceTypeString)) {
            return ConferenceType.PRIVATE;
        } else {
            return null;
        }
    }


    public Conference getConferenceByName(String name) throws SQLException {
        String req1 = "SELECT * FROM conference WHERE nom = ?";
        Conference u = new Conference();
        PreparedStatement stm = cnx.prepareStatement(req1);
        stm.setString(1, name);
        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        if (res.next()){

            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setConferenceLocation(res.getInt("emplacement"));

        }
        return u;
    }


    public Conference getTodayConference() throws SQLException {
        String req1 = "SELECT * FROM conference WHERE date = ? LIMIT 1";
        PreparedStatement stm = cnx.prepareStatement(req1);
        LocalDate today = LocalDate.now();
        stm.setDate(1, java.sql.Date.valueOf(today)); // Convert java.time.LocalDate to java.sql.Date
        ResultSet res = stm.executeQuery();

        // Assuming that there's at most one conference per day, hence no loop needed
        if (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setConferenceLocation(res.getInt("emplacement"));

            return u;
        } else {
            return null; // No conference found for today
        }
    }

    public void updateConference(Conference conference)  {
        String req = "UPDATE `conference` SET `nom`=?,`date`=?,`sujet`=?,`budget`=?,`typeConf`=?,`image`=? WHERE id = ?";


        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, conference.getName());
            stm.setDate(2, conference.getDate());
            stm.setString(3,conference.getSubject());
            stm.setDouble(4,conference.getBudget());
            stm.setBoolean(5,conference.getType());
            stm.setString(6,conference.getImage());
            stm.setInt(7,conference.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }


    public List<Conference> getAllConferences() throws SQLException {
        String req1 = "SELECT * FROM conference";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Conference> conference = FXCollections.observableArrayList();
        while (res.next()){
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conference.add(u);
        }
        return conference;
    }
    public List<Conference> getAllConferencesDone() throws SQLException {
        String req1 = "SELECT * FROM conference WHERE date <= CURDATE()";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Conference> conference = FXCollections.observableArrayList();
        while (res.next()){
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conference.add(u);
        }
        return conference;
    }
    public List<Conference> getAllConferenceObservable(Object search) throws SQLException {
        String req1;
        PreparedStatement stm;

        if (search != null) {
            req1 = "SELECT conference.* FROM conference JOIN emplacement ON conference.emplacement = emplacement.id WHERE conference.nom LIKE ? OR conference.budget LIKE ? OR emplacement.label LIKE ? OR emplacement.gouvernourat LIKE ?";
            stm = cnx.prepareStatement(req1);
            String searchTerm = "%" + search + "%";
            stm.setString(1, searchTerm);
            stm.setString(2, searchTerm);
            stm.setString(3, searchTerm);
            stm.setString(4, searchTerm);
        } else {
            req1 = "SELECT * FROM conference";
            stm = cnx.prepareStatement(req1);
        }

        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        while (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conferences.add(u);
        }

        return conferences;
    }


    public List<Conference> getPublicorPrivate(Boolean type) throws SQLException {
        String req = "SELECT * FROM conference WHERE typeConf = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setBoolean(1, type);
        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        while (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conferences.add(u);
        }
        return conferences;
    }

    public int conferenceByName(String name) throws SQLException {
        String req = "SELECT id FROM conference WHERE nom = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setString(1, name);
        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        int id = 0;
        while (res.next()) {
            id = res.getInt("id");

        }
        return id;
    }

    public List<String> getConfereceNameByDate(Date date) throws SQLException {
        String req = "SELECT nom FROM conference WHERE date = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setDate(1, date);
        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        String name = "";
        List<String> names = new ArrayList<>();
        while (res.next()) {
            name = res.getString("nom");
            names.add(name);
        }
        return names;
    }

    public List<Conference> triConferenceByname() throws SQLException {
        String req = "SELECT * FROM conference ORDER BY nom";

            PreparedStatement stm = cnx.prepareStatement(req);
            ResultSet res = stm.executeQuery();
            ObservableList<Conference> conferences = FXCollections.observableArrayList();
            while (res.next()) {
                Conference u = new Conference();
                u.setId(res.getInt("id"));
                u.setName(res.getString("nom"));
                u.setDate(res.getDate("date"));
                u.setSubject(res.getString("sujet"));
                u.setBudget(res.getDouble("budget"));
                u.setType(res.getBoolean("typeConf"));
                u.setImage(res.getString("image"));
                u.setConferenceLocation(res.getInt("emplacement"));
                conferences.add(u);
            }
        return conferences;
    }

    public List<Conference> triConferenceByBudget() throws SQLException {
        String req = "SELECT * FROM conference ORDER BY budget";
        PreparedStatement stm = cnx.prepareStatement(req);
        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        while (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conferences.add(u);
        }
        return conferences;

    }

    public List<Conference> triConferenceByDate() throws SQLException {
        String req = "SELECT * FROM conference ORDER BY date";
        PreparedStatement stm = cnx.prepareStatement(req);
        ResultSet res = stm.executeQuery();
        ObservableList<Conference> conferences = FXCollections.observableArrayList();
        while (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(res.getBoolean("typeConf"));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conferences.add(u);
        }
        return conferences;
    }

    public List<String> getByDate(Date date) {
        String req = "SELECT date FROM conference WHERE date = ?";
        List<String> dates = new ArrayList<>();
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setDate(1, date);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                dates.add(res.getString("date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dates;
    }
}


