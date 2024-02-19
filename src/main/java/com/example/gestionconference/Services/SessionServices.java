package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class SessionServices {
    private Connection cnx;

    public SessionServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addSession(Session session) throws SQLException {


            String req = "INSERT INTO `session` (`heureDebut`,`heureFin`,`objectif`,`nbPresence`) VALUES(?,?,?,?)";
            PreparedStatement stm = cnx.prepareStatement(req);

            stm.setInt(1, session.getHeureDebut());
            stm.setInt(2, session.getHeureFin());
            stm.setString(3,session.getObjectif());
            stm.setInt(4,session.getNbPresence());
            stm.executeUpdate();
        }
    public void deleteSession(int id) throws SQLException {
        String req = "DELETE FROM `session` WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, id);
        stm.executeUpdate();
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

    public ObservableList<Session> getAllSessions() throws SQLException {
        String req1 = "SELECT * FROM session";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Session> sessions = FXCollections.observableArrayList();
        while (res.next()){
            Session u = new Session();
            u.setId(res.getInt("id"));
            u.setHeureDebut(res.getInt("heureDebut"));
            u.setHeureFin(res.getInt("heureFin"));
            u.setObjectif(res.getString("objectif"));
            u.setNbPresence(res.getInt("nbPresence"));
            sessions.add(u);
        }
        return sessions;
    }
}


