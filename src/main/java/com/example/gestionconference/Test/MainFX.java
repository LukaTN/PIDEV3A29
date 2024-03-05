package com.example.gestionconference.Test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //generate
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/UserFXML/signin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Confera");
        primaryStage.setScene(scene);
       // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }
}
