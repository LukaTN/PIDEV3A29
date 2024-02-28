package com.example.gestionconference.Services;

import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalTime;
import java.sql.*;
import java.util.List;


public class SessionServices {
    private Connection cnx;

    public SessionServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public int getMaxId(){
        int maxId = 0; // Initialize maxId to a default value

        try  {
            String req = "SELECT MAX(id) AS max_id FROM session";
            // Create a statement
            PreparedStatement stm = cnx.prepareStatement(req);

            // Execute the query
            ResultSet resultSet = stm.executeQuery();

            // Check if the result set has data
            if (resultSet.next()) {
                // Retrieve the maximum ID
                maxId = resultSet.getInt("max_id");
                System.out.println("The largest ID in the session table is: " + maxId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId; // Return the maximum ID
    }

    public int convertTime(Time t) {
        String[] parts =t.toString().split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);


        int convertedTime = hours * 10000 + minutes * 100;

        return convertedTime;
    }

    public void addSessions(List<Session> sessions) {
        TopicServices ts = new TopicServices();
        String req = "INSERT INTO `session` (`sessionName`,`startTime`,`endTime`,`idConference`) VALUES(?,?,?,?)";

        try {
            for (Session session : sessions) {
                try (PreparedStatement stm = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
                    stm.setString(1, session.getSessionName());
                    stm.setInt(2, session.getStartTime());
                    stm.setInt(3, session.getEndTime());
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
            s.setStartTime(convertTime(res.getTime("startTime")) );
            s.setEndTime(convertTime(res.getTime("endTime")));
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
        stm.setInt(2, session.getStartTime());
        stm.setInt(3,session.getEndTime());
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
