package com.example.gestionconference.Controllers.Usercontrollers;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.UserServices.UserService;

public class Signin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private Button loginbutton;

    @FXML
    private PasswordField password;

    @FXML
    private Label signuplink;

    @FXML
    void initialize() {
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'signin.fxml'.";
        assert loginbutton != null : "fx:id=\"loginbutton\" was not injected: check your FXML file 'signin.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'signin.fxml'.";
        assert signuplink != null : "fx:id=\"signuplink\" was not injected: check your FXML file 'signin.fxml'.";

    }
    @FXML
    void loginButtonOnAction(ActionEvent event) throws SQLException {
        String username = login.getText();
        String pass = password.getText();
        if (username.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.show();
        } else {
            UserService userService = new UserService();
            User user = userService.getByUsername(username);
            System.out.println(user);
            if (user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username incorrect");
                alert.show();
            }
            else
            {
                if (!user.getPassword().equals(pass)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("password incorrect");
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.show();
                }
            }
        }
    }

    @FXML
    public void goToSignup(Event actionEvent) {
        try {


            // Load the new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) signuplink.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
        } catch (IOException er) {
            er.printStackTrace();

        }
    }
}

