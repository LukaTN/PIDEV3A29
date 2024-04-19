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

    @FXML
    private TextField search;


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
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                advancedSearch(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception
            }
        });

    }


    private void loadSponsors() {
        try {
            // Fetch sponsors from the database
            ObservableList<Sponsor> fetchedSponsors = FXCollections.observableArrayList(sponsorServices.getAll());

            // Clear the existing list and add the fetched sponsors
            sponsorList.clear();
            sponsorList.addAll(fetchedSponsors);

            // Set the items in the TableView
            sponsors.setItems(fetchedSponsors);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void rowClicked(MouseEvent mouseEvent) {
    }

    private void advancedSearch(String query) throws SQLException {
        ObservableList<Sponsor> filteredList = FXCollections.observableArrayList();
        for (Sponsor sponsor : sponsorList) {
            // Compare le nom du sponsor avec la requête de recherche
            if (sponsor.getNom().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(sponsor);
            }
        }
        sponsors.setItems(filteredList);
    }


    @FXML
    public void handleviewr(ActionEvent event) throws IOException {
        try {
            // Load the interface for viewing accepted sponsors
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewRejectedSponsors.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ViewRejectedSponsors viewRejectedSponsors = loader.getController();

            // Add the AddSponsor scene to the current scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) viewrejected.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);

            // Refresh the sponsors after addition
            loadSponsors();

        } catch (IOException e)

        {
            e.printStackTrace();
        }
    }




    public void handleviewa(ActionEvent event) {
        try {
            // Load the interface for viewing accepted sponsors
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewAcceptedSponsors.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ViewAcceptedSponsors viewAcceptedSponsors = loader.getController();

            // Add the AddSponsor scene to the current scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) viewaccepted.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);

            // Refresh the sponsors after addition
            loadSponsors();

        } catch (IOException e) {
            e.printStackTrace();
        }
        }





    public void handleadd(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/AddSponsor.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddSponsor addSponsor = loader.getController();

            // Add the AddSponsor scene to the current scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) addsponsor.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);

            // Refresh the sponsors after addition
            loadSponsors();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void delete() {
        Sponsor selectedSponsor = sponsors.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                sponsorServices.delete(selectedSponsor.getId());
                loadSponsors(); // Recharge la liste des sponsors après la suppression
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception, par exemple afficher un message d'erreur
            }
        } else {
            // Afficher un message indiquant qu'aucun sponsor n'est sélectionné
        }
    }

    @FXML
    public void update() {
        Sponsor selectedSponsor = sponsors.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                // Load the AddSponsor.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/AddSponsor.fxml"));
                Parent root = loader.load();

                // Get the controller instance
                AddSponsor addSponsor = loader.getController();

                // Set the sponsor to be updated
                addSponsor.setSponsor(selectedSponsor);
                loadSponsors();

                // Show the AddSponsor scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        } else {
            // Show error message
        }
//        fillSubinputs();
  }


    public void onNext(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/FinancialIncomes.fxml"));
        Parent root = loader.load();

        // Add the AddSponsor scene to the current scene
        Scene scene = new Scene(root);
        Stage stage = (Stage) addsponsor.getScene().getWindow(); // Get the current stage
        stage.setScene(scene);
        stage.show();
    }
}


