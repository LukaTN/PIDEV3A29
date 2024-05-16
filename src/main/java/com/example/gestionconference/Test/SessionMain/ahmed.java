package com.example.gestionconference.Test.SessionMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ahmed extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/PresenceFXML/CardManagement.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Confera");
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("com/example/gestionconference/Styles/StyleSheet.css");
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}