package com.example.gestionconference.Controllers.BudgetController;

import com.example.gestionconference.Models.BudgetModels.Expenses;
import com.example.gestionconference.Models.BudgetModels.Incomes;
import com.example.gestionconference.Services.BudgetServices.ExpensesServices;
import com.example.gestionconference.Services.BudgetServices.IncomesServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BudgetingController implements Initializable {

    @FXML
    private Button addData;

    @FXML
    private Button deleteData;

    @FXML
    private TableColumn<Expenses, Integer> expAmmountTC;

    @FXML
    private TextField expAmmountTF;

    @FXML
    private TableView<Expenses> expanesesTV;

    @FXML
    private TableColumn<Expenses, String> onWhatTC;

    @FXML
    private TextField onWhatTF;

    @FXML
    private TableColumn<Incomes, String> fromWhatTC;

    @FXML
    private TextField fromWhatTF;

    @FXML
    private TableColumn<Incomes, Integer> incAmmountTC;

    @FXML
    private TextField incAmmountTF;

    @FXML
    private TableView<Incomes> incomesTV;

    @FXML
    private Label totalExpensesSum;

    @FXML
    private Label totalIncomesSum;

    @FXML
    private PieChart ratioChart;

    private ExpensesServices es = new ExpensesServices();
    private IncomesServices is = new IncomesServices();

    @FXML
    void addData(ActionEvent event) {
        String onWhat = onWhatTF.getText();
        String expAmountText = expAmmountTF.getText();
        if (!expAmountText.isEmpty()) {
            int expAmount = Integer.parseInt(expAmountText);
            Expenses expenses = new Expenses(onWhat, expAmount);
            try {
                es.addExpenses(expenses);
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String fromWhat = fromWhatTF.getText();
        String incAmountText = incAmmountTF.getText();
        if (!incAmountText.isEmpty()) {
            int incAmount = Integer.parseInt(incAmountText);
            Incomes incomes = new Incomes(fromWhat, incAmount);
            try {
                is.addIncomes(incomes);
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void deleteData(ActionEvent event) throws SQLException {
        ObservableList<Expenses> selectedExpenses = expanesesTV.getSelectionModel().getSelectedItems();
        if (selectedExpenses != null && !selectedExpenses.isEmpty()) {
            for (Expenses expense : selectedExpenses) {
                try {
                    es.deleteExpenses(expense);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        ObservableList<Incomes> selectedIncomes = incomesTV.getSelectionModel().getSelectedItems();
        if (selectedIncomes != null && !selectedIncomes.isEmpty()) {
            for (Incomes income : selectedIncomes) {
                try {
                    is.deleteIncomes(income);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        refreshTable();
        clearFields();
    }

    @FXML
    void editData(ActionEvent event) {
        if (expanesesTV.getSelectionModel().getSelectedItem() != null) {
            Expenses selectedExpense = expanesesTV.getSelectionModel().getSelectedItem();
            String updatedOnWhat = onWhatTF.getText();
            String updatedExpAmountText = expAmmountTF.getText();
            if (!updatedOnWhat.isEmpty() && !updatedExpAmountText.isEmpty()) {
                int updatedExpAmount = Integer.parseInt(updatedExpAmountText);
                selectedExpense.setOnWhat(updatedOnWhat);
                selectedExpense.setExpAmmount(updatedExpAmount);
                try {
                    es.updateExpenses(selectedExpense);
                    refreshTable();
                    clearFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if (incomesTV.getSelectionModel().getSelectedItem() != null) {
            Incomes selectedIncome = incomesTV.getSelectionModel().getSelectedItem();
            String updatedFromWhat = fromWhatTF.getText();
            String updatedIncAmountText = incAmmountTF.getText();
            if (!updatedFromWhat.isEmpty() && !updatedIncAmountText.isEmpty()) {
                int updatedIncAmount = Integer.parseInt(updatedIncAmountText);
                selectedIncome.setFromWhat(updatedFromWhat);
                selectedIncome.setIncAmmount(updatedIncAmount);
                try {
                    is.updateIncomes(selectedIncome);
                    refreshTable();
                    clearFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void refreshTable() throws SQLException {
        ObservableList<Expenses> expenses = FXCollections.observableArrayList(es.getAll());
        expanesesTV.setItems(expenses);
        ObservableList<Incomes> incomes = FXCollections.observableArrayList(is.getAll());
        incomesTV.setItems(incomes);

        int totalExpenses = calculateTotalExpenses(expenses);
        totalExpensesSum.setText(Integer.toString(totalExpenses));

        int totalIncomes = calculateTotalIncomes(incomes);
        totalIncomesSum.setText(Integer.toString(totalIncomes));

        double expensesRatio = totalExpenses / (double)(totalExpenses + totalIncomes);
        double incomesRatio = totalIncomes / (double)(totalExpenses + totalIncomes);

        // Créer une liste de segments de PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Expenses", expensesRatio),
                new PieChart.Data("Total Incomes", incomesRatio)
        );

        // Assigner les données au PieChart
        ratioChart.setData(pieChartData);
    }



    private int calculateTotalExpenses(ObservableList<Expenses> expenses) {
        int totalExpenses = 0;
        for (Expenses expense : expenses) {
            totalExpenses += expense.getExpAmmount();
        }
        return totalExpenses;
    }

    private int calculateTotalIncomes(ObservableList<Incomes> incomes) {
        int totalIncomes = 0;
        for (Incomes income : incomes) {
            totalIncomes += income.getIncAmmount();
        }
        return totalIncomes;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onWhatTC.setCellValueFactory(new PropertyValueFactory<>("onWhat"));
        expAmmountTC.setCellValueFactory(new PropertyValueFactory<>("expAmmount"));
        expanesesTV.setEditable(true);

        fromWhatTC.setCellValueFactory(new PropertyValueFactory<>("fromWhat"));
        incAmmountTC.setCellValueFactory(new PropertyValueFactory<>("incAmmount"));
        incomesTV.setEditable(true);



        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onMouseClickedIn(MouseEvent mouseEvent) {
        Incomes selecIncome = incomesTV.getSelectionModel().getSelectedItem();
        fromWhatTF.setText(selecIncome.getFromWhat());
        incAmmountTF.setText(String.valueOf(selecIncome.getIncAmmount()));
    }

    public void onMouseClickedEx(MouseEvent mouseEvent) {
        Expenses selectExpenses = expanesesTV.getSelectionModel().getSelectedItem();
        onWhatTF.setText(selectExpenses.getOnWhat());
        expAmmountTF.setText(String.valueOf(selectExpenses.getExpAmmount()));
    }
    public void clearFields(){
        onWhatTF.clear();
        expAmmountTF.clear();
        fromWhatTF.clear();
        incAmmountTF.clear();
    }

}
