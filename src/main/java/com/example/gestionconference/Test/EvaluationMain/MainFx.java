package com.example.gestionconference.Test.EvaluationMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application  {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {



        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("/com/example/gestionconference/Fxml/EvaluationFXML/Affichercom.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Confera");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    }

