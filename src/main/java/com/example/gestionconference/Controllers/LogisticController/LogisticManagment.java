package com.example.gestionconference.Controllers.LogisticController;



import com.example.gestionconference.Controllers.ConferenceControllers.ControllerCommon;
import com.example.gestionconference.Models.LogisticModels.Logistic;
import com.example.gestionconference.Models.LogisticModels.LogisticIncome;
import com.example.gestionconference.Services.LogisticServices.LogisticServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;



public class LogisticManagment {

    @FXML
    private Button deleteData;

    @FXML
    private TableView<Logistic> logTV;

    @FXML
    private TableColumn<Logistic, String> providedLogTC;

    @FXML
    private TextField providedLogTF;

    @FXML
    private TableColumn<Logistic, String> quantityTC;

    @FXML
    private TextField quantityTF;
    @FXML
    private TableColumn<Logistic, Logistic> NextColumn;
    private LogisticServices logisticServices = new LogisticServices();

    ControllerCommon cc = new ControllerCommon();




    @FXML
    void btnSubmit(ActionEvent event) {
        String providedLog = providedLogTF.getText();
        int quantity = Integer.parseInt(quantityTF.getText());
        Logistic logistic = new Logistic(providedLog, quantity);
        try {
            logisticServices.addLogistic(logistic);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteData(ActionEvent event) throws SQLException {
        ObservableList<Logistic> selectedItems = logTV.getSelectionModel().getSelectedItems();
        if (selectedItems != null) {
            List<Logistic> logistics = Arrays.asList(selectedItems.toArray(new Logistic[0]));
            logistics.forEach(logistic -> {
                try {
                    logisticServices.deleteLogistic(logistic);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            refreshTable();
        }
    }

    @FXML
    public void initialize() {
        providedLogTC.setCellValueFactory(new PropertyValueFactory<>("providedLog"));
        quantityTC.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        logTV.setEditable(true);

        try {

            refreshTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void refreshTable() throws SQLException {
        ObservableList<Logistic> logistics = FXCollections.observableArrayList(logisticServices.getAll());
        logTV.setItems(logistics);
    }
    @FXML
    private void editData() {
        Logistic logistic = logTV.getSelectionModel().getSelectedItem();
        if (logistic != null) {
            try {
                logistic.setProvidedLog(providedLogTC.getCellData(logTV.getSelectionModel().getSelectedIndex()));
                logistic.setQuantity(Integer.parseInt(quantityTC.getCellData(logTV.getSelectionModel().getSelectedIndex())));

                logisticServices.updateLogistic(logistic);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            cc.showAlert(Alert.AlertType.WARNING, "Warning", "Please select a place to update");
        }


    }

    public void toIncomes(ActionEvent event) throws IOException {
        Logistic logistic = logTV.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/LogisticIncome.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            LogisticIncomeController logisticIncome = loader.getController();
            logisticIncome.setData(logistic);
            Stage stage = (Stage) logTV.getScene().getWindow();
            stage.setScene(scene);
    }

    /*@FXML
    void detailsMove(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/LogisticIncome.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }*/
    @FXML
    void onBudget(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Budgeting.fxml"));
        Parent root = loader.load();

        // Add the AddSponsor scene to the current scene
        Scene scene = new Scene(root);
        Stage stage = (Stage) deleteData.getScene().getWindow(); // Get the current stage
        stage.setScene(scene);
        stage.show();
    }


}
