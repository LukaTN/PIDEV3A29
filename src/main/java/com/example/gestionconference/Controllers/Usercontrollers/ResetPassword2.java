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

public class ResetPassword2 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField confpass;

    @FXML
    private PasswordField newpass;

    @FXML
    private Button save;

    @FXML
    private PasswordField verifcode;

    private User user;

    String code;





    @FXML
    void initialize() {
        assert confpass != null : "fx:id=\"confpass\" was not injected: check your FXML file 'resetPassword2.fxml'.";
        assert newpass != null : "fx:id=\"newpass\" was not injected: check your FXML file 'resetPassword2.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'resetPassword2.fxml'.";
        assert verifcode != null : "fx:id=\"verifcode\" was not injected: check your FXML file 'resetPassword2.fxml'.";

    }

    public void getData(User user,String code) {
        this.user = user;
        this.code = code;
    }

    @FXML
    public void save(ActionEvent event) throws SQLException, IOException {
        String verif = verifcode.getText();
        String newPassword = newpass.getText();
        String confirmPassword = confpass.getText();


        // Add your validation logic here
        if (verif.equals(this.code)) {
            // Verify the current password in the database
            if (validateInput(verif, newPassword, confirmPassword)) {
                this.user.setPassword(newPassword);
                UserService us=new UserService();
                us.update(this.user);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password Updated", "Your password has been successfully updated.");

                // Load the new FXML page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/signin.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                // Get the current stage
                Stage stage = (Stage) save.getScene().getWindow();
                // Set the new scene
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
}
