package com.example.gestionconference.Controllers.Usercontrollers;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.gestionconference.Controllers.ConferenceControllers.AddConference;
import com.example.gestionconference.Controllers.ConferenceControllers.ControllerCommon;
import com.example.gestionconference.Models.UserModels.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    private Label fermer;

    @FXML
    private Label forgotpass;

    ControllerCommon cc = new ControllerCommon();



    public void close(Event event)
    {
        Stage stage = (Stage) fermer.getScene().getWindow();
        stage.close();
    }


    @FXML
    void initialize() {
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'signin.fxml'.";
        assert loginbutton != null : "fx:id=\"loginbutton\" was not injected: check your FXML file 'signin.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'signin.fxml'.";
        assert signuplink != null : "fx:id=\"signuplink\" was not injected: check your FXML file 'signin.fxml'.";
        assert fermer != null : "fx:id=\"fermer\" was not injected: check your FXML file 'signin.fxml'.";
        assert forgotpass != null : "fx:id=\"forgotpass\" was not injected: check your FXML file 'signin.fxml'.";
        login.setText("melek");
        password.setText("password");

    }
    @FXML
    void loginButtonOnAction(ActionEvent event) throws SQLException, IOException {
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
                    if( user.getRole().equals("Organizer")){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/Conference.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);

                            // Get the controller of the loaded FXML file
                            AddConference addconference = loader.getController();

                            // Pass user details to the Accountmanagement controller
                            addconference.initData(user);

                            // Set the new scene
                            Stage stage = (Stage) loginbutton.getScene().getWindow();
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/EvaluationFXML/Ajoutercom.fxml"));
                        Parent root = loader.load();

                        // Add the AddSponsor scene to the current scene
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) password.getScene().getWindow(); // Get the current stage
                        stage.setScene(scene);
                        stage.show();
                    }


                }
            }
        }
    }

    @FXML
    public void goToSignup(Event actionEvent) {
        try {


            // Load the new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/signup.fxml"));
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

    public void goToReset(Event actionEvent) {
        try {


            // Load the new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/resetPassword1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) forgotpass.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
        } catch (IOException er) {
            er.printStackTrace();

        }
    }

}

