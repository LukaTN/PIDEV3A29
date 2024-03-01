package com.example.gestionconference.Controllers.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewSponsor implements Initializable {

    @FXML
    private TableView<Sponsor> sponsors;

    @FXML
    private TableColumn<Sponsor, String> idCol;

    @FXML
    private TableColumn<Sponsor, String> nomCol;

    @FXML
    private TableColumn<Sponsor, String> emailCol;

    @FXML
    private TableColumn<Sponsor, Integer> numtelCol;

    @FXML
    private TableColumn<Sponsor, String> statusCol;

    @FXML
    private Icon add;

    @FXML
    private Icon update;

    @FXML
    private Icon delete;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Sponsor sponsor = null ;
    ObservableList<Sponsor> sponsorList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code here
    }

    @FXML
    void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewSponsor.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewSponsor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void refreshTable(MouseEvent event) {
        try {
            sponsorList.clear();
            query = "SELECT * FROM `sponsor`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                // Assuming you have a Status class with appropriate constructor
                sponsorList.add(new  Sponsor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("numtel"),
                        resultSet.getString("status")));
                sponsors.setItems(sponsorList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewSponsor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deleteSponsor(MouseEvent event) {
        // Implement logic to delete a sponsor
    }

    @FXML
    void rowClicked(MouseEvent event) {
        // Implement logic for handling row click event
    }
}
