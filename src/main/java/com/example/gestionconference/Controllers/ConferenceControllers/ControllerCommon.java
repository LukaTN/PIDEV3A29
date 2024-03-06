package com.example.gestionconference.Controllers.ConferenceControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerCommon {

    public void jump(String title,String path, Control field){
        try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
             // FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/gestionconference/Fxml/Sponsoring/ViewRejectedSponsors.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) field.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
