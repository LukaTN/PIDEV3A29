package com.example.gestionconference.Models.ConferenceModels;

import com.example.gestionconference.Models.SessionModels.Session;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Conference {
    private int id;
    private String name;
    private Date date;
    private String subject;
    private double budget;
    private Boolean typeConf;

    private String image;
    private int emplacement;
    private int organisateur;

    private List<Session> sessions;


    // Constructor for Conference


    public Conference(int id, String name, Date date, String subject, double budget, Boolean typeConf, int emplacement) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;
    }
    public Conference(String name, Date date, String subject, double budget, Boolean typeConf, int emplacement, int organisateur) {

        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;
        this.organisateur = organisateur;

    }

    public Conference( String name, Date date, String subject, double budget, Boolean typeConf, int emplacement) {

        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.emplacement = emplacement;

    }
    public Conference(int id, String name, Date date, String subject, double budget, Boolean typeConf,
                      String image, int emplacement, int organisateur) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.budget = budget;
        this.typeConf = typeConf;
        this.image = image;
        this.emplacement = emplacement;
        this.organisateur = organisateur;
    }

    public Conference(int id, String name, Date date, String subject, double budget, Boolean typeConf,
                      String image, int emplacement, int organisateur, List<Session> sessions) {
        this(id, name, date, subject, budget, typeConf, image, emplacement, organisateur);
        this.sessions = sessions;
    }


    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
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

    public Boolean getType() {
        return typeConf;
    }

    public void setType(Boolean typeConf) {
        this.typeConf = typeConf;
    }

    public int getConferenceLocation() {
        return emplacement;
    }

    public void setConferenceLocation(int emplacement) {
        this.emplacement = emplacement;
    }

    public Boolean getTypeConf() {
        return typeConf;
    }

    public void setTypeConf(Boolean typeConf) {
        this.typeConf = typeConf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(int emplacement) {
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
