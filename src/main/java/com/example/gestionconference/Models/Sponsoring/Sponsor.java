package com.example.gestionconference.Models.Sponsoring;

public class Sponsor {
    private int id;
    private String nom;
    private String email;
    private String numtel;
    private double budget;
    private String status;
    private  String cause;

    public Sponsor() {

    }





    public Sponsor(int id, String nom, String email, String numtel, String status, double budget) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.budget = budget;
        this.status = status;
    }
    public Sponsor(int id, String nom, String email, String numtel) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;

    }

    public Sponsor(int id, String nom, String email, String numtel , String status,String cause) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.status = status;
        this.cause = cause;

    }

    public Sponsor(int id, String nom, String email, String numtel, String selectedStatus, double budget, String cause) {
        this.id=id;
        this.nom=nom;
        this.email=email;
        this.numtel=numtel;
        this.status=selectedStatus;
        this.budget=budget;
        this.cause=cause;

    }

    public Sponsor(String nom, String email, String numtel, double budget, String status) {
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.budget = budget;
        this.status = status;
    }

    public Sponsor(String nom, String email, String numtel, String status, String cause) {
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.status = status;
        this.cause = cause;
    }

    public Sponsor(int id, String nom, String email, String numtel, String status) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numtel = numtel;
        this.status = status;
    }

    public Sponsor(int id, String nom, String email, String numtel, double budget) {
    }


    public int getId() {
        return id;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", numtel='" + numtel + '\'' +
                ", cause='" + cause + '\'' +
                ", budget=" + budget +
                ", status='" + status + '\'' +
                '}';
    }
}
