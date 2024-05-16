package com.example.gestionconference.Models.BudgetModels;

public class EstimatedExpenses {
    private int estimatedExpensesId;
    private String expensesWay;
    private double pessimisticExpenses;
    private double realisticExpenses;
    private double optimisticExpenses;

    public EstimatedExpenses() {
    }

    public EstimatedExpenses(String expensesWay, double pessimisticExpenses, double realisticExpenses, double optimisticExpenses) {
        this.expensesWay = expensesWay;
        this.pessimisticExpenses = pessimisticExpenses;
        this.realisticExpenses = realisticExpenses;
        this.optimisticExpenses = optimisticExpenses;
    }

    public int getEstimatedExpensesId() {
        return estimatedExpensesId;
    }

    public void setEstimatedExpensesId(int estimatedExpensesId) {
        this.estimatedExpensesId = estimatedExpensesId;
    }

    public String getExpensesWay() {
        return expensesWay;
    }

    public void setExpensesWay(String expensesWay) {
        this.expensesWay = expensesWay;
    }

    public double getPessimisticExpenses() {
        return pessimisticExpenses;
    }

    public void setPessimisticExpenses(double pessimisticExpenses) {
        this.pessimisticExpenses = pessimisticExpenses;
    }

    public double getRealisticExpenses() {
        return realisticExpenses;
    }

    public void setRealisticExpenses(double realisticExpenses) {
        this.realisticExpenses = realisticExpenses;
    }

    public double getOptimisticExpenses() {
        return optimisticExpenses;
    }

    public void setOptimisticExpenses(double optimisticExpenses) {
        this.optimisticExpenses = optimisticExpenses;
    }
}
