package com.example.gestionconference.Models;

public class CauseOfRejection extends SponsorRejected {
    private CauseType sujetCause;
    private String description;

    public CauseOfRejection() {
        super();
    }

    public CauseOfRejection(int id, String nom, String email, int numtel, String cause, CauseType sujetCause, String description) {
        super(id, nom, email, numtel, cause);
        this.sujetCause = sujetCause;
        this.description = description;
    }

    public CauseType getSujetCause() {
        return sujetCause;
    }

    public void setSujetCause(CauseType sujetCause) {
        this.sujetCause = sujetCause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CauseOfRejection{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", numtel=" + getNumtel() +
                ", cause='" + getCause() + '\'' +
                ", sujetCause=" + sujetCause +
                ", description='" + description + '\'' +
                '}';
    }
}
