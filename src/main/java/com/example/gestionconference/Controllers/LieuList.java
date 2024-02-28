package com.example.gestionconference.Controllers;

import com.example.gestionconference.Models.Lieu;
import com.example.gestionconference.Services.LieuServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LieuList implements Initializable {

    LieuServices ss = new LieuServices();
    String selectedValue;

    @FXML
    private TableView<Lieu> lieuTableView;

    @FXML
    private TableColumn<Lieu, Integer> capacityCol;

    @FXML
    private TableColumn<Lieu, String> govermentCol;

    @FXML
    private TableColumn<Lieu, String> placeCol;

    @FXML
    private TableColumn<Lieu, String> zoneCol;
    @FXML
    private ComboBox<String> LDGov;

    @FXML
    private TextField TFCapacity;

    @FXML
    private TextField TFPlaceNom;

    @FXML
    private TextField TFZone;

    ControllerCommon cc = new ControllerCommon();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LDGov.getItems().addAll("Ariana","Gafsa","Kef","Kasserine","Beja","Jendouba","Medenine","Monastir","Nabeul","Sfax","SidiBouzid","Siliana","Sousse","Tataouine","Tozeur","Tunis","Zaghouan");
        try {
            showLieu();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showLieu() throws SQLException {

        ObservableList<Lieu> lieux = ss.getAllLocationsObservable();
        System.out.println(lieux);
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        placeCol.setCellValueFactory(new PropertyValueFactory<>("label"));
        zoneCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        govermentCol.setCellValueFactory(new PropertyValueFactory<>("zone"));
        lieuTableView.setItems(lieux);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        Lieu selectedLieu = lieuTableView.getSelectionModel().getSelectedItem();

        if (selectedLieu != null) {
            LDGov.setValue(selectedLieu.getZone());
            TFCapacity.setText(String.valueOf(selectedLieu.getCapacity()));
            TFPlaceNom.setText(selectedLieu.getLabel());
            TFZone.setText(selectedLieu.getPlace());
        }
    }

    @FXML
    void updateLieu(ActionEvent event) {
        Lieu lieu = lieuTableView.getSelectionModel().getSelectedItem();
        if (lieu != null) {
            try {
                String newLabel = TFPlaceNom.getText();
                String newZone = TFZone.getText();
                String newCapacityText = TFCapacity.getText();
                String newGov = LDGov.getValue();

                if (newLabel.isEmpty() || newZone.isEmpty() || newCapacityText.isEmpty()) {
                    cc.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
                    return;
                }

                int newCapacity;
                try {
                    newCapacity = Integer.parseInt(newCapacityText);
                    if (newCapacity <= 0) {
                        cc.showAlert(Alert.AlertType.ERROR, "Error", "Capacity must be a positive integer");
                        return;
                    }
                } catch (NumberFormatException e) {
                    cc.showAlert(Alert.AlertType.ERROR, "Error", "Invalid capacity value");
                    return;
                }
                lieu.setId(lieu.getId());
                lieu.setZone(newGov);
                lieu.setCapacity(newCapacity);
                lieu.setLabel(newLabel);
                lieu.setPlace(newZone);
                ss.updateLieu(lieu);
                showLieu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            cc.showAlert(Alert.AlertType.WARNING, "Warning", "Please select a place to update");
        }

    }

    @FXML
    void deleteLieu(ActionEvent event) {
        Lieu lieu = lieuTableView.getSelectionModel().getSelectedItem();
        if (lieu != null) {
            try {
                cc.showAlert(Alert.AlertType.CONFIRMATION, "Delete Place", "Are you sure you want to delete this place?");
                ss.deleteLieu(lieu.getId());
                showLieu();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    public void newPlace(ActionEvent actionEvent) {

        cc.jump("Add Place","/com/example/gestionconference/Fxml/AddLieu.fxml",TFZone);
    }

}
