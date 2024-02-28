package com.example.gestionconference.Models;

public class SponsorRejected extends Sponsor {
    private String cause;

    public SponsorRejected() {
        super();
    }

    public SponsorRejected(int id, String nom, String email, int numtel, String cause) {
        super(id, nom, email, numtel);
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "SponsorRejected{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", numtel=" + getNumtel() +
                ", cause='" + cause + '\'' +
                '}';
    }
}
