package com.example.gestionconference.Controllers.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.SponsorAccepted;
import com.example.gestionconference.Services.Sponsoring.SponsorAcceptedServices;
import com.example.gestionconference.Services.Sponsoring.SponsorServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ViewAcceptedSponsors implements Initializable {
    public TableView<Sponsor> sponsoraccepted = new TableView<>();

    @FXML
    private Label somme;

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


    @FXML
    private Button Total;

    private final SponsorAcceptedServices sponsorAcceptedServices;

    private final ObservableList<Sponsor> sponsorAccepteddata = FXCollections.observableArrayList();

    public ViewAcceptedSponsors() {
        sponsorAcceptedServices = new SponsorAcceptedServices();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        numtelCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumtel()));
        budgetCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBudget())));

        //double total = somme(sponsorccepted);
        //somme.setText(String.valueOf(total));
        // Load accepted sponsors initially
        try {
            CalculSomme();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadAcceptedSponsors();

    }

    private void loadAcceptedSponsors() {
        SponsorServices us = new SponsorServices();
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

    @FXML
    public void CalculSomme() throws SQLException {
        double totalBudget = 0.0;
        List<SponsorAccepted> acceptedSponsors = sponsorAcceptedServices.getAllAcceptedSponsors();
        for (SponsorAccepted sponsor : acceptedSponsors) {
            totalBudget += sponsor.getBudget();
        }
        somme.setText("Total Budget: " + totalBudget);
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

    public void handleBack(ActionEvent actionEvent) {
        try {
            // Load the ViewSponsor.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewSponsor.fxml"));
            Parent root = loader.load();

            // Set the ViewSponsor scene in the current window
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // Get the current stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

