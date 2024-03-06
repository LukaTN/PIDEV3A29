package com.example.gestionconference.Controllers.LogisticController;


import com.example.gestionconference.Models.LogisticModels.Logistic;
import com.example.gestionconference.Models.LogisticModels.LogisticIncome;
import com.example.gestionconference.Services.LogisticServices.LogisticIncomeServices;

import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LogisticIncomeController implements Initializable {

    @FXML
    private Button deleteLog;

    @FXML
    private TableView<LogisticIncome> logIncomesTV;

    @FXML
    private TableColumn<LogisticIncome, File> logProofTC;

    @FXML
    private TableColumn<LogisticIncome, String> logProviderTC;

    @FXML
    private TextField logProviderTF;

    @FXML
    private TableColumn<LogisticIncome, Integer> logQtyTC;

    @FXML
    private TextField logQtyTF;

    @FXML
    private Label logProofLabel;
    private LogisticIncomeServices li = new LogisticIncomeServices();

    private Logistic logistic; // Define logistic variable


    @FXML
    void deleteLog(ActionEvent event) throws SQLException {
        ObservableList<LogisticIncome> selectedItems = logIncomesTV.getSelectionModel().getSelectedItems();
        if (selectedItems != null) {
            List<LogisticIncome> logisticIncomes = Arrays.asList(selectedItems.toArray(new LogisticIncome[0]));
            logisticIncomes.forEach(logisticIncome -> {
                try {
                    li.deleteLogistic(logisticIncome);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            refreshTable();
        }
    }

    @FXML
    private void onAjoutLog(ActionEvent event) {
        String logSponsorName = logProviderTF.getText();
        int logIncomeQty = Integer.parseInt(logQtyTF.getText());
        byte[] logProof = new byte[0];
        try {
            logProof = Files.readAllBytes(Paths.get(logProofLabel.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        LogisticIncome logisticIncome = new LogisticIncome(logSponsorName, logIncomeQty, logProof);
        try {
            li.addLogisticIncome(logisticIncome);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une preuve");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Set the absolute path of the selected file
            logProofLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logProviderTC.setCellValueFactory(new PropertyValueFactory<>("logSponsorName"));
        logQtyTC.setCellValueFactory(new PropertyValueFactory<>("logIncomeQty"));
        logProofTC.setCellValueFactory(new PropertyValueFactory<>("logProof"));

        logProviderTC.setCellFactory(TextFieldTableCell.forTableColumn());
        logProviderTC.setOnEditCommit(event -> {
            event.getRowValue().setLogSponsorName(event.getNewValue());
            try {
                refreshTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        logQtyTC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        logQtyTC.setOnEditCommit(event -> {
            event.getRowValue().setLogIncomeQty(event.getNewValue());
            try {
                refreshTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        logIncomesTV.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && logIncomesTV.getSelectionModel().getSelectedItem() != null) {
                TablePosition pos = logIncomesTV.getSelectionModel().getSelectedCells().get(0);
                TableColumn col = pos.getTableColumn();

                if (col.isEditable() && pos.getRow() < logIncomesTV.getItems().size()) {
                    logIncomesTV.edit(pos.getRow(), col);
                }
            }
        });

        try {
            System.out.println(logistic.getLogisticId());
            li.getLogisticBySourceId(logistic.getLogisticId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ObservableList<LogisticIncome> logisticIncomes = li.getLogisticBySourceId(logistic.getLogisticId());
            for (LogisticIncome e : logisticIncomes ){
                logIncomesTV.getItems().add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logProviderTC.setEditable(true);
        logQtyTC.setEditable(true);
    }

    void refreshTable() throws SQLException {
        ObservableList<LogisticIncome> logisticIncomes = FXCollections.observableArrayList(li.getAll());
        logIncomesTV.setItems(logisticIncomes);
    }

    @FXML
    void onBack(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/LogisticManagment.fxml"));
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
    }
    public void setData(Logistic logistic) {
        System.out.println(logistic);
        this.logistic = logistic; // Set logistic data
    }

}