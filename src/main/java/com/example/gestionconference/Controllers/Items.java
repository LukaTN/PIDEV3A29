package com.example.gestionconference.Controllers;

import com.example.gestionconference.Models.Conference;
import com.example.gestionconference.Models.Lieu;
import com.example.gestionconference.Services.ConferenceServices;
import com.example.gestionconference.Services.LieuServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Items {

    @FXML
    private Label capacity;

    @FXML
    private Label city;

    @FXML
    private Label confBudget;

    @FXML
    private Label confDate;

    @FXML
    private Label confLabel;

    @FXML
    private Label confSubject;

    @FXML
    private Label confType;

    @FXML
    private ImageView eventIV;

    @FXML
    private AnchorPane event_aff;

    @FXML
    private Label goverment;

    @FXML
    private Label label;

    @FXML
    private Button modifierBT;

    private Conference conference;

    private ConferenceServices conferenceServices = new ConferenceServices();

    private LieuServices lieuServices = new LieuServices();
    private ControllerCommon cc = new ControllerCommon();

    public void initialize() throws SQLException {
    }

    @FXML
    void modifier(ActionEvent event) throws SQLException {
        Stage stage = (Stage) modifierBT.getScene().getWindow();

        try {
            // Load the UpdateConference scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UpdateConference.fxml"));
            Parent root = loader.load();

            // Get the controller for the UpdateConference scene
            UpdateConference updateConferenceController = loader.getController();

            // Set the selected conference in UpdateConference controller
            updateConferenceController.setSelectedConference(conference);

            // Create a new scene and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error loading UpdateConference scene");
            errorAlert.showAndWait();
            // Handle the exception (show an alert or log the error)
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this conference?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    conferenceServices.deleteConference(conference);
                    event_aff.setVisible(false);
                    ConferenceList conferenceList = (ConferenceList) event_aff.getProperties().get("controller");
                    conferenceList.diplayConferences();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Unable to delete this conference");
                    errorAlert.showAndWait();
                }
            }
        });
    }

    public void setData(Conference conference) throws SQLException {
        this.conference = conference;
        int lieu_id = conference.getConferenceLocation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(conference.getDate());
        confDate.setText(formattedDate);
        confLabel.setText(conference.getName());
        confSubject.setText(conference.getSubject());
        confType.setText(conference.getType().toString());
        confBudget.setText(String.valueOf(conference.getBudget()));
        Lieu lieu = lieuServices.getLieuByid(lieu_id);
        city.setText(lieu.getPlace());
        capacity.setText(String.valueOf(lieu.getCapacity()));
        goverment.setText(lieu.getZone());
        label.setText(lieu.getLabel());
    }
}
