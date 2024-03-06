package com.example.gestionconference.Services.SessionServices;

import com.example.gestionconference.Models.SessionModels.Topic;
import com.example.gestionconference.Util.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class TopicServices {

    private Connection cnx;

    public TopicServices() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addTopics(List<Topic> topics) throws SQLException {


        String req = "INSERT INTO `topics` (`speakerName`,`topicName`,`topicDescription`,`idSession`) VALUES(?,?,?,?)";

        try {
            for (Topic topic : topics) {
                try (PreparedStatement stm = cnx.prepareStatement(req)) {

                    stm.setString(1, topic.getSpeakerName());
                    stm.setString(2, topic.getTopicName());
                    stm.setString(3, topic.getTopicDescription());
                    stm.setInt(4, topic.getIdSession());

                    stm.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Topic> getAllTopics() throws SQLException {
        String req1 = "SELECT * FROM topics";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req1);
        ObservableList<Topic> sessions = FXCollections.observableArrayList();
        while (res.next()){
            Topic s = new Topic();
            s.setId(res.getInt("id"));
            s.setTopicName(res.getString("topicName"));
            s.setTopicDescription(res.getString("topicDescription"));
            s.setSpeakerName(res.getString("speakerName"));
            s.setIdSession(res.getInt("idSession"));
            sessions.add(s);
        }

        return sessions;
    }



    public void deleteTopic(int id) throws SQLException {


        String deleteQuery = "DELETE FROM topics WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(deleteQuery);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void updateTopic(Topic topic) throws SQLException {
        String req = "UPDATE `topics` SET `topicName`=?,`speakerName`=?,`topicDescription`=? WHERE id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setString(1, topic.getTopicName());
        stm.setString(2, topic.getSpeakerName());
        stm.setString(3,topic.getTopicDescription());
        stm.setInt(4, topic.getId());
        stm.executeUpdate();
    }

    public ObservableList<Topic> getSessionTopic(int idSession) throws SQLException {
        String req1 = "SELECT * FROM topics WHERE idSession = ?";
        PreparedStatement st = cnx.prepareStatement(req1);
        st.setInt(1, idSession); // Setting the parameter value

        // Execute the query
        ResultSet res = st.executeQuery(); // Remove req1 from executeQuery

        // Create an ObservableList to store topics
        ObservableList<Topic> topics = FXCollections.observableArrayList();

        // Iterate over the result set and populate topics list
        while (res.next()) {
            Topic topic = new Topic();
            topic.setId(res.getInt("id"));
            topic.setTopicName(res.getString("topicName"));
            topic.setTopicDescription(res.getString("topicDescription"));
            topic.setSpeakerName(res.getString("speakerName"));
            topic.setIdSession(res.getInt("idSession"));
            topics.add(topic);
        }

        // Close the statement and result set
        st.close();
        res.close();
        System.out.println(topics);
        // Return the list of topics
        return topics;
    }






}