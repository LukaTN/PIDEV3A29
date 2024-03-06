package com.example.gestionconference.Controllers.Usercontrollers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetPassword1 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button getCode;

    @FXML
    private TextField username;

    private User user;

    @FXML
    void getCode(ActionEvent event) throws SQLException, IOException {
        UserService us=new UserService();
        this.user=us.getByUsername(username.getText());
        if(user!=null) {
            System.out.println(this.user);

            user = this.user;
            String redeemedCode = generateCode();

            // Specify the recipient's email address
            String recipientEmail = user.getMail();
            // Email details
            String subject = "Redeemed Code";
            String messageBody = "Your redeemed code is: " + redeemedCode;
            // Send the email
            EmailSender.sendEmail(recipientEmail, subject, messageBody);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("A verification code is sent to " + user.getMail());
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/resetPassword2.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ResetPassword2 reset2Controller = loader.getController();
            reset2Controller.getData(user, redeemedCode);

            // Set the new scene
            Stage stage = (Stage) getCode.getScene().getWindow();
            stage.setScene(scene);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User doesn't exist");
            alert.show();
        }




    }

    @FXML
    void initialize() {
        assert getCode != null : "fx:id=\"save\" was not injected: check your FXML file 'resetPassword1.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'resetPassword1.fxml'.";

    }

    public static String generateCode() {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        int codeLength = 6;

        // Create a StringBuilder to build the alphanumeric code
        StringBuilder alphanumericCode = new StringBuilder();

        // Create an instance of Random
        Random random = new Random();

        // Generate random characters for the code
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            alphanumericCode.append(randomChar);
        }

        // Convert the StringBuilder to a String and return the alphanumeric code
        return alphanumericCode.toString();
    }



}

