package com.example.gestionconference.Models;

import java.util.ArrayList;
import java.util.List;

public class Conference {
    private int id;
    private String name;
    private String date;
    private String subject;
    private double budget;
    private ConferenceType type;
    private Lieu conferenceLocation;
    private List<Session> sessions ;


    // Constructor for Conference
    public Conference(int id, String name, String date, String subject, double budget, ConferenceType type, Lieu conferenceLocation) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.type = type;
        this.conferenceLocation = conferenceLocation;
        this.sessions= new ArrayList<Session>();
    }
    public Conference( String name, String date, String subject, double budget, ConferenceType type, Lieu conferenceLocation) {
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.type = type;
        this.conferenceLocation = conferenceLocation;
        this.sessions= new ArrayList<Session>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public ConferenceType getType() {
        return type;
    }

    public void setType(ConferenceType type) {
        this.type = type;
    }

    public Lieu getConferenceLocation() {
        return conferenceLocation;
    }

    public void setConferenceLocation(Lieu conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", subject='" + subject + '\'' +
                ", budget=" + budget +
                ", type=" + type +
                ", conferenceLocation=" + conferenceLocation +
                ", sessions=" + sessions +
                '}';
    }
}
