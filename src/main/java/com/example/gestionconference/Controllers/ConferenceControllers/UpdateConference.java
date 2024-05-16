package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    private User user;


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
        selectedConference.setType(ChTypeConf.isSelected());

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
            if (!TFConfName.getText().matches("^[a-zA-Z0-9\\s_-]+$")) {
                cc.showAlert(Alert.AlertType.ERROR, "Invalid Conference Name", "Conference name should contain only alphabets, numbers, spaces, hyphens, and underscores.");
                return;
            }

            // If all validations pass, update the conference

            selectedConference.setName(TFConfName.getText());
            selectedConference.setSubject(TASubject.getText());
            selectedConference.setBudget(budget);
            selectedConference.setType(ChTypeConf.isSelected());
            selectedConference.setImage(selectedConference.getImage());
           /* Conference updatedConference = new Conference();
            updatedConference.setId(selectedConference.getId());
            updatedConference.setName(conferenceName);
            updatedConference.setSubject(subject);
            updatedConference.setBudget(budget);
            updatedConference.setDate(selectedConference.getDate());
            updatedConference.setType(ChTypeConf.isSelected());
            updatedConference.setImage(selectedConference.getImage());*/

            conferenceServices.updateConference(selectedConference);
            cc.showAlert(Alert.AlertType.INFORMATION, "Success", "Conference updated successfully");
        } catch (Exception e) {
            cc.showAlert(Alert.AlertType.ERROR, "Error", "Error updating conference: " + e.getMessage());
        }
    }

    private ConferenceType transform() {
        return ChTypeConf.isSelected() ? ConferenceType.PRIVATE : ConferenceType.PUBLIC;
    }


    @FXML
    void importImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the FileChooser dialog
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                String imageName = selectedFile.getName(); // Get only the filename
                String imagePath = "C:\\Users\\melek\\Desktop\\3a29\\pidevwebbb\\conferaWeb\\public\\images\\" + imageName;

                // Copy the selected file to the specified directory
                Files.copy(selectedFile.toPath(), new File(imagePath).toPath(), StandardCopyOption.REPLACE_EXISTING);

                imageConf.setImage(new Image("file:///" + imagePath)); // Set the image path with file:/// prefix
                selectedConference.setImage(imageName); // Store the full image path in the database

            } catch (Exception e) {
                // Handle exceptions gracefully, like logging or showing an error message
                cc.showAlert(Alert.AlertType.ERROR, "Error", "Error processing image: " + e.getMessage());
            }
        }

    }

    public void initData(User user) {
        this.user = user;
//        username.setText(user.getUsername());
//        role.setText(user.getRole());
//        try {
//            Image image = new Image(new ByteArrayInputStream(user.getProfilePicture()));
//            imageUser.setImage(image);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}