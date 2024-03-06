package com.example.gestionconference.Controllers.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.SponsorAccepted;
import com.example.gestionconference.Models.Sponsoring.SponsorRejected;
import com.example.gestionconference.Services.Sponsoring.SponsorRejectedServices;
import com.example.gestionconference.Services.Sponsoring.SponsorServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewRejectedSponsors implements Initializable {

    public TableView<Sponsor> sponsorrejected =  new TableView<>();

    @FXML
    private TableColumn<Sponsor, Integer> idCol;

    @FXML
    private TableColumn<Sponsor, String> nomCol;

    @FXML
    private TableColumn<Sponsor, String> numtelCol;

    @FXML
    private TableColumn<Sponsor, String> emailCol;

    @FXML
    private TableColumn<Sponsor, String> causeCol;

    private final SponsorRejectedServices sponsorRejectedServices;
    private final ObservableList<Sponsor> sponsorRejecteddata = FXCollections.observableArrayList();

    public ViewRejectedSponsors() {
        sponsorRejectedServices = new SponsorRejectedServices();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for specific columns
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
//        numtelCol.setCellValueFactory(new PropertyValueFactory<>("numtel"));
//        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        causeCol.setCellValueFactory(new PropertyValueFactory<>("cause"));

        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        numtelCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumtel()));
        causeCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStatus())));

        // Load sponsors initially

        loadRejectedSponsors();

        // Load rejected sponsors initially

        //   loadRejectedSponsors();

    }

    public void loadRejectedSponsors()  {
        SponsorServices us = new SponsorServices() ;
//
//            ObservableList<Sponsor> data = FXCollections.observableArrayList(us.getByStatus("REJECTED"));
//        System.out.println(data);
//            sponsorsTable.setItems(data);

        ObservableList<Sponsor> sponsorData = null;
        try {
            sponsorData = FXCollections.observableArrayList(us.getByStatus("REJECTED"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sponsorrejected.setItems(sponsorData);

    }

}