/*
package com.example.gestionconference.Controllers.RessourceControllers.LogisticController;

import com.example.gestionconference.Models.*;
import com.example.gestionconference.Services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

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
    private LogisticServices logisticServices = new LogisticServices();

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
        quantityTC.setCellValueFactory(new PropertyValueFactory<Logistic, String>("quantity"));
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

    private void editData(){
        providedLogTC.setCellFactory(TextFieldTableCell.<Logistic>forTableColumn());
        providedLogTC.setOnEditCommit(event ->{
            Logistic logistic = event.getTableView().getItems().get(event.getTablePosition().getRow());
            logistic.setProvidedLog(event.getNewValue());
            System.out.println("log Name was updated to "+ event.getNewValue() +" at row "+ (event.getTablePosition().getRow() + 1));
        });

        quantityTC.setCellFactory(TextFieldTableCell.<Logistic>forTableColumn());
        quantityTC.setOnEditCommit(event ->{
            Logistic logistic= event.getTableView().getItems().get(event.getTablePosition().getRow());
            logistic.setQuantity(Integer.parseInt(String.valueOf(event.getNewValue())));
            System.out.println(logistic.getQuantity() + "qt in was updated to "+ event.getNewValue() +" at row "+ (event.getTablePosition().getRow() + 1));

        });
    }
}*/
