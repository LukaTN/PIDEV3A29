package com.example.gestionconference.Controllers.Usercontrollers;

import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.File;

public class Accountmanagement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private TextField nom;

    @FXML
    private TextField numtel;

    @FXML
    private TextField prenom;

    @FXML
    private ImageView profileImageView;

    @FXML
    void initialize() {
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert numtel != null : "fx:id=\"numtel\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert prenom != null : "fx:id=\"prenom\" was not injected: check your FXML file 'accountmanagement.fxml'.";
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'accountmanagement.fxml'.";

    }

     @FXML
    private File choosePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image and display it in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }





}
