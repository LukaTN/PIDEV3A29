package com.example.gestionconference.Test.SessionMain;
import com.example.gestionconference.Models.SessionModels.DataModel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test extends Application {

    private TableView<DataModel> tableView;

    // Database connection parameters
    private final String dbUrl = "jdbc:mysql://localhost:3306/testback";
    private final String dbUsername = "root";
    private final String dbPassword = "";

    final String URL = "jdbc:mysql://localhost:3306/confera";
    final String USER = "root";
    final String password = "";

    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();
        TableColumn<DataModel, String> nameColumn = new TableColumn<>("uid");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Add more columns as needed

        tableView.getColumns().add(nameColumn);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Real-Time Table View Demo");
        primaryStage.show();

        // Load data initially
        loadDataFromDatabase();

        // Start a thread to periodically update the table view
        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Adjust the update interval as needed
                    Platform.runLater(this::loadDataFromDatabase);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void loadDataFromDatabase() {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM testiot");
            tableView.getItems().clear();
            while (rs.next()) {
                DataModel data = new DataModel(rs.getString("uid"));
                tableView.getItems().add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
