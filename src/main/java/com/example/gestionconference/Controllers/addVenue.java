package com.example.gestionconference.Controllers;



import com.example.gestionconference.Models.Venue;
import com.example.gestionconference.Services.VenueServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class addVenue {

    @FXML
    private TextField adressTF;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextArea outputTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField venueNameTF;

    private VenueServices venueServices = new VenueServices();

    @FXML
    void addVenue(ActionEvent event) {
        String venueName = venueNameTF.getText();
        String adresse = adressTF.getText();
        String city = cityTF.getText();
        String email = emailTF.getText();
        String phone = phoneTF.getText();

        Venue venue = new Venue(venueName, adresse, city, email, phone);

        try {
            venueServices.addVenue(venue);
            outputTF.setText("Venue added successfully!");
        } catch (SQLException e) {
            outputTF.setText("Failed to add venue: " + e.getMessage());
        }
    }
}