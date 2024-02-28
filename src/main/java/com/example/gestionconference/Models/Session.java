package com.example.gestionconference.Models;




public class Session {
    private int id;
    private int heureDebut;
    private int heureFin;
    private String objectif;
    private int nbPresence;

    // Constructor for Session

    public Session() {
    }
    public Session(int id,int heureDebut, int heureFin, String objectif, int nbPresence) {
        this.id=id;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.objectif = objectif;
        this.nbPresence = nbPresence;
    }
    public Session(int heureDebut, int heureFin, String objectif, int nbPresence) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.objectif = objectif;
        this.nbPresence = nbPresence;
    }



    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public int getNbPresence() {
        return nbPresence;
    }

    public void setNbPresence(int nbPresence) {
        this.nbPresence = nbPresence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Session{" +
                "heureDebut='" + heureDebut + '\'' +
                ", heureFin='" + heureFin + '\'' +
                ", objectif='" + objectif + '\'' +
                ", nbPresence=" + nbPresence +
                '}';
    }
}