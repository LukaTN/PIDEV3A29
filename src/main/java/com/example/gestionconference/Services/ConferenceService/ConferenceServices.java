package com.example.gestionconference.Services.ConferenceService;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

import static com.example.gestionconference.Services.ConferenceService.LieuServices.isNumeric;

public class ConferenceServices {
    private Connection cnx;

    public ConferenceServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addConference(Conference conference) throws SQLException {


        String req = "INSERT INTO `conference` (`nom`,`date`,`sujet`,`budget`,`typeConf`,`image`,`emplacement`) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, conference.getName());
        stm.setDate(2, conference.getDate());
        stm.setString(3,conference.getSubject());
        stm.setDouble(4,conference.getBudget());
        stm.setString(5,conference.getType().toString());
        stm.setString(6,conference.getImage());
        stm.setInt(7,conference.getConferenceLocation());

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
    public void updateConference(Conference conference)  {
        String req = "UPDATE `conference` SET `nom`=?,`date`=?,`sujet`=?,`budget`=?,`typeConf`=?,`image`=? WHERE id = ?";


        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, conference.getName());
            stm.setDate(2, conference.getDate());
            stm.setString(3,conference.getSubject());
            stm.setDouble(4,conference.getBudget());
            stm.setString(5,conference.getType().toString());
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
            u.setType(transform(res.getString("typeConf")));
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
            req1 = "SELECT * FROM conference WHERE nom LIKE ? OR budget LIKE ? OR typeConf = ?";
            stm = cnx.prepareStatement(req1);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");

            // Check if search is a number before setting it as an integer parameter
            if (search instanceof String && isNumeric((String) search)) {
                stm.setInt(3, Integer.parseInt((String) search));
            } else {
                stm.setInt(3, 0); // A placeholder value, won't affect the query
            }
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
            u.setType(transform(res.getString("typeConf")));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conferences.add(u);
        }

        return conferences;
    }

    public List<Conference> getAllPrivateConferences() throws SQLException {
        String req = "SELECT * FROM conference WHERE typeConf = 'PRIVATE'";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        ObservableList<Conference> privateConferences = FXCollections.observableArrayList();
        while (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(transform(res.getString("typeConf")));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            privateConferences.add(u);
        }
        return privateConferences;
    }

    public List<Conference> getAllNonPrivateConferences() throws SQLException {
        String req = "SELECT * FROM conference WHERE typeConf != 'PRIVATE'";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        ObservableList<Conference> nonPrivateConferences = FXCollections.observableArrayList();
        while (res.next()) {
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getDate("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(transform(res.getString("typeConf")));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            nonPrivateConferences.add(u);
        }
        return nonPrivateConferences;
    }

}


