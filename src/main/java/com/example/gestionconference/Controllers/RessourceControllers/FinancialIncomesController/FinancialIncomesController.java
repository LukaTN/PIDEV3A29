package com.example.gestionconference.Controllers.RessourceControllers.FinancialIncomesController;

import com.example.gestionconference.Models.FinancialIncomesModels.FinancialIncomes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;



public class FinancialIncomesController implements Initializable {

    @FXML
    private TableColumn<FinancialIncomes, String> cashInTC;

    @FXML
    private TableView<FinancialIncomes> financeIncomesTV;

    @FXML
    private TableColumn<FinancialIncomes, String> proofTC;

    @FXML
    private TableColumn<FinancialIncomes, String> sponsorNameTC;

    @FXML
    private TextField sponsorNameTF;

    @FXML
    private TextField cashInTF;

    @FXML
    private TextField proofTF;
    /*private FinancialIncomesServices fs = new FinancialIncomesServices();*/


    ObservableList<FinancialIncomes> initialData(){
        FinancialIncomes p1 = new FinancialIncomes("Sample",200," link");
        FinancialIncomes p2 = new FinancialIncomes("Test",330," link");
        return FXCollections.observableArrayList(p1, p2);
    }


    @FXML
    void onSubmit(ActionEvent event) {
        if(!sponsorNameTF.getText().isEmpty() || !cashInTF.getText().isEmpty() || !proofTF.getText().isEmpty()) {
            FinancialIncomes newData = new FinancialIncomes(sponsorNameTF.getText(), Integer.parseInt(cashInTF.getText()), proofTF.getText());
            financeIncomesTV.getItems().add(newData);
            sponsorNameTF.clear();
            cashInTF.clear();
            proofTF.clear();
        }else{
            System.out.println("Fields should not be empty.");
        }
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsorNameTC.setCellValueFactory(new PropertyValueFactory<>("sponsorName"));
        cashInTC.setCellValueFactory(new PropertyValueFactory<>("cashIn"));
        proofTC.setCellValueFactory(new PropertyValueFactory<>("proof"));
    }

    private void editData(){
        sponsorNameTC.setCellFactory(TextFieldTableCell.<FinancialIncomes>forTableColumn());
        sponsorNameTC.setOnEditCommit(event ->{
            FinancialIncomes financialIncomes = event.getTableView().getItems().get(event.getTablePosition().getRow());
            financialIncomes.setSponsorName(event.getNewValue());
            System.out.println("sponsor Name was updated to "+ event.getNewValue() +" at row "+ (event.getTablePosition().getRow() + 1));
        });

        cashInTC.setCellFactory(TextFieldTableCell.<FinancialIncomes>forTableColumn());
        cashInTC.setOnEditCommit(event ->{
           FinancialIncomes financialIncomes= event.getTableView().getItems().get(event.getTablePosition().getRow());
            financialIncomes.setCashIn(Integer.parseInt(event.getNewValue()));
            System.out.println(financialIncomes.getSponsorName() + "'s cash in was updated to "+ event.getNewValue() +" at row "+ (event.getTablePosition().getRow() + 1));
        });

        proofTC.setCellFactory(TextFieldTableCell.<FinancialIncomes>forTableColumn());
        proofTC.setOnEditCommit(event ->{
            FinancialIncomes financialIncomes = event.getTableView().getItems().get(event.getTablePosition().getRow());
            financialIncomes.setProof(event.getNewValue());
            System.out.println("proof was updated");
        });
    }

}
