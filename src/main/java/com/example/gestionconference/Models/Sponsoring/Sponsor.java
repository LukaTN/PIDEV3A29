package com.example.gestionconference.Models.Sponsoring;

public class Sponsor {
    private int id;
    private String nom;
    private String email;
    private String numtel;

    private String status;

    public Sponsor() {

    }

    public Sponsor(int id, String nom, String email, String numtel, String status) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.status = status;
    }
    public Sponsor(int id, String nom, String email, String numtel) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;

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

    public String getNumtel() {
        return numtel;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", numtel='" + numtel + '\'' +
                ", status=" + status +
                '}';
    }
}
