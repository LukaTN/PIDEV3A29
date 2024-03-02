package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AddConference  implements Initializable {
    ConferenceServices ss = new ConferenceServices();
    String selectedValue;

    @FXML
    private CheckBox ChTypeConf;

    @FXML
    private TextField SpBudget;

    @FXML
    private TextArea TASubject;

    @FXML
    private TextField TFConfName;

    @FXML
    private DatePicker TFDate;

    @FXML
    private ComboBox<String> LDLocations;
    @FXML
    private ImageView imageConf;

    @FXML
    private TextField TFOrgName;

    @FXML
    private Text finalResult;
    List<Lieu> lieux ;
    int lieuId;

    private Conference conference = new Conference();

    ControllerCommon cc = new ControllerCommon();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LieuServices ser = new LieuServices();
        try {
            // Retrieve locations from the service
             lieux = ser.getAllLocations(); // Assuming Location is the class representing a location
            // Extract location names and add them to the ListView
            List<String> locationNames = new ArrayList<>();
            for (Lieu lieu : lieux) {
                locationNames.add(lieu.getLabel());
            }
            LDLocations.getItems().addAll(locationNames);
        } catch (SQLException e) {
            // Handle SQL exception gracefully, like logging or showing an error message
            System.out.println(e.getMessage()); // For debugging purposes, you might want to log the exception
        }


    }


    public ConferenceType transform() {
        if (ChTypeConf.isSelected()) {
            return ConferenceType.PRIVATE;
        } else  {
            return ConferenceType.PUBLIC;
        }
    }
    @FXML
    void onSelectLocation(ActionEvent event) {
        selectedValue = LDLocations.getValue();
        if (selectedValue != null) {

            lieuId = lieux.stream()
                    .filter(lieu -> selectedValue.equals(lieu.getLabel()))
                    .mapToInt(Lieu::getId)
                    .findFirst()
                    .orElse(-1);
            System.out.println(lieuId);
        }
    }


    @FXML
    void onNewLocation(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/AddLieu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onViewList(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/ConferenceList.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void onAddConf(ActionEvent event) {
        try {
            // Validate input fields
            if (TFConfName.getText().isEmpty() || TFDate.getValue() == null || TASubject.getText().isEmpty()
                    || SpBudget.getText().isEmpty() || LDLocations.getValue() == null) {
                cc.showAlert(Alert.AlertType.ERROR, "Missing Information", "Please fill in all fields.");
                return;
            }

            try {
                int capacity = Integer.parseInt(SpBudget.getText());
                if (capacity <= 0) {
                    cc.showAlert(Alert.AlertType.ERROR, "Invalid Budget", "Budget must be a positive integer.");
                    return;
                }
            } catch (NumberFormatException e) {
               cc.showAlert(Alert.AlertType.ERROR, "Invalid Budget", "Please enter a valid integer for budget.");
                return;
            }
            try {
                Double.parseDouble(SpBudget.getText());
            } catch (NumberFormatException e) {
                cc.showAlert(Alert.AlertType.ERROR, "Invalid Budget", "Please enter a valid numeric budget.");
                return;
            }
            LocalDate selectedDate = TFDate.getValue();
            if (selectedDate == null || selectedDate.isBefore(LocalDate.now())) {
                cc.showAlert(Alert.AlertType.ERROR, "Error", "Please select a valid date (not less than the current date)");
                return;
            }
            String ldLocationsValue = LDLocations.getValue();
            if (ldLocationsValue == null){
                cc.showAlert(Alert.AlertType.ERROR,"Error","Plese select location or create one click on button New Location for more");
              return;
            }
            if (!TFConfName.getText().matches("^[a-zA-Z0-9]+$")) {
                cc.showAlert(Alert.AlertType.ERROR, "Invalid Conference Name", "Conference name should contain only alphabets and numbers.");
                return;
            }
            java.sql.Date sqlDate = java.sql.Date.valueOf(TFDate.getValue());

            conference.setName(TFConfName.getText());
            conference.setDate(sqlDate);
            conference.setSubject(TASubject.getText());
            conference.setBudget(Double.parseDouble(SpBudget.getText()));
            conference.setType(transform());
            conference.setEmplacement(lieuId);

//            Conference s = new Conference(
//                    TFConfName.getText(),
//                    sqlDate,
//                    TASubject.getText(),
//                    Double.parseDouble(SpBudget.getText()),
//                    transform(),
//                    lieuId,
//                    1
//            );

            ss.addConference(conference);
            cc.showAlert(Alert.AlertType.INFORMATION, "Success", "Conference added successfully");
            clearFields();
        } catch (Exception e) {
            cc.showAlert(Alert.AlertType.ERROR, "Error", "Error adding conference: " + e.getMessage());
            System.out.println(e.getMessage());
        }
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
                // Set the image path in the Conference object
                String imagePath = selectedFile.getAbsolutePath();
                imageConf.setImage(new Image(selectedFile.toURI().toString()));

                // Set the image path in your Conference object (assuming imagePath is a property in the Conference class)
                // Replace "yourConferenceObject" with the actual instance of your Conference object
                conference.setImage(imagePath);

            } catch (Exception e) {
                // Handle exceptions gracefully, like logging or showing an error message
                cc.showAlert(Alert.AlertType.ERROR, "Error", "Error processing image: " + e.getMessage());
            }
        }

    }








    void clearFields(){
        ChTypeConf.setSelected(false);
        SpBudget.setText("");
        TASubject.setText("");
        TFConfName.setText("");
        TFDate.setValue(null);
        LDLocations.setValue(null);
    }

}
