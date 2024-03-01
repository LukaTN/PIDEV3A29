package com.example.gestionconference.Models.Sponsoring;

public class SponsorAccepted extends Sponsor {
    private double budget;

    public SponsorAccepted() {
        super();
    }

    public SponsorAccepted(int id, String nom, String email, String numtel, double budget) {
        super(id, nom, email, numtel, "ACCEPTED");
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
