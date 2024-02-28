package com.example.gestionconference.Models;

public class SponsorAccepted extends Sponsor {
    private double budget;

    public SponsorAccepted() {
        super();
    }

    public SponsorAccepted(int id, String nom, String email, int numtel, double budget) {
        super(id, nom, email, numtel);
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "SponsorAccepted{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", numtel=" + getNumtel() +
                ", budget=" + budget +
                '}';
    }
}
