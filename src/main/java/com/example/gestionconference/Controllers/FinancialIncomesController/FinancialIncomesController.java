package com.example.gestionconference.Controllers.FinancialIncomesController;

import com.example.gestionconference.Models.FinancialIncomesModels.FinancialIncomes;

import com.example.gestionconference.Services.FinancialIncomesServices.FinancialIncomesServices;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;



public class FinancialIncomesController implements Initializable {

    @FXML
    private AnchorPane AddView;

    @FXML
    private TextField CashINTF;

    @FXML
    private TextField SponsorName;

    @FXML
    private AnchorPane UpdateView;

    @FXML
    private TableColumn<FinancialIncomes, Integer> cashInTC;

    @FXML
    private TextField cashInTF;

    @FXML
    private Button deleteData;

    @FXML
    private TableView<FinancialIncomes> financeIncomesTV;

    @FXML
    private Button onSubmit;

    @FXML
    private Button onUpdateB;

    @FXML
    private Label proofLabel;

    @FXML
    private TableColumn<FinancialIncomes, Byte> proofTC;

    @FXML
    private TableColumn<FinancialIncomes, String> sponsorNameTC;

    @FXML
    private TextField sponsorNameTF;

    @FXML
    private Button uploadProof;

    boolean editView=false;

    private FinancialIncomesServices fs = new FinancialIncomesServices();


    /*ObservableList<FinancialIncomes> initialData(){
        FinancialIncomes p1 = new FinancialIncomes("Sample",200," link");
        FinancialIncomes p2 = new FinancialIncomes("Test",330," link");
        return FXCollections.observableArrayList(p1, p2);
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        sponsorNameTC.setCellValueFactory(new PropertyValueFactory<>("sponsorName"));
        cashInTC.setCellValueFactory(new PropertyValueFactory<>("cashIn"));
        proofTC.setCellValueFactory(new PropertyValueFactory<>("proof"));
        UpdateView.setVisible(false);

        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML

    private void onSubmit(ActionEvent event) {
        String sponsorName = sponsorNameTF.getText();
        int cashIn = Integer.parseInt(cashInTF.getText());
        byte[] Proof = new byte[0];

        try {
            Proof = Files.readAllBytes(Paths.get(proofLabel.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FinancialIncomes financialIncomes = new FinancialIncomes(sponsorName,cashIn, Proof);
        try {
            fs.addFinancialIncomes(financialIncomes);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void uploadProof(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une preuve");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Set the absolute path of the selected file
            proofLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void onUpdate(ActionEvent event) throws SQLException {
        FinancialIncomes financialIncomes = financeIncomesTV.getSelectionModel().getSelectedItem();
        if (financialIncomes != null) {
            String sponsorname = sponsorNameTF.getText();
            int cashIn = Integer.parseInt(cashInTF.getText());
            byte[] proof = new byte[0];

            try {
                proof = Files.readAllBytes(Paths.get(proofLabel.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            financialIncomes.setFinancialIncomesId(financialIncomes.getFinancialIncomesId());
            financialIncomes.setSponsorName(sponsorname);
            financialIncomes.setCashIn(cashIn);
            financialIncomes.setProof(proof);

            fs.updateFinancialIncomes(financialIncomes);
            refreshTable();
        }

    }
    @FXML
    void onSelect(MouseEvent event){
        FinancialIncomes financialIncomes = financeIncomesTV.getSelectionModel().getSelectedItem();
        if (financialIncomes != null){
            sponsorNameTF.setText(financialIncomes.getSponsorName());
            cashInTF.setText(String.valueOf(financialIncomes.getCashIn()));
            //CashINTF.setText(financialIncomes.getCashIn()+"");

        }

    }

    private void refreshTable() throws SQLException {
        ObservableList<FinancialIncomes> financialIncomes = FXCollections.observableArrayList(fs.getAll());
        financeIncomesTV.setItems(financialIncomes);
    }





    @FXML
    private void deleteData(ActionEvent event){
        TableView.TableViewSelectionModel<FinancialIncomes> selectionModel = financeIncomesTV.getSelectionModel();
        if(selectionModel.isEmpty()){
            System.out.println("You need select a data before deleting.");
        }

        ObservableList<Integer> list = selectionModel.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        Arrays.sort(selectedIndices);

        for(int i = selectedIndices.length - 1; i >= 0; i--){
            selectionModel.clearSelection(selectedIndices[i].intValue());
            financeIncomesTV.getItems().remove(selectedIndices[i].intValue());
        }
    }


    public void onLogistics(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/LogisticManagment.fxml"));
        Parent root = loader.load();

        // Add the AddSponsor scene to the current scene
        Scene scene = new Scene(root);
        Stage stage = (Stage) SponsorName.getScene().getWindow(); // Get the current stage
        stage.setScene(scene);
        stage.show();
    }
}
