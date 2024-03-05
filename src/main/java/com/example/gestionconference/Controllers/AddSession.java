package com.example.gestionconference.Controllers;


import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Services.SessionServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class AddSession implements Initializable {

    SessionServices ss = new SessionServices();

    @FXML
    private Spinner<Integer> heureDebut;

    @FXML
    private Spinner<Integer> heureFin;

    private int hourDebut;
    private int hourFin;

    @FXML
    private TextField nbPresence;

    @FXML
    private TextArea objectif;

    @FXML
    private Label result;

    @FXML
    private Label resultNbpres;

    @FXML
    private Label resultObjectif;

    @FXML
    private Label finalResult;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactoryHeureDebut = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> valueFactoryHeureFin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        heureDebut.setValueFactory(valueFactoryHeureDebut);
        heureFin.setValueFactory(valueFactoryHeureFin);
        hourDebut = heureDebut.getValue();
        hourFin = heureFin.getValue();

    }

    @FXML
    void addSession(ActionEvent event)  {
        int hourDebutValue = heureDebut.getValue();
        int hourFinValue = heureFin.getValue();
        if (hourDebutValue > hourFinValue) {
            result.setText("Heure debut est après heure fin");
            //clearResultLabels();
        } else if (objectif.getText().isEmpty()) {
            resultObjectif.setText("Pls enter objectif");
            //clearResultLabels();
        } else if (nbPresence.getText().isEmpty()) {
            resultNbpres.setText("Pls enter nbr presence");
            //clearResultLabels();
        } else {
            try {
                Session s = new Session(hourDebutValue, hourFinValue, objectif.getText(), Integer.parseInt(nbPresence.getText()));
                ss.addSession(s);
                clearInputFields();
                clearResultLabels();
                finalResult.setText("Session added successfully");
            } catch (Exception e) {
                finalResult.setText("Error adding session: " + e.getMessage());
            }
        }
    }
    private void clearResultLabels() {
        result.setText("");
        resultObjectif.setText("");
        resultNbpres.setText("");
        finalResult.setText("");
    }

    private void clearInputFields() {
        heureDebut.getValueFactory().setValue(1);
        heureFin.getValueFactory().setValue(1);
        nbPresence.clear();
        objectif.clear();
    }
    }

