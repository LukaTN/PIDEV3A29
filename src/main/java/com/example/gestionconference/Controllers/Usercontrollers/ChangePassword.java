package com.example.gestionconference.Controllers.Usercontrollers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.UserServices.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField confpass;

    @FXML
    private PasswordField current;

    @FXML
    private PasswordField newpass;

    private User user;

    @FXML
    private Button save;

    @FXML
    void initialize() {
        assert confpass != null : "fx:id=\"confpass\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert current != null : "fx:id=\"current\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert newpass != null : "fx:id=\"newpass\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'ChangePassword.fxml'.";

    }

    @FXML
    public void save(ActionEvent event) throws SQLException, IOException {
        String currentPassword = current.getText();
        String newPassword = newpass.getText();
        String confirmPassword = confpass.getText();

        // Add your validation logic here
        if (this.user.getPassword().equals(currentPassword)) {
            // Verify the current password in the database
            if (validateInput(currentPassword, newPassword, confirmPassword)) {
                this.user.setPassword(newPassword);
                UserService us=new UserService();
                us.update(this.user);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password Updated", "Your password has been successfully updated.");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/Accountmanagement.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Get the controller of the loaded FXML file
                Accountmanagement accountManagementController = loader.getController();

                // Pass user details to the Accountmanagement controller
                accountManagementController.initData(user);

                // Set the new scene
                Stage stage = (Stage) save.getScene().getWindow();
                stage.setScene(scene);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Incorrect Current Password", "Please enter the correct current password.");

        }
    }


    private boolean validateInput(String currentPassword, String newPassword, String confirmPassword) {
        if (newPassword.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Weak Password", "Password must be at least 8 characters long.");
            return false;
        } else if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password Mismatch", "Password and confirm password do not match.");
            return false;
        }

        return true;
    }


    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void getData(User user) {
        this.user = user;
    }




}



