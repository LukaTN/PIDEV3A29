package com.example.gestionconference.Models.SessionModels;

public class Topic {
    private int id;
    private String speakerName;
    private String topicName;
    private String topicDescription;
    private int idSession;

    public Topic (){}

    public Topic(String speakerName, String topicName, String topicDescription, int idSession) {
        this.speakerName = speakerName;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.idSession = idSession;
    }

    public Topic(String speakerName, String topicName, String topicDescription) {
        this.speakerName = speakerName;
        this.topicName = topicName;
        this.topicDescription = topicDescription;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "speakerName='" + speakerName + '\'' +
                ", topicName='" + topicName + '\'' +
                ", topicDescription='" + topicDescription + '\'' +
                ", idSession='" + idSession + '\'' +
                '}';
    }
}
