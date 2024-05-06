package com.example.gestionconference.Test.SessionMain;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Tab 1");
        tab1.setContent(new StackPane());
        Tab tab2 = new Tab("Tab 2");
        tab2.setContent(new StackPane());
        Tab tab3 = new Tab("Tab 3");
        tab3.setContent(new StackPane());

        tabPane.getTabs().addAll(tab1, tab2, tab3);

        tabPane.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10px;"); // Set the background color and radius

        for (Tab tab : tabPane.getTabs()) {
            tab.setStyle("-fx-background-color: #d0d0d0; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-style: solid; -fx-padding: 5 10;");
        }

        tabPane.getTabs().get(0).setStyle(tabPane.getTabs().get(0).getStyle() + "-fx-background-radius: 20px 0 0 0;"); // Apply border radius to the first tab

        Scene scene = new Scene(tabPane, 400, 300);
        scene.getStylesheets().add("/com/example/gestionconference/Styles/StyleSheet.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Styled TabPane Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
