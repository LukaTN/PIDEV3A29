package com.example.gestionconference.Services.ConferenceService;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Util.EvaluationUtils.MyDB;
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


        String req = "INSERT INTO `conference` (`nom`,`date`,`sujet`,`budget`,`typeConf`,`image`,`emplacement`,`organisateur`) VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, conference.getName());
        stm.setDate(2, conference.getDate());
        stm.setString(3,conference.getSubject());
        stm.setDouble(4,conference.getBudget());
        stm.setString(5,conference.getType().toString());
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
            stm.setString(1,  search + "%");
            stm.setString(2,   search + "%");

            // Check if search is a number before setting it as an integer parameter
            if (search instanceof ConferenceType) {
                ConferenceType conferenceType = (ConferenceType) search;
                stm.setString(3, conferenceType.name());
            } else {
                // Handle other cases or provide a default value
                stm.setString(3, "%" + search + "%");
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


    public List<Conference> getPublicorPrivate(ConferenceType type) throws SQLException {
        String req = "SELECT * FROM conference WHERE typeConf = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setString(1, type.toString());
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
                u.setType(transform(res.getString("typeConf")));
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
            u.setType(transform(res.getString("typeConf")));
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
            u.setType(transform(res.getString("typeConf")));
            u.setImage(res.getString("image"));
            u.setConferenceLocation(res.getInt("emplacement"));
            conferences.add(u);
        }
        return conferences;
    }

}


