package com.example.gestionconference.Controllers.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.SponsorAccepted;
import com.example.gestionconference.Services.Sponsoring.SponsorAcceptedServices;
import com.example.gestionconference.Services.Sponsoring.SponsorRejectedServices;
import com.example.gestionconference.Services.Sponsoring.SponsorServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ViewAcceptedSponsors implements Initializable {
    public TableView<Sponsor> sponsoraccepted =  new TableView<>();
    public Label somme;

    @FXML
    private TableView<Sponsor> sponsors;

    @FXML
    private TableColumn<Sponsor, String> idCol;

    @FXML
    private TableColumn<Sponsor, String> nomCol;

    @FXML
    private TableColumn<Sponsor, String> numtelCol;

    @FXML
    private TableColumn<Sponsor, String> emailCol;

    @FXML
    private TableColumn<Sponsor, String> budgetCol;



    private final SponsorAcceptedServices sponsorAcceptedServices;

    private final ObservableList<Sponsor> sponsorAccepteddata = FXCollections.observableArrayList();

    public ViewAcceptedSponsors() {
        sponsorAcceptedServices = new SponsorAcceptedServices();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // Initialize TableView and columns
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
//        numtelCol.setCellValueFactory(new PropertyValueFactory<>("numtel"));
//        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        budgetCol.setCellValueFactory(new PropertyValueFactory<>("budget"));
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        numtelCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumtel()));
        budgetCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBudget())));
        // Load accepted sponsors initially
        loadAcceptedSponsors();
        try {
            setSomme();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAcceptedSponsors() {
        SponsorServices us = new SponsorServices() ;
//
//            ObservableList<Sponsor> data = FXCollections.observableArrayList(us.getByStatus("ACCEPTED"));
//        System.out.println(data);
//            sponsorsTable.setItems(data);
        ObservableList<Sponsor> sponsorData = null;
        try {
            sponsorData = FXCollections.observableArrayList(us.getByStatus("ACCEPTED"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sponsoraccepted.setItems(sponsorData);

    }


    public void setSomme() throws SQLException {
        double totalBudget = 0.0;
        List<SponsorAccepted> acceptedSponsors = sponsorAcceptedServices.getAllAcceptedSponsors();
        for (SponsorAccepted sponsor : acceptedSponsors) {
            totalBudget += sponsor.getBudget();
        }
        somme.setText(String.valueOf(totalBudget));
    }

    public void handletri(ActionEvent actionEvent) {
        // Get the TableColumn for the budget
        TableColumn<Sponsor, String> budgetColumn = budgetCol;

        // Get the current items from the table
        ObservableList<Sponsor> sponsorList = FXCollections.observableArrayList(sponsoraccepted.getItems());

        // Sort the items based on the budget column in ascending order
        sponsorList.sort(Comparator.comparing(Sponsor::getBudget));

        // Set the sorted items to the table
        sponsoraccepted.setItems(sponsorList);
    }

    public void handlenotif(ActionEvent actionEvent) {
    }
}

