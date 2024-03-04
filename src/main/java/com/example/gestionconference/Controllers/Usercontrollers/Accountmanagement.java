package com.example.gestionconference.Controllers.Usercontrollers;

import com.example.gestionconference.Models.UserModels.User;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import java.io.File;

import javafx.event.ActionEvent;
import java.sql.*;
import com.example.gestionconference.Services.UserServices.UserService;
import javafx.stage.Stage;

public class Accountmanagement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label changepassword;



    @FXML
    private TextField nom;

    @FXML
    private TextField numtel;

    @FXML
    private TextField prenom;

    @FXML
    private PasswordField pass;

    @FXML
    private ChoiceBox<String> role;




    private User user ;

    private byte[] profilePicture;




    @FXML
    private ImageView profileImageView;

    @FXML
    void initialize() {
        assert changepassword != null : "fx:id=\"changepassword\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert numtel != null : "fx:id=\"numtel\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert prenom != null : "fx:id=\"prenom\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert role != null : "fx:id=\"role\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        role.getItems().addAll("Organizer", "Participant");

    }

    public void initData(User user){
        this.user = user;
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        numtel.setText(String.valueOf(user.getPhone()));
        try {


        Image image = new Image(new ByteArrayInputStream(user.getProfilePicture()));
        profileImageView.setImage(image);
        role.setValue(user.getRole());}
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    @FXML
    private void choosePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            try {
                // Convert the selected image to bytes
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());


                // Load the selected image and display it in the ImageView
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                profileImageView.setImage(image);
                System.out.println(selectedFile.getAbsolutePath());
                this.profilePicture = imageBytes;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) throws SQLException {
        UserService userService = new UserService();
        if(this.profilePicture==null)
        {
            this.profilePicture = user.getProfilePicture();
        }
        User user = new User(this.user.getUsername(), nom.getText(), prenom.getText(), this.user.getMail(), this.user.getPassword(), Integer.parseInt(numtel.getText()), role.getValue(), this.profilePicture);
        this.user=user;
        if (pass.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please type your password");
            alert.show();
        }
        else {
            if (!pass.getText().equals(user.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("password incorrect");
                alert.show();
            } else {
                user.setProfilePicture(this.profilePicture);
                userService.update(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Profile updated successfully");
                alert.show();
            }
        }



    }

    @FXML
    public void goToChangePassword(Event actionEvent) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/ChangePassword.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the controller of the loaded FXML file
            ChangePassword ChangepasswordController = loader.getController();

            // Pass user details to the Accountmanagement controller
            ChangepasswordController.getData(user);

            // Set the new scenech
            Stage stage = (Stage) changepassword.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException er) {
            er.printStackTrace();

        }
    }




}
