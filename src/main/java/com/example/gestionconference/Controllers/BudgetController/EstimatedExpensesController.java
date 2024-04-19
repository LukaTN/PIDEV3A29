package com.example.gestionconference.Controllers.BudgetController;

import com.example.gestionconference.Models.BudgetModels.EstimatedExpenses;
import com.example.gestionconference.Services.BudgetServices.EstimatedExpensesServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EstimatedExpensesController implements Initializable {

    @FXML
    private TableColumn<EstimatedExpenses, Double> OExpTC;

    @FXML
    private TextField OExpTF;

    @FXML
    private TableColumn<EstimatedExpenses, Double> PExpTC;

    @FXML
    private TextField PExpTF;

    @FXML
    private TableColumn<EstimatedExpenses, Double>  RExpTC;

    @FXML
    private TextField RExpTF;



    @FXML
    private TableColumn<EstimatedExpenses, String>  estExpensesTC;

    @FXML
    private TextField estExpensesTF;

    @FXML
    private TableView<EstimatedExpenses> estExpensesTV;

    @FXML
    private Label optimisticSum;

    @FXML
    private Label pessimisticSum;

    @FXML
    private Label realisticSum;

    private EstimatedExpensesServices ees = new EstimatedExpensesServices();
    @FXML
    void addEstExp(ActionEvent event) {
        String expensesWay = estExpensesTF.getText();
        String pessimisticExpensesText = PExpTF.getText().replace(",", ".");
        String realisticExpensesText = RExpTF.getText().replace(",", ".");
        String optimisticExpensesText = OExpTF.getText().replace(",", ".");

        if (!expensesWay.isEmpty() &&
                !pessimisticExpensesText.isEmpty() &&
                !realisticExpensesText.isEmpty() &&
                !optimisticExpensesText.isEmpty()) {
            double pessimisticExpenses = Double.parseDouble(pessimisticExpensesText);
            double realisticExpenses = Double.parseDouble(realisticExpensesText);
            double optimisticExpenses = Double.parseDouble(optimisticExpensesText);

            EstimatedExpenses estimatedExpenses = new EstimatedExpenses(expensesWay, pessimisticExpenses, realisticExpenses, optimisticExpenses);

            try {
                ees.addEstimatedExpenses(estimatedExpenses);
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    void deleteEstExp(ActionEvent event) {
        ObservableList<EstimatedExpenses> selectedExpenses = (ObservableList<EstimatedExpenses>) estExpensesTV.getSelectionModel().getSelectedItems();
        if (selectedExpenses != null && !selectedExpenses.isEmpty()) {
            for (EstimatedExpenses expense : selectedExpenses) {
                try {
                    ees.deleteEstimatedExpenses(expense);
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
            try {
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
    @FXML
    void editEstExp(ActionEvent event) throws SQLException {
        EstimatedExpenses selectedExpenses = estExpensesTV.getSelectionModel().getSelectedItem();
        if (selectedExpenses != null) {

            String expensesWay = estExpensesTF.getText();
            Double pessimisticExpenses = Double.valueOf(PExpTF.getText());
            Double realisticExpenses = Double.valueOf(RExpTF.getText());
            Double optimisticExpenses = Double.valueOf(OExpTF.getText());

            selectedExpenses.setEstimatedExpensesId(selectedExpenses.getEstimatedExpensesId());
            selectedExpenses.setExpensesWay(expensesWay);
            selectedExpenses.setPessimisticExpenses(pessimisticExpenses);
            selectedExpenses.setRealisticExpenses(realisticExpenses);
            selectedExpenses.setOptimisticExpenses(optimisticExpenses);


            ees.updateEstimatedExpenses(selectedExpenses);
            refreshTable();
            clearFields();
        }
    }
    @FXML
    void onSelectE(MouseEvent event) {
        EstimatedExpenses estimatedExpensess = estExpensesTV.getSelectionModel().getSelectedItem();
        if (estimatedExpensess != null){
            estExpensesTF.setText(estimatedExpensess.getExpensesWay());
            PExpTF.setText(String.valueOf(estimatedExpensess.getPessimisticExpenses()));
            RExpTF.setText(String.valueOf(estimatedExpensess.getRealisticExpenses()));
            OExpTF.setText(String.valueOf(estimatedExpensess.getOptimisticExpenses()));



        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estExpensesTC.setCellValueFactory(new PropertyValueFactory<>("expensesWay"));
        PExpTC.setCellValueFactory(new PropertyValueFactory<>("pessimisticExpenses"));
        RExpTC.setCellValueFactory(new PropertyValueFactory<>("realisticExpenses"));
        OExpTC.setCellValueFactory(new PropertyValueFactory<>("optimisticExpenses"));
        estExpensesTV.setEditable(true);

        try {
            refreshTable();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void refreshTable() throws SQLException {
        ObservableList<EstimatedExpenses> estimatedExpenses = FXCollections.observableArrayList(ees.getAll());
        estExpensesTV.setItems(estimatedExpenses);
        double optimisticSumValue = calculateSum(OExpTC);
        double realisticSumValue = calculateSum(RExpTC);
        double pessimisticSumValue = calculateSum(PExpTC);

        optimisticSum.setText(String.valueOf(optimisticSumValue));
        realisticSum.setText(String.valueOf(realisticSumValue));
        pessimisticSum.setText(String.valueOf(pessimisticSumValue));
    }
    public void clearFields(){
        estExpensesTF.clear();
        PExpTF.clear();
        RExpTF.clear();
        OExpTF.clear();
    }

    private double calculateSum(TableColumn<EstimatedExpenses, Double> column) {
        double sum = 0.0;
        for (EstimatedExpenses item : estExpensesTV.getItems()) {
            Double value = column.getCellData(item);
            if (value != null) {
                sum += value;
            }
        }
        return sum;
    }


    public void onRatio(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/EstimationV.fxml"));
        Parent root = loader.load();

        // Add the AddSponsor scene to the current scene
        Scene scene = new Scene(root);
        Stage stage = (Stage) OExpTF.getScene().getWindow(); // Get the current stage
        stage.setScene(scene);
        stage.show();
    }
}
