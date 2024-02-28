package com.example.gestionconference.Controllers;

import com.example.gestionconference.Models.Participant;
import com.example.gestionconference.Services.ParticipantServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AfficherParticipant {

    ParticipantServices ps = new ParticipantServices();
    @FXML
    private TableColumn<Participant, Date> birthdayTV;

    @FXML
    private TextField cinTF;

    @FXML
    private TableColumn<Participant, String> cinTV;

    @FXML
    private TableColumn<Participant, String> emailTV;

    @FXML
    private TextField nameTF;

    @FXML
    private TableColumn<Participant, String> nameTV;

    @FXML
    private TableView<Participant> participantTV;

    @FXML
    private TableColumn<Participant, String> phoneTV;

    @FXML
    void recherche(ActionEvent event) {
        String name = nameTF.getText();
        Participant participant = ps.searchByName(name);
        ObservableList<Participant> participantObservableList = FXCollections.observableArrayList(participant);
        participantTV.setItems(participantObservableList);
    }

    @FXML
    void afficher(ActionEvent event) {
        try {
            List<Participant> participants = ps.getAll();
            ObservableList<Participant> participantObservableList = FXCollections.observableArrayList(participants);
            participantTV.setItems(participantObservableList);
        } catch (SQLException e) {
            System.err.println("Error retrieving all participants: " + e.getMessage());
        }
    }
}




