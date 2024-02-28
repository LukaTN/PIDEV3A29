package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Conference;
import com.example.gestionconference.Models.ConferenceType;
import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ConferenceServices {
    private Connection cnx;

    public ConferenceServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addConference(Conference conference) throws SQLException {


        String req = "INSERT INTO `conference` (`nom`,`date`,`sujet`,`budget`,`typeConf`,`emplacement`,`organisateur`) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, conference.getName());
        stm.setString(2, conference.getDate());
        stm.setString(3,conference.getSubject());
        stm.setDouble(4,conference.getBudget());
        stm.setString(5,conference.getType().toString());
        stm.setInt(6,conference.getConferenceLocation());
        stm.setInt(7,conference.getOrganisateur());

        stm.executeUpdate();
    }
    public void deleteConference(int id) throws SQLException {
        String req = "DELETE FROM `conference` WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, id);
        stm.executeUpdate();
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
    public void updateSession(Session session) throws SQLException {
        String req = "UPDATE `session` SET `heureDebut`=?,`heureFin`=?,`objectif`=?,`nbPresence`=? WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, session.getHeureDebut());
        stm.setInt(2, session.getHeureFin());
        stm.setString(3,session.getObjectif());
        stm.setInt(4,session.getNbPresence());

        stm.executeUpdate();
    }


    public ObservableList<Conference> getAllConferences() throws SQLException {
        String req1 = "SELECT * FROM conference";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Conference> conference = FXCollections.observableArrayList();
        while (res.next()){
            Conference u = new Conference();
            u.setId(res.getInt("id"));
            u.setName(res.getString("nom"));
            u.setDate(res.getString("date"));
            u.setSubject(res.getString("sujet"));
            u.setBudget(res.getDouble("budget"));
            u.setType(transform(res.getString("typeConf")));
            u.setConferenceLocation(res.getInt("emplacement"));
            u.setOrganisateur(res.getInt("organisateur"));

            conference.add(u);
        }

        return conference;
    }
}


