package com.example.gestionconference.Controllers.ConferenceControllers;



import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Models.UserModels.User;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javafx.scene.control.ComboBox;

import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddLieu implements Initializable  {


    LieuServices ss = new LieuServices();
    String selectedValue;
    @FXML
    private TextField confCountry;

    @FXML
    private ComboBox<String> LDGov;

    @FXML
    private TextField TFCapacity;

    @FXML
    private TextField TFPlaceNom;

    @FXML
    private TextField TFZone;

    @FXML
    private Text finalResp;

    private User user;


    ControllerCommon cc = new ControllerCommon();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        List<String> states = StatesApi.getbyCountry("Tunisia");
//        LDGov.getItems().addAll(states);
    }


    @FXML
    void onSelect(ActionEvent event) {
         selectedValue = LDGov.getValue();
    }

    @FXML
    void onAddButton(ActionEvent event) throws IOException {
        try {
            if (selectedValue == null || TFCapacity.getText().isEmpty() || TFZone.getText().isEmpty() || TFPlaceNom.getText().isEmpty()) {
                cc.showAlert(Alert.AlertType.ERROR, "Missing Information", "Please fill in all fields.");
                return;
            }


            try {
                int capacity = Integer.parseInt(TFCapacity.getText());
                if (capacity <= 0) {
                    cc.showAlert(Alert.AlertType.ERROR, "Invalid Capacity", "Capacity must be a positive integer.");
                    return;
                }
            } catch (NumberFormatException e) {
                cc.showAlert(Alert.AlertType.ERROR, "Invalid Capacity", "Please enter a valid integer for Capacity.");
                return;
            }

            Lieu lieu = new Lieu(selectedValue, TFZone.getText(), Integer.parseInt(TFCapacity.getText()), TFPlaceNom.getText());
            ss.addLieu(lieu);

            cc.showAlert(Alert.AlertType.INFORMATION, "Success", "Place added successfully.");
            clearFields();

            // Navigate back to the Conference.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/Conference.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            finalResp.setText("Error adding Place: " + e.getMessage());
        }


    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        cc.jump("Confera", "/com/example/gestionconference/Fxml/ConferenceFXML/Conference.fxml",TFZone);
    }

    void clearFields() {
        LDGov.setValue(null);
        TFCapacity.setText("");
        TFZone.setText("");
        TFPlaceNom.setText("");
    }

    public void toPlaces(ActionEvent actionEvent) {
        cc.jump("Confera", "/com/example/gestionconference/Fxml/ConferenceFXML/LieuList.fxml",TFZone);
    }

    public void toNewConf(ActionEvent actionEvent) {
        cc.jump("Confera", "/com/example/gestionconference/Fxml/ConferenceFXML/Conference.fxml",TFZone);
    }

    @FXML
    void onMouseClickedGoverment(MouseEvent event) {
        String country = confCountry.getText();
        try {
            List<String> states = StatesApi.getbyCountry(country);
            LDGov.getItems().clear();
            LDGov.getItems().addAll(states);
            if (LDGov.getItems().isEmpty()) {
                cc.showAlert(Alert.AlertType.ERROR, "Invalid Country", "Please enter a valid country name or enter country in english.");
            }
        }catch (Exception e){
            cc.showAlert(Alert.AlertType.ERROR, "Invalid Country", "Please enter a valid country name.");
        }

    }

    public void initData(User user) {
        this.user=user;
    }
}




