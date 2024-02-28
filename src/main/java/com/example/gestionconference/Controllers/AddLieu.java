package com.example.gestionconference.Controllers;



import com.example.gestionconference.Models.Lieu;
import com.example.gestionconference.Services.LieuServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddLieu implements Initializable  {
    LieuServices ss = new LieuServices();
    String selectedValue;
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

    ControllerCommon cc = new ControllerCommon();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LDGov.getItems().addAll("Ariana","Gafsa","Kef","Kasserine","Beja","Jendouba","Medenine","Monastir","Nabeul","Sfax","SidiBouzid","Siliana","Sousse","Tataouine","Tozeur","Tunis","Zaghouan");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Conference.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Conference.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    void clearFields() {
        LDGov.setValue(null);
        TFCapacity.setText("");
        TFZone.setText("");
        TFPlaceNom.setText("");
    }

    public void toPlaces(ActionEvent actionEvent) {
        cc.jump("Confera","/com/example/gestionconference/Fxml/LieuList.fxml",TFZone);
    }

    public void toNewConf(ActionEvent actionEvent) {
        cc.jump("Confera","/com/example/gestionconference/Fxml/Conference.fxml",TFZone);
    }
}




