package com.example.gestionconference.Models.EvaluationModels;

import java.util.Objects;

public class Note {
    int  idnotation ;
    int  notation;
    String image ;
    int idNotation;
    public Note ( int idNotation,int  notation, String image )
    {
        this.idNotation=idNotation;
        this.notation=notation;
        this.image=image ;
    }


    @Override
    public String toString() {
        return "Note{" +"idNotation=" + idNotation+ "notaion=" + notation + "image=" + image + '}';
    }


    @Override
    public int hashCode() {
        int hash = 7;

        hash = 11 * hash + Objects.hashCode(this.notation);
        hash = 11 * hash + Objects.hashCode(this.image);
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
        final Note other = (Note) obj;
        if (this.notation != other.notation) {
            return false;
        }
        if (this.image != other.image) {
            return false;
        }

        return true;
    }



    public int getIdNotation() {
        return idNotation;
    }
    public int getNotation() {
        return notation;
    }


    public String getImage() {
        return image;
    }


    public void  setIdNotation(int idNotation) {
        this.idNotation = idNotation;
    }

    public void setNotation(int notation) {
        this.notation = notation;
    }


    public void setImage(String image) {
        this.image = image;
    }




}
