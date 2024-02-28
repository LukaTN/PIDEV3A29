package com.example.gestionconference.Test;

import com.example.gestionconference.Models.*;
import com.example.gestionconference.Services.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
       /* ParticipantServices ps = new ParticipantServices();*/

        /*Participant p1 = new Participant("adel", "07233345", Date.valueOf("1990-01-01"),"55690408","adel@gmail.com");
        ps.addParticipant(p1);
        Participant p2 = new Participant("ahmed", "07255345", Date.valueOf("1980-01-01"),"55690408","adel@gmail.com");
        ps.addParticipant(p2);
        Participant p3 = new Participant("ali", "07255345", Date.valueOf("1990-02-01"),"55690405","ali@gmail.com");
        ps.addParticipant(p3);
        Participant p4 = new Participant("melek", "07233348", Date.valueOf("1990-03-01"),"55690409","melek@gmail.com");
        ps.addParticipant(p4);
        Participant p5 = new Participant("amen", "07233346", Date.valueOf("1990-04-01"),"55690404","amen@gmail.com");
        ps.addParticipant(p5);*/

        VenueServices vs = new VenueServices();

        Venue v1 = new Venue("fell", "fell@gmail.com", "71250250", "Nabeul", "5 route touristique nabeul", "responsive");
        vs.addVenue(v1);

    }
}