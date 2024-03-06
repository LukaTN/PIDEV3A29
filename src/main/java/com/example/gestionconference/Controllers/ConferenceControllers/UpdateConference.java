package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateConference implements Initializable {

    @FXML
    private CheckBox ChTypeConf;

    @FXML
    private ComboBox<String> LDLocations;

    @FXML
    private TextField SpBudget;

    @FXML
    private ImageView imageConf;

    @FXML
    private TextArea TASubject;

    @FXML
    private TextField TFConfName;

    @FXML
    private DatePicker TFDate;

    private List<Lieu> lieux;
    private ConferenceServices conferenceServices = new ConferenceServices();

    private String selectedValue;
    private int lieuId;


    ControllerCommon cc = new ControllerCommon();
    private Conference selectedConference; // Added field to store the selected conference




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }



    @FXML
    void onViewList(ActionEvent event) throws IOException {

        cc.jump("Confera", "/com/example/gestionconference/Fxml/ConferenceFXML/ConferenceList.fxml", TFConfName);
    }

    public void setSelectedConference(Conference selectedConference) {
        this.selectedConference = selectedConference;
        TFConfName.setText(selectedConference.getName());
        TASubject.setText(selectedConference.getSubject());
        SpBudget.setText(String.valueOf(selectedConference.getBudget()));
        Image existingImage = new Image("file:" + selectedConference.getImage());
        imageConf.setImage(existingImage);
        if (selectedConference.getType() == ConferenceType.PRIVATE) {
            ChTypeConf.setSelected(true);
        } else {
            ChTypeConf.setSelected(false);
        }

    }


    @FXML
    void onUpdate(ActionEvent event) {

        try {
            // Implement input validation for the updated fields
            String conferenceName = TFConfName.getText();
            String subject = TASubject.getText();
            String budgetText = SpBudget.getText();

            if (conferenceName.isEmpty() || subject.isEmpty() || budgetText.isEmpty()) {
                cc.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
                return;
            }

            double budget;
            try {
                budget = Double.parseDouble(budgetText);
                if (budget < 0) {
                    cc.showAlert(Alert.AlertType.ERROR, "Error", "Budget must be a non-negative number");
                    return;
                }
            } catch (NumberFormatException e) {
                cc.showAlert(Alert.AlertType.ERROR, "Error", "Invalid budget value");
                return;
            }
            if (!TFConfName.getText().matches("^[a-zA-Z0-9]+$")) {
                cc.showAlert(Alert.AlertType.ERROR, "Invalid Conference Name", "Conference name should contain only alphabets and numbers.");
                return;
            }

            // If all validations pass, update the conference
            Conference updatedConference = new Conference();
            updatedConference.setId(selectedConference.getId());
            updatedConference.setName(conferenceName);
            updatedConference.setSubject(subject);
            updatedConference.setBudget(budget);
            updatedConference.setDate(selectedConference.getDate());
            updatedConference.setType(transform());
            updatedConference.setImage(selectedConference.getImage());

            conferenceServices.updateConference(updatedConference);
            cc.showAlert(Alert.AlertType.INFORMATION, "Success", "Conference updated successfully");
        } catch (Exception e) {
            cc.showAlert(Alert.AlertType.ERROR, "Error", "Error updating conference: " + e.getMessage());
        }
    }

    private ConferenceType transform() {
        return ChTypeConf.isSelected() ? ConferenceType.PRIVATE : ConferenceType.PUBLIC;
    }


    public void importImage(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            // Show open file dialog
            Stage stage = (Stage) imageConf.getScene().getWindow();
            java.io.File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                // Load and set the selected image
                Image selectedImage = new Image(selectedFile.toURI().toString());
                imageConf.setImage(selectedImage);

                // Update the image path in the Conference object
                selectedConference.setImage(selectedFile.getAbsolutePath());

                // You may want to display a success message or handle other tasks here
            }

        }catch (Exception e) {
            cc.showAlert(Alert.AlertType.ERROR, "Error", "Error loading image: " + e.getMessage());
        }

    }
}
