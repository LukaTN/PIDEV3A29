package com.example.gestionconference.Models.SessionModels;


import java.time.LocalTime;
import java.util.List;

public class Session {
    private int id;
    private String sessionName;
    private LocalTime startTime;
    private LocalTime endTime;
    private int presenceNbr;
    private List<Topic> topicList;

    private int idConference;


    public Session(){}


    public Session(String sessionName, LocalTime startTime, LocalTime endTime, int idConference) {
        this.sessionName = sessionName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idConference = idConference;
    }

    public Session(String sessionName, LocalTime startTime, LocalTime endTime) {
        this.sessionName = sessionName;
        this.startTime = startTime;
        this.endTime = endTime;

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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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