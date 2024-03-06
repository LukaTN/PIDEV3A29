package com.example.gestionconference.Services.SessionServices;

import com.example.gestionconference.Models.SessionModels.Session;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;


public class SessionServices {
    private Connection cnx;

    public SessionServices() {
        cnx = MyDB.getInstance().getCnx();
    }



    public void addSessions(List<Session> sessions) {
        TopicServices ts = new TopicServices();
        String req = "INSERT INTO `session` (`sessionName`,`startTime`,`endTime`,`idConference`) VALUES(?,?,?,?)";

        try {
            for (Session session : sessions) {
                try (PreparedStatement stm = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
                    stm.setString(1, session.getSessionName());
                    stm.setTime(2,  Time.valueOf(session.getStartTime()));
                    stm.setTime(3, Time.valueOf( session.getEndTime()));
                    stm.setInt(4, session.getIdConference());
                    stm.executeUpdate();

                    // Retrieve the generated keys (including the ID)
                    ResultSet rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        int generatedId = rs.getInt(1); // Assuming the ID column is the first column
                        session.getTopicList().forEach(topic -> topic.setIdSession(generatedId));
                        ts.addTopics(session.getTopicList());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Session> getAllSessions() throws SQLException {
        TopicServices ts = new TopicServices();
        String req1 = "SELECT * FROM session";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Session> sessions = FXCollections.observableArrayList();
        while (res.next()){
            Session s = new Session();
            s.setId(res.getInt("id"));
            s.setId(res.getInt("id"));
            s.setSessionName(res.getString("sessionName"));
            s.setStartTime(res.getTime("startTime").toLocalTime());
            s.setEndTime(res.getTime("endTime").toLocalTime());
            s.setPresenceNbr(res.getInt("presenceNbr"));
            s.setTopicList(ts.getSessionTopic(res.getInt("id")));
            sessions.add(s);
        }


        return sessions;
    }


    public void updateSession(Session session) throws SQLException {
        String req = "UPDATE `session` SET `sessionName`=?,`startTime`=?,`endTime`=? WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setString(1, session.getSessionName());
        stm.setTime(2,  Time.valueOf(session.getStartTime()));
        stm.setTime(3, Time.valueOf(session.getEndTime()));
        stm.setInt(4, session.getId());
        stm.executeUpdate();
    }


    public void deleteSession(int id) throws SQLException {
        String req = "DELETE FROM `session` WHERE id = ?";
        String deleteQuery = "DELETE FROM topics WHERE idSession = ?";
        PreparedStatement statement = cnx.prepareStatement(deleteQuery);
        statement.setInt(1, id);
        statement.executeUpdate();
        try {
            try (PreparedStatement stm = cnx.prepareStatement(req)) {
                stm.setInt(1, id);
                stm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
}
