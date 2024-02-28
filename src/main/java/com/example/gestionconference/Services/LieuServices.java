package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Lieu;
import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuServices {

    private Connection cnx;

    public LieuServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addConference(Lieu lieu) throws SQLException {


        String req = "INSERT INTO `emplacement` (`gouvernourat`,`ville`,`capacite`,`label`) VALUES(?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, lieu.getZone());
        stm.setString(2, lieu.getPlace());
        stm.setInt(3,lieu.getCapacity());
        stm.setString(4,lieu.getLabel());


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

    public List<Lieu> getAllLocations() throws SQLException {
        String req1 = "SELECT * FROM emplacement";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        List<Lieu> lieux = new ArrayList<>();
        //ObservableList<Session> sessions = FXCollections.observableArrayList();
        while (res.next()){
            Lieu u = new Lieu();
            u.setId(res.getInt("id"));
            u.setCapacity(res.getInt("capacite"));
            u.setZone(res.getString("gouvernourat"));
            u.setLabel(res.getString("label"));
            u.setPlace(res.getString("ville"));

            lieux.add(u);
        }

        return lieux;
    }
}


