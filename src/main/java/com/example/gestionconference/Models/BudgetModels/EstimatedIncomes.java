package com.example.gestionconference.Models.BudgetModels;

public class EstimatedIncomes {
    private int estimatedIncomesId;
    private String incomeSource;
    private double pessimisticIncome;
    private double realisticIncome;
    private double optimisticIncome;

    public EstimatedIncomes() {
    }

    public EstimatedIncomes(String incomeSource, double pessimisticIncome, double realisticIncome, double optimisticIncome) {
        this.incomeSource = incomeSource;
        this.pessimisticIncome = pessimisticIncome;
        this.realisticIncome = realisticIncome;
        this.optimisticIncome = optimisticIncome;
    }

    public int getEstimatedIncomesId() {
        return estimatedIncomesId;
    }

    public void setEstimatedIncomesId(int estimatedIncomesId) {
        this.estimatedIncomesId = estimatedIncomesId;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }

    public double getPessimisticIncome() {
        return pessimisticIncome;
    }

    public void setPessimisticIncome(double pessimisticIncome) {
        this.pessimisticIncome = pessimisticIncome;
    }

    public double getRealisticIncome() {
        return realisticIncome;
    }

    public void setRealisticIncome(double realisticIncome) {
        this.realisticIncome = realisticIncome;
    }

    public double getOptimisticIncome() {
        return optimisticIncome;
    }

    public void setOptimisticIncome(double optimisticIncome) {
        this.optimisticIncome = optimisticIncome;
    }
}
