package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Lieu;
import com.example.gestionconference.Models.Organisateur;
import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class OrganisateurServices {

    private Connection cnx;

    public OrganisateurServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addOrganisateur(Organisateur organisateur) throws SQLException {


        String req = "INSERT INTO `organisateur` (`nom`,`mail`,`numtel`,`type`) VALUES(?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, organisateur.getNom());
        stm.setString(2, organisateur.getMail());
        stm.setInt(3,organisateur.getNumtel());
        stm.setString(4,organisateur.getType());


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

    public Organisateur getOrganisateur(int id) throws SQLException {
        String req1 = "SELECT * FROM organisateur WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req1);
        st.setInt(1, id); // Setting the parameter value

        ResultSet res = st.executeQuery();



            Organisateur u = new Organisateur();
        if (res.next()) { // Checking if ResultSet has any rows
            u = new Organisateur();
            u.setId(res.getInt("id"));
            u.setNom(res.getString("nom"));
            u.setMail(res.getString("mail"));
            u.setType(res.getString("type"));
            u.setNumtel(res.getInt("numtel"));
        }

        // Close resources
        if (res != null) {
            res.close();
        }
        if (st != null) {
            st.close();
        }

        return u;

    }
}


