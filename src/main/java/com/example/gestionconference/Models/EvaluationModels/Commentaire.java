package com.example.gestionconference.Models.EvaluationModels;

import java.util.Objects;

public class Commentaire {
    String caractere ;
    int idCommentaire ;
    public Commentaire (int idCommentaire,String caractere )
    {
        this.caractere=caractere;
        this.idCommentaire=idCommentaire;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "caractere='" + caractere + '\'' +
                ", idCommentaire=" + idCommentaire +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 11 * hash + Objects.hashCode(this.caractere);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commentaire other = (Commentaire) obj;
        if (this.caractere != other.caractere) {
            return false;
        }

        return true;
    }

    public String getCaractere() {
        return caractere;
    }

    public int getIdCommentaire() {
        return idCommentaire;
    }



    public void setCaractere(String caractere) {
        this.caractere = caractere;
    }

    public void  setidCommentaire() {
        this.idCommentaire = idCommentaire;
    }




}
