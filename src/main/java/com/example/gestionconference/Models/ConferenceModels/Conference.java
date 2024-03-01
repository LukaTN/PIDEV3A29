package com.example.gestionconference.Models.ConferenceModels;

import java.sql.Date;

public class Conference {
    private int id;
    private String name;
    private Date date;
    private String subject;
    private double budget;
    private ConferenceType typeConf;
    private int emplacement;
    private int organisateur;


    // Constructor for Conference


    public Conference(int id, String name, Date date, String subject, double budget, ConferenceType typeConf, int emplacement) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;
    }

    public Conference(String name, Date date, String subject, double budget, ConferenceType typeConf, int emplacement, int organisateur) {

        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;
        this.organisateur = organisateur;
        //this.sessions= new ArrayList<Session>();
    }

    public Conference( String name, Date date, String subject, double budget, ConferenceType typeConf, int emplacement) {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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


    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", subject='" + subject + '\'' +
                ", budget=" + budget +
                ", typeConf=" + typeConf +
                ", emplacement=" + emplacement +
                ", organisateur=" + organisateur +
                '}';
    }
}
