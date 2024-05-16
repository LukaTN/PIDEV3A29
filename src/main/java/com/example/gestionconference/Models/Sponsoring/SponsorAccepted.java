package com.example.gestionconference.Models.Sponsoring;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.value.ObservableValue;

import javafx.beans.property.*;

public class SponsorAccepted extends Sponsor {
    private double budget;

    public SponsorAccepted() {
        super();
    }

    public SponsorAccepted(int id, String nom, String email, String numtel, double budget) {
        super(id, nom, email, numtel, budget);
        this.budget = budget;
    }

    @Override
    public double getBudget() {
        return budget;
    }

    @Override
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





