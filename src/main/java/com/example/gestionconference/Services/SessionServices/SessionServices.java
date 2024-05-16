package com.example.gestionconference.Services.SessionServices;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.SessionModels.Session;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Util.EvaluationUtils.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
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

            s.setSessionName(res.getString("sessionName"));
            s.setStartTime(res.getTime("startTime").toLocalTime());
            s.setEndTime(res.getTime("endTime").toLocalTime());
            s.setPresenceNbr(res.getInt("presenceNbr"));
            s.setPresenceTime(res.getInt("presenceSpent"));
            s.setPresenceQuality(res.getInt("presenceQuality"));
            s.setTopicList(ts.getSessionTopic(res.getInt("id")));
            sessions.add(s);
        }


        return sessions;
    }

    public Session getCurrentSession(Conference newConf) throws SQLException {
        // Use a prepared statement for parameterized query
        String req1 = "SELECT * " +
                "FROM session " +
                "WHERE idConference = ? " +
                "AND CURTIME() BETWEEN startTime AND endTime;";

        try (PreparedStatement pst = cnx.prepareStatement(req1)) {
            pst.setInt(1, newConf.getId());

            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    // Session found, create and populate the Session object
                    Session s = new Session();
                    s.setId(res.getInt("id"));
                    s.setSessionName(res.getString("sessionName"));
                    s.setStartTime(res.getTime("startTime").toLocalTime());
                    s.setEndTime(res.getTime("endTime").toLocalTime());
                    s.setPresenceNbr(res.getInt("presenceNbr"));
                    s.setPresenceTime(res.getInt("presenceSpent"));
                    s.setPresenceQuality(res.getInt("presenceQuality"));

                    return s;
                } else {
                    // No session found, return null
                    return null;
                }
            }
        }
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

    public List<Session> getSessionsByConferenceId(int id) {
        List<Session> sessions = new ArrayList<>();
        TopicServices ts = new TopicServices();

        try {
            String query = "SELECT * FROM session WHERE idConference = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setSessionName(resultSet.getString("sessionName"));
                session.setStartTime(resultSet.getTime("startTime").toLocalTime());
                session.setEndTime(resultSet.getTime("endTime").toLocalTime());
                session.setPresenceNbr(resultSet.getInt("presenceNbr"));
                session.setPresenceTime(resultSet.getInt("presenceSpent"));
                session.setPresenceQuality(resultSet.getInt("presenceQuality"));
                session.setTopicList(ts.getSessionTopic(resultSet.getInt("id")));

                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return sessions;
    }
}
