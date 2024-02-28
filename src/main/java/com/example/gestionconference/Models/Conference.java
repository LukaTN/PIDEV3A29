package com.example.gestionconference.Models;

import java.util.ArrayList;
import java.util.List;

public class Conference {
    private int id;
    private String name;
    private String date;
    private String subject;
    private double budget;
    private ConferenceType typeConf;
    private int emplacement;
    private int organisateur;
    private List<Session> sessions ;


    // Constructor for Conference

    public Conference( String name, String date, String subject, double budget, ConferenceType typeConf, int emplacement,int organisateur) {

        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;
        this.organisateur = organisateur;
        //this.sessions= new ArrayList<Session>();
    }

    public Conference( String name, String date, String subject, double budget, ConferenceType typeConf, int emplacement) {

        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;

    }

    public Conference(){

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

    public int getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(int organisateur) {
        this.organisateur = organisateur;
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
        return typeConf;
    }

    public void setType(ConferenceType typeConf) {
        this.typeConf = typeConf;
    }

    public int getConferenceLocation() {
        return emplacement;
    }

    public void setConferenceLocation(int emplacement) {
        this.emplacement = emplacement;
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
                ", type=" + typeConf +
                ", conferenceLocation=" + emplacement +
                ", sessions=" + sessions +
                '}';
    }
}
