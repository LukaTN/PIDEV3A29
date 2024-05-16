package com.example.gestionconference.Controllers.BudgetController;

import com.example.gestionconference.Models.BudgetModels.EstimatedExpenses;
import com.example.gestionconference.Models.BudgetModels.EstimatedIncomes;
import com.example.gestionconference.Services.BudgetServices.EstimatedExpensesServices;
import com.example.gestionconference.Services.BudgetServices.EstimatedIncomesServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.List;

public class EstimationV {
    @FXML
    private PieChart optimisticChart;

    @FXML
    private PieChart pessimisticChart;

    @FXML
    private PieChart realisticChart;


    // Méthode d'initialisation du contrôleur
    @FXML
    public void initialize() {
        // Récupération des services
        EstimatedExpensesServices expensesServices = new EstimatedExpensesServices();
        EstimatedIncomesServices incomesServices = new EstimatedIncomesServices();

        // Récupération des dépenses et des revenus pessimistes
        double totalPessimisticExpenses = 0.0;
        double totalPessimisticIncomes = 0.0;

        double totalRealisticExpenses = 0.0;
        double totalRealisticIncomes = 0.0;

        double totalOptimisticExpenses = 0.0;
        double totalOptimisticIncomes = 0.0;

        try {
            List<EstimatedExpenses> expensesList = expensesServices.getAll();
            for (EstimatedExpenses expense : expensesList) {
                totalPessimisticExpenses += expense.getPessimisticExpenses();
                totalRealisticExpenses += expense.getRealisticExpenses();
                totalOptimisticExpenses += expense.getOptimisticExpenses();
            }

            List<EstimatedIncomes> incomesList = incomesServices.getAll();
            for (EstimatedIncomes income : incomesList) {
                totalPessimisticIncomes += income.getPessimisticIncome();
                totalRealisticIncomes += income.getRealisticIncome();
                totalOptimisticIncomes += income.getOptimisticIncome();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Création du PieChart avec les valeurs récupérées
        ObservableList<PieChart.Data> pessimisticPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Pessimistic Expenses", totalPessimisticExpenses),
                new PieChart.Data("Pessimistic Incomes", totalPessimisticIncomes)
        );

        ObservableList<PieChart.Data> realisticPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Realistic Expenses", totalRealisticExpenses),
                new PieChart.Data("Realistic Incomes", totalRealisticIncomes)
        );

        ObservableList<PieChart.Data> optimisticPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Optimistic Expenses", totalOptimisticExpenses),
                new PieChart.Data("Optimistic Incomes", totalOptimisticIncomes)
        );

        pessimisticChart.setData(pessimisticPieChartData);
        realisticChart.setData(realisticPieChartData);
        optimisticChart.setData(optimisticPieChartData);
    }
}
