package com.example.gestionconference.Controllers;

import com.example.gestionconference.Models.Status;
import com.example.gestionconference.Models.Venue;
import com.example.gestionconference.Services.VenueServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class afficherVenue {
    private final VenueServices vs = new VenueServices();

    @FXML
    private TableView<Venue> venueTV;

    @FXML
    private TextField keywordTF;

    @FXML
    private TableColumn<Venue, String> nameTC;

    @FXML
    private TableColumn<Venue, String>emailTC;

    @FXML
    private TableColumn<Venue, String> phoneTC;

    @FXML
    private TableColumn<Venue, String>cityTC;

    @FXML
    private TableColumn<Venue, String> adressTC;

    @FXML
    private TableColumn<Venue, String> outputTC;

    @FXML
    private TableColumn<Venue, Status> StatusTC;
    @FXML

    public void initialize() {

        List<Venue> venues = null;
        try {
            venues = vs.getAllVenue();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<Venue> venueObservableList = FXCollections.observableArrayList(venues);
        venueTV.setItems(venueObservableList);


        nameTC.setCellValueFactory(new PropertyValueFactory<>("venueName"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneTC.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cityTC.setCellValueFactory(new PropertyValueFactory<>("city"));
        adressTC.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        outputTC.setCellValueFactory(new PropertyValueFactory<>("output"));
        StatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
