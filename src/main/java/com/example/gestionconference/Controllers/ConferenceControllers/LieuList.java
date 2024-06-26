package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.function.Add;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.gestionconference.Services.ConferenceService.LieuServices.isNumeric;

public class LieuList implements Initializable {

    LieuServices ss = new LieuServices();
    String selectedValue;


    @FXML
    private TextField searchField;

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

    private User user;

    @FXML
    private ImageView imageUser;

    @FXML
    private Text role;

    @FXML
    private Text username;

    ControllerCommon cc = new ControllerCommon();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newvalue) {
                searchLieu(newvalue);
            }
        });
        try {
            showLieu();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showLieu() throws SQLException {

        ObservableList<Lieu> lieux = ss.getAllLocationsObservable();
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


    public void newPlace(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/AddLieu.fxml"));
        Parent root = loader.load();
        // Get the controller of the loaded FXML file
        AddLieu lieuList = loader.getController();
        // Pass user details to the Accountmanagement controller
        lieuList.initData(user);
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void searchLieu(Object o) {
        try {
            ObservableList<Lieu> filteredLocations;

            if (o != null) {
                filteredLocations = ss.getAllLocationsObservable(o);
            } else {
                filteredLocations = ss.getAllLocationsObservable();
            }

            lieuTableView.setItems(filteredLocations);
        } catch (SQLException e) {
            cc.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during the search: " + e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onMouseClickedGoverment(MouseEvent event) {
        List<String> states = StatesApi.getbyCountry(TFPlaceNom.getText());
        LDGov.getItems().addAll(states);
    }

    public void initData(User user) {
        this.user = user;
        username.setText(user.getUsername());
        role.setText(user.getRole());
        try {
            Image image = new Image(new ByteArrayInputStream(user.getProfilePicture()));
            imageUser.setImage(image);
       } catch (Exception e) {
            System.out.println(e);
        }

    }
}