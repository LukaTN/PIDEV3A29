package com.example.gestionconference.Models.BudgetModels;

public class Expenses {
    private int expensesId;
    private String onWhat;
    private int expAmmount;

    public Expenses() {
    }

    public Expenses(String onWhat, int expAmmount) {
        this.onWhat = onWhat;
        this.expAmmount = expAmmount;
    }

    public int getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(int expensesId) {
        this.expensesId = expensesId;
    }

    public String getOnWhat() {
        return onWhat;
    }

    public void setOnWhat(String onWhat) {
        this.onWhat = onWhat;
    }

    public int getExpAmmount() {
        return expAmmount;
    }

    public void setExpAmmount(int expAmmount) {
        this.expAmmount = expAmmount;
    }
}
