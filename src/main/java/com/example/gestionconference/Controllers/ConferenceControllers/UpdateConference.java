package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;

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
        TFDate.setValue(selectedConference.getDate().toLocalDate());
        TASubject.setText(selectedConference.getSubject());
        SpBudget.setText(String.valueOf(selectedConference.getBudget()));

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

            // If all validations pass, update the conference
            Conference updatedConference = new Conference();
            updatedConference.setId(selectedConference.getId());
            java.sql.Date sqlDate = java.sql.Date.valueOf(TFDate.getValue());
            updatedConference.setName(conferenceName);
            updatedConference.setDate(sqlDate);
            updatedConference.setSubject(subject);
            updatedConference.setBudget(budget);
            updatedConference.setType(transform());

            conferenceServices.updateConference(updatedConference);
            cc.showAlert(Alert.AlertType.INFORMATION, "Success", "Conference updated successfully");
        } catch (Exception e) {
            cc.showAlert(Alert.AlertType.ERROR, "Error", "Error updating conference: " + e.getMessage());
        }
    }

    private ConferenceType transform() {
        return ChTypeConf.isSelected() ? ConferenceType.PRIVATE : ConferenceType.PUBLIC;
    }


}
