package com.example.gestionconference.Models.FinancialIncomesModels;

import java.util.Arrays;

public class FinancialIncomes {
    private int financialIncomesId;
    private String sponsorName;
    private int cashIn;
    private byte[] proof;

    public FinancialIncomes() {

    }


    public FinancialIncomes(int financialIncomesId, String sponsorName, int cashIn, byte[] proof) {
        this.financialIncomesId = financialIncomesId;
        this.sponsorName = sponsorName;
        this.cashIn = cashIn;
        this.proof = proof;
    }

    public FinancialIncomes(String sponsorName, int cashIn, byte[] proof) {
        this.sponsorName = sponsorName;
        this.cashIn = cashIn;
        this.proof = proof;
    }

    public byte[] getProof() {
        return proof;
    }

    public void setProof(byte[] proof) {
        this.proof = proof;
    }

    public FinancialIncomes(int financialIncomesId, String sponsorName, int cashIn) {
        this.financialIncomesId = financialIncomesId;
        this.sponsorName = sponsorName;
        this.cashIn = cashIn;

    }

    public int getFinancialIncomesId() {
        return financialIncomesId;
    }

    public void setFinancialIncomesId(int financialIncomesId) {
        this.financialIncomesId = financialIncomesId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public int getCashIn() {
        return cashIn;
    }

    public void setCashIn(int cashIn) {
        this.cashIn = cashIn;
    }





}
