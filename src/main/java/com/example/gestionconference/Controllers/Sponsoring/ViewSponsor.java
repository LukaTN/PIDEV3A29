package com.example.gestionconference.Controllers.Sponsoring;

import com.example.gestionconference.Controllers.ConferenceControllers.ControllerCommon;
import com.example.gestionconference.Controllers.Sponsoring.AddSponsor;
import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Services.Sponsoring.SponsorServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewSponsor implements Initializable {

    @FXML
    private TableView<Sponsor> sponsors;

    @FXML
    private TableColumn<Sponsor, String> idCol;

    @FXML
    private TableColumn<Sponsor, String> nomCol;

    @FXML
    private TableColumn<Sponsor, String> emailCol;

    @FXML
    private TableColumn<Sponsor, String> numtelCol;

    @FXML
    private TableColumn<Sponsor, String> statusCol;


    @FXML
    private Button addsponsor;

    @FXML
    private Button updatesponsor;

    @FXML
    private Button deletesponsor;

    @FXML
    private Button viewaccepted;

    @FXML
    private Button viewrejected;


    ControllerCommon cc = new ControllerCommon();

    private SponsorServices sponsorServices;

    // ObservableList to hold sponsors for the TableView
    private final ObservableList<Sponsor> sponsorList = FXCollections.observableArrayList();

    public ViewSponsor() {
        sponsorServices = new SponsorServices();
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for specific columns
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        numtelCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumtel()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStatus())));

        // Load sponsors initially
        loadSponsors();
    }

    private void loadSponsors() {
        try {
            ObservableList<Sponsor> sponsorData = FXCollections.observableArrayList(sponsorServices.getAll());
            sponsors.setItems(sponsorData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void rowClicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void handleviewr(ActionEvent event) throws IOException {
        // Load the interface for viewing accepted sponsors
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewRejectedSponsors.fxml"));
        Parent root = loader.load();

        // Show the interface
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void handleviewa(ActionEvent event) {
        try {
            // Load the interface for viewing accepted sponsors
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewAcceptedSponsors.fxml"));
            Parent root = loader.load();

            // Show the interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void handleadd(ActionEvent actionEvent) {
        try {
            // Load the AddSponsor.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/AddSponsor.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddSponsor addSponsor = loader.getController();

            // Show the AddSponsor scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleupdate(ActionEvent actionEvent) {
        Sponsor selectedSponsor = (Sponsor) sponsors.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                // Load the AddSponsor.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/AddSponsor.fxml"));
                Parent root = loader.load();

                // Get the controller instance
                AddSponsor AddSponsor = loader.getController();

                // Pass the selected sponsor to the AddSponsor controller
                AddSponsor.setSponsor(selectedSponsor);

                // Show the AddSponsor scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Show error message
        }
    }

    public void handledelete(ActionEvent actionEvent) { Sponsor selectedSponsor = (Sponsor) sponsors.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                sponsorServices.delete(selectedSponsor);
                sponsorList.remove(selectedSponsor);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception
            }
        } else {
            // Show error message
        }
    }
}


