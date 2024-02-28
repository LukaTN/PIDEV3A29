package com.example.gestionconference.Models;

public class Organisateur {
    private int  id;
    private String nom;
    private String mail;
    private int numtel;
    private String type;

    public Organisateur(int id,String nom, String mail, int numtel, String type) {
        this.id=id;
        this.nom = nom;
        this.mail = mail;
        this.numtel = numtel;
        this.type = type;
    }
    public Organisateur(String nom, String mail, int numtel, String type) {

        this.nom = nom;
        this.mail = mail;
        this.numtel = numtel;
        this.type = type;
    }
    public Organisateur(){}
    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
