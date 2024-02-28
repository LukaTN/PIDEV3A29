package com.example.gestionconference.Models;

public class Sponsor {
    private int id;
    private String nom;
    private String email;
    private int numtel;

    private Status status;

    public Sponsor(){

    }

    public Sponsor(int id, String nom, String email, int numtel ,Status status) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.status = status;
    }

    public Sponsor(String nom, String email, int numtel) {
    }

    public Sponsor(int id, String nom, String email, int numtel) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", numtel=" + numtel +
                ", status=" + status +
                '}';
    }
}

