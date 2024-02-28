package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Lieu;
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

    public void addLieu(Lieu lieu) throws SQLException {


        String req = "INSERT INTO `emplacement` (`gouvernourat`,`ville`,`capacite`,`label`) VALUES(?,?,?,?)";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setString(1, lieu.getZone());
        stm.setString(2, lieu.getPlace());
        stm.setInt(3,lieu.getCapacity());
        stm.setString(4,lieu.getLabel());


        stm.executeUpdate();
    }

    public void updateLieu(Lieu lieu) throws SQLException {
        String req = "UPDATE `emplacement` SET `gouvernourat`=?,`ville`=?,`capacite`=?,`label`=? WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setString(1, lieu.getZone());
        stm.setString(2, lieu.getPlace());
        stm.setInt(3,lieu.getCapacity());
        stm.setString(4,lieu.getLabel());
        stm.setInt(5, lieu.getId());
        stm.executeUpdate();
    }

    public void deleteLieu(int id) throws SQLException {
        String req = "DELETE FROM `emplacement` WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    public List<Lieu> getAllLocations() throws SQLException {
        String req1 = "SELECT * FROM emplacement";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        List<Lieu> lieux = new ArrayList<>();
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
    public ObservableList<Lieu> getAllLocationsObservable() throws SQLException {
        String req1 = "SELECT * FROM emplacement";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Lieu> lieux = FXCollections.observableArrayList();
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
    public Lieu getLieuByid(int id) throws SQLException {
        String req = "SELECT * FROM emplacement WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, id);
        ResultSet res = stm.executeQuery();
        if (res.next()) {
            Lieu lieu = new Lieu();
            lieu.setId(res.getInt("id"));
            lieu.setCapacity(res.getInt("capacite"));
            lieu.setZone(res.getString("gouvernourat"));
            lieu.setLabel(res.getString("label"));
            lieu.setPlace(res.getString("ville"));
            return lieu;
        }
        return null;
    }
}


