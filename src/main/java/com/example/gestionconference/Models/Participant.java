package com.example.gestionconference.Models;


import java.sql.Date;

public class Participant {
    private int id;
    private String nom;
    private String cin;
    private Date DateN;
    private String numTel;
    private String email;

    public Participant() {

    }

    public Participant( String nom, String cin, Date dateN, String numTel, String email) {
        this.nom = nom;
        this.cin = cin;
        DateN = dateN;
        this.numTel = numTel;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Date getDateN() {
        return DateN;
    }

    public void setDateN(Date dateN) {
        DateN = dateN;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "participant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", cin='" + cin + '\'' +
                ", DateN=" + DateN +
                ", numTel='" + numTel + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
