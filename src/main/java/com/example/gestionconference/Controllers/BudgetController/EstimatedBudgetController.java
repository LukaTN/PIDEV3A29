package com.example.gestionconference.Controllers.BudgetController;

import com.example.gestionconference.Models.BudgetModels.EstimatedIncomes;
import com.example.gestionconference.Services.BudgetServices.EstimatedIncomesServices;
import com.example.gestionconference.Util.PDFExporter;
import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EstimatedBudgetController implements Initializable {

    @FXML
    private TableColumn<EstimatedIncomes, Double> OIncTC;

    @FXML
    private TextField OIncTF;

    @FXML
    private TableColumn<EstimatedIncomes, Double> PInctTC;

    @FXML
    private TextField PInctTF;

    @FXML
    private TableColumn<EstimatedIncomes, Double> RIncTC;

    @FXML
    private TextField RIncTF;

    @FXML
    private Button addEstInc;

    @FXML
    private Button deleteEstInc;

    @FXML
    private Button editEstInc;

    @FXML
    private TableColumn<EstimatedIncomes, String> estIncomesTC;

    @FXML
    private TextField estIncomesTF;

    @FXML
    private TableView<EstimatedIncomes> estIncomesTV;

    @FXML
    private Label optimisticSum;

    @FXML
    private Label pessimisticSum;

    @FXML
    private Label realisticSum;



    private EstimatedIncomesServices eis = new EstimatedIncomesServices();

    @FXML
    void addEstInc(ActionEvent event) {
        String incomeSource = estIncomesTF.getText();
        String pessimisticIncomeText = PInctTF.getText().replace(",", ".");
        String realisticIncomeText = RIncTF.getText().replace(",", ".");
        String optimisticIncomeText = OIncTF.getText().replace(",", ".");

        if (!incomeSource.isEmpty() &&
                !pessimisticIncomeText.isEmpty() &&
                !realisticIncomeText.isEmpty() &&
                !optimisticIncomeText.isEmpty()) {
            double pessimisticIncome = Double.parseDouble(pessimisticIncomeText);
            double realisticIncome = Double.parseDouble(realisticIncomeText);
            double optimisticIncome = Double.parseDouble(optimisticIncomeText);

            EstimatedIncomes estimatedIncomes = new EstimatedIncomes(incomeSource, pessimisticIncome, realisticIncome, optimisticIncome);

            try {
                eis.addEstimatedIncomes(estimatedIncomes);
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    void deleteEstInc(ActionEvent event) {
        ObservableList<EstimatedIncomes> selectedIncomes = estIncomesTV.getSelectionModel().getSelectedItems();
        if (selectedIncomes != null && !selectedIncomes.isEmpty()) {
            for (EstimatedIncomes income : selectedIncomes) {
                try {
                    eis.deleteEstimatedIncomes(income);
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
    void editEstInc(ActionEvent event) throws SQLException {
        EstimatedIncomes selectedIncome = estIncomesTV.getSelectionModel().getSelectedItem();
        if (selectedIncome != null) {

            String incomeSource = estIncomesTF.getText();
            Double pessimisticIncome = Double.valueOf(PInctTF.getText());
            Double realisticIncome = Double.valueOf(RIncTF.getText());
            Double optimisticIncome = Double.valueOf(OIncTF.getText());

            selectedIncome.setEstimatedIncomesId(selectedIncome.getEstimatedIncomesId());
            selectedIncome.setIncomeSource(incomeSource);
            selectedIncome.setPessimisticIncome(pessimisticIncome);
            selectedIncome.setRealisticIncome(realisticIncome);
            selectedIncome.setOptimisticIncome(optimisticIncome);


            eis.updateEstimatedIncomes(selectedIncome);
            refreshTable();
            clearFields();
        }
    }
    @FXML
    void onSelectI(MouseEvent event) {
        EstimatedIncomes estimatedIncomes = estIncomesTV.getSelectionModel().getSelectedItem();
        if (estimatedIncomes != null){
            estIncomesTF.setText(estimatedIncomes.getIncomeSource());
            PInctTF.setText(String.valueOf(estimatedIncomes.getPessimisticIncome()));
            RIncTF.setText(String.valueOf(estimatedIncomes.getRealisticIncome()));
            OIncTF.setText(String.valueOf(estimatedIncomes.getOptimisticIncome()));



        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estIncomesTC.setCellValueFactory(new PropertyValueFactory<>("incomeSource"));
        PInctTC.setCellValueFactory(new PropertyValueFactory<>("pessimisticIncome"));
        RIncTC.setCellValueFactory(new PropertyValueFactory<>("realisticIncome"));
        OIncTC.setCellValueFactory(new PropertyValueFactory<>("optimisticIncome"));
        estIncomesTV.setEditable(true);



        try {

            refreshTable();
            clearFields();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws SQLException {
        ObservableList<EstimatedIncomes> estimatedIncomes = FXCollections.observableArrayList(eis.getAll());
        estIncomesTV.setItems(estimatedIncomes);
        double optimisticSumValue = calculateSum(OIncTC);
        double realisticSumValue = calculateSum(RIncTC);
        double pessimisticSumValue = calculateSum(PInctTC);

        optimisticSum.setText(String.valueOf(optimisticSumValue));
        realisticSum.setText(String.valueOf(realisticSumValue));
        pessimisticSum.setText(String.valueOf(pessimisticSumValue));
    }
    public void clearFields(){
        estIncomesTF.clear();
        PInctTF.clear();
        RIncTF.clear();
        OIncTF.clear();
    }



    private double calculateSum(TableColumn<EstimatedIncomes, Double> column) {
        double sum = 0.0;
        for (EstimatedIncomes item : estIncomesTV.getItems()) {
            Double value = column.getCellData(item);
            if (value != null) {
                sum += value;
            }
        }
        return sum;
    }

    @FXML
    void exportToPDF(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("table_export.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                PDFExporter.exportToPDF(estIncomesTV, file);
                showAlert(Alert.AlertType.INFORMATION, "Success", "The PDF file has been exported successfully.");
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onEstimatedExpenses(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/EstimatedExpenses.fxml"));
        Parent root = loader.load();

        // Add the AddSponsor scene to the current scene
        Scene scene = new Scene(root);
        Stage stage = (Stage) deleteEstInc.getScene().getWindow(); // Get the current stage
        stage.setScene(scene);
        stage.show();
    }
}
