package com.example.gestionconference.Controllers.Sponsoring;

import com.example.gestionconference.Controllers.ConferenceControllers.ControllerCommon;
import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.Status;
import com.example.gestionconference.Services.Sponsoring.SponsorServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddSponsor implements Initializable {

    ControllerCommon cc = new ControllerCommon();
    @FXML
    private Text Nom;

    @FXML
    private TextField nomTF;

    @FXML
    private Text Email;

    @FXML
    private TextField EmailTF;

    @FXML
    private Text NumTel;

    @FXML
    private TextField numtelTF;

    @FXML
    private Text status;

    @FXML
    private ComboBox<String> StatusCB;

    @FXML
    private TextField budgetTF;

    @FXML
    private ComboBox<String> causeCB;

    @FXML
    private Text budgetLabel;

    @FXML
    private Text causeLabel;

    @FXML
    private Button save;

    @FXML
    private Button clear;

    private SponsorServices sponsorServices;

    // ObservableList to hold sponsors for the TableView
    private ObservableList<Sponsor> sponsorList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBox items
        StatusCB.setItems(FXCollections.observableArrayList("ACCEPTED", "REJECTED"));
        causeCB.setItems(FXCollections.observableArrayList("PERSONAL", "LOGISTIC", "FINANCIAL"));

        // Initially hide additional fields
        budgetTF.setVisible(false);
        causeCB.setVisible(false);
        budgetLabel.setVisible(false);
        causeLabel.setVisible(false);

        // Instantiate the sponsorServices object
        sponsorServices = new SponsorServices();

        // Load sponsors initially
        loadSponsors();

        // Add event listener to Status ComboBox to handle visibility changes
        StatusCB.setOnAction(event -> {
            String selectedStatus = StatusCB.getValue();
            if ("ACCEPTED".equals(selectedStatus)) {
                budgetLabel.setVisible(true);
                budgetTF.setVisible(true);
                causeLabel.setVisible(false);
                causeCB.setVisible(false);
            } else if ("REJECTED".equals(selectedStatus)) {
                budgetLabel.setVisible(false);
                budgetTF.setVisible(false);
                causeLabel.setVisible(true);
                causeCB.setVisible(true);
            }
        });
    }


    @FXML
    public void handleSave() {
        String nom = nomTF.getText();
        // Check if the nom field is empty
        if (nom.isEmpty()) {
            System.out.println("Nom field is required!");
            cc.showAlert(Alert.AlertType.ERROR,"Error","Nom field is required!");
            return; // Prevent further execution
        }

        String email = EmailTF.getText();
        String numtel = numtelTF.getText();
        String selectedStatus = StatusCB.getValue();
        String cause = causeCB.getValue();
        double budget = 0.0; // Default value

        // Debug output to check the selected status


        if ("ACCEPTED".equals(selectedStatus)) {
            // Check if the budget text field is visible
            if (budgetTF.isVisible()) {
                try {
                    // Attempt to parse the budget value
                    budget = Double.parseDouble(budgetTF.getText());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid budget format!");
                    return; // Prevent further execution
                }
                try {
                    Sponsor newSponsor = new Sponsor(nom,email,numtel,budget,selectedStatus);
                    sponsorServices.add(newSponsor);
                    // Refresh the sponsors list after adding a new sponsor
                    loadSponsors();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Display an error message indicating that the budget field is required
            System.out.println("Budget field is required for ACCEPTED status!");
            return; // Prevent further execution
        }else{

            try {
                Sponsor newSponsor = new Sponsor(nom,email,numtel,selectedStatus,cause);
                sponsorServices.add(newSponsor);
                // Refresh the sponsors list after adding a new sponsor
                loadSponsors();
                System.out.println("Selected Status: " + selectedStatus);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        clearFields();
    }


    @FXML
    public void handleClear() {
        clearFields();
    }

    private void clearFields() {
        nomTF.clear();
        EmailTF.clear();
        numtelTF.clear();
        StatusCB.getSelectionModel().clearSelection();
        budgetTF.clear();
        causeCB.getSelectionModel().clearSelection();
    }

    // Method to load sponsors from the database and populate the TableView
    private void loadSponsors() {
        try {
            List<Sponsor> sponsors = sponsorServices.getAll();
            sponsorList.clear();
            sponsorList.addAll(sponsors);
            // Assuming you have a TableView called "sponsorsTable"
            // Set the items of the TableView with the updated sponsor list

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AddSponsor() {
        sponsorServices = new SponsorServices();
    }

    public void setSponsor(Sponsor selectedSponsor) {
    }
}
