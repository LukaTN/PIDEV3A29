package com.example.gestionconference.Models.BudgetModels;

public class Incomes {
    private int incomesId;
    private String fromWhat;
    private int incAmmount;

    public Incomes() {
    }

    public Incomes(String fromWhat, int incAmmount) {
        this.fromWhat = fromWhat;
        this.incAmmount = incAmmount;
    }

    public int getIncomesId() {
        return incomesId;
    }

    public void setIncomesId(int incomesId) {
        this.incomesId = incomesId;
    }

    public String getFromWhat() {
        return fromWhat;
    }

    public void setFromWhat(String fromWhat) {
        this.fromWhat = fromWhat;
    }

    public int getIncAmmount() {
        return incAmmount;
    }

    public void setIncAmmount(int incAmmount) {
        this.incAmmount = incAmmount;
    }
}
