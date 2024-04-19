package com.example.gestionconference.Services.PresenceServices;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.PresenceModels.Presence;
import com.example.gestionconference.Models.SessionModels.Session;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.SessionServices.SessionServices;
import com.example.gestionconference.Services.SessionServices.TopicServices;
import com.example.gestionconference.Services.UserServices.UserService;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.util.List;

public class CardService {

    private Connection cnx;
    private final ConferenceServices cs = new ConferenceServices();
    private final UserService us = new UserService();

    public CardService() {
        cnx = MyDB.getInstance().getCnx();
    }



    public ObservableList<Presence> getAllTopics() throws SQLException {
        String req1 = "SELECT uid, idParticipant, currentTime, status FROM uidcard GROUP BY uid";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Presence> presences = FXCollections.observableArrayList();
        while (res.next()){
            Presence presence = new Presence(res.getString("uid"),
                    res.getInt("idParticipant"), res.getTimestamp("currentTime"),
                    res.getInt("status"));
            presences.add(presence);
        }

        return presences;
    }

    public Presence addCard(String uid) throws SQLException{
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
Presence presence = new Presence(uid,0,currentTimeStamp,0);


        String selectQuery = "SELECT * FROM uidcard WHERE uid = ? ORDER BY currentTime DESC LIMIT 1";
            PreparedStatement selectStmt = cnx.prepareStatement(selectQuery);
            selectStmt.setString(1, uid);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                // UID exists in the table
                int status = resultSet.getInt("status");
                // Increment the presence status
                int stat = status + 1;
                presence.setPresenceStatus(stat);
                presence.setIdParticipant(resultSet.getInt("idParticipant"));

                    String req1 = "INSERT INTO `uidcard` (`uid`,`idParticipant`,`status`) VALUES(?,?,?)";
                    PreparedStatement updateStmt = cnx.prepareStatement(req1);
                    if(resultSet.getInt("idParticipant") != 0 && uid != null) {
                    updateStmt.setInt(3, stat);
                    updateStmt.setInt(2,resultSet.getInt("idParticipant") );
                    updateStmt.setString(1, uid);

                        updateStmt.executeUpdate();



                    String req2 = "UPDATE `uidcard` SET `idParticipant`=? WHERE uid = ?";
                    PreparedStatement updateStmt2 = cnx.prepareStatement(req2);
                    updateStmt2.setString(2,uid);
                    updateStmt2.setInt(1, resultSet.getInt("idParticipant") );
                    updateStmt2.executeUpdate();
                    return presence;
                    }


            } else {
                // UID does not exist in the table
                String insertQuery = "INSERT INTO uidcard (uid) VALUES (?)";
                PreparedStatement insertStmt = cnx.prepareStatement(insertQuery);
                insertStmt.setString(1, uid);
                insertStmt.executeUpdate();
                return null;

            }
return null;
    }


    public Presence getParticipantById(int id) throws SQLException {
        Presence presence = null; // Initialize presence variable outside the try block

        String req1 = "SELECT * FROM uidcard WHERE idParticipant=?";

        try (PreparedStatement pstmt = cnx.prepareStatement(req1)) {
            pstmt.setInt(1, id);

            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    presence = new Presence(
                            res.getString("uid"),
                            res.getInt("idParticipant"),
                            res.getTimestamp("currentTime"),
                            res.getInt("status")
                    );
                }
            }
        }

        return presence;
    }



    public ObservableList<User> getAllParticipantNonRegistered() throws SQLException {
        String req1 = "SELECT user.id, user.username " +
                "FROM user " +
                "LEFT JOIN uidcard ON user.id = uidcard.idParticipant " +
                "WHERE uidcard.idParticipant IS NULL;";
        ObservableList<User> users = FXCollections.observableArrayList();
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req1)) {
            while (res.next()) {
                User user = new User(res.getInt("id"), res.getString("username"));
                users.add(user);
            }
        }

        return users;
    }


    public void updateCard(Presence presence) throws SQLException {
        String req = "UPDATE `uidcard` SET `uid`=?,`idParticipant`=? WHERE uid = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setString(1, presence.getUid());
        stm.setInt(2,  presence.getIdParticipant());
        stm.setString(3, presence.getUid());
        stm.executeUpdate();
    }

    public void deleteCard(String uid) throws SQLException {
        String req = "DELETE FROM `uidcard` WHERE uid = ?";

            try (PreparedStatement stm = cnx.prepareStatement(req)) {
                stm.setString(1, uid);
                stm.executeUpdate();
            }
         catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public boolean participantPresence(int idParticipant, String conferenceName) throws SQLException {

            Conference conf = cs.getConferenceByName(conferenceName);
            String query = "SELECT * " +
                    "FROM uidcard " +
                    "WHERE idParticipant = ? AND DATE(currentTime) = ? " +
                    "ORDER BY status DESC LIMIT 1;";
            try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
                pstmt.setInt(1, idParticipant);
                pstmt.setDate(2,conf.getDate());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getInt("status")>0){

                            return true;
                        }
                        return false;
                        }else {
                            return false ; // If there are rows, the participant is present
                        }
                }
            }

    }




    public void setPresenceTime(Conference conf,int timeSpent,int stabilityValue) throws SQLException {
        SessionServices ss = new SessionServices();
        Session session;
        session = ss.getCurrentSession(conf);

        String req = "UPDATE `session` SET `presenceNbr`=?,`presenceSpent`=?,`presenceQuality`=? WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);

        stm.setInt(1,session.getPresenceNbr()+1);
        stm.setInt(2,session.getPresenceTime()+timeSpent);
        stm.setInt(3,stabilityValue);
        stm.setInt(4, session.getId());

        stm.executeUpdate();
    }
}