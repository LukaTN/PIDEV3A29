package com.example.gestionconference.Models;


import java.util.List;

public class Session {
    private int id;
    private String sessionName;
    private int startTime;
    private int endTime;
    private int presenceNbr;
    private List<Topic> topicList;

    private int idConference;


    public Session(){}


    public Session(String sessionName, int startTime, int endTime, int idConference) {
        this.sessionName = sessionName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idConference = idConference;
    }

    public Session(String sessionName, int startTime, int endTime) {
        this.sessionName = sessionName;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public Session(String sessionName, int startTime, int endTime,int presenceNbr,List<Topic> topicList) {
        this.sessionName = sessionName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.presenceNbr = presenceNbr;
        this.topicList = topicList;

    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getPresenceNbr() {
        return presenceNbr;
    }

    public void setPresenceNbr(int presenceNbr) {
        this.presenceNbr = presenceNbr;
    }


    public int getIdConference() {
        return idConference;
    }

    public void setIdConference(int idConference) {
        this.idConference = idConference;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionName='" + sessionName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", presenceNbr=" + presenceNbr +
                ", idConference=" + idConference +
                ", idConference=" +topicList +
                '}';
    }
}