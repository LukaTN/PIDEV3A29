package com.example.gestionconference.Models.LogisticModels;

import java.io.File;


public class LogisticIncome {
    private int logIncomeId;
    private String logSponsorName;
    private int logIncomeQty;
    private byte[] logProof;
    private int logisticId;

    public LogisticIncome() {
    }

    public LogisticIncome(String logSponsorName, int logIncomeQty, byte[] logProof) {
        this.logSponsorName = logSponsorName;
        this.logIncomeQty = logIncomeQty;
        this.logProof = logProof;
    }

    public int getLogIncomeId() {
        return logIncomeId;
    }

    public void setLogIncomeId(int logIncomeId) {
        this.logIncomeId = logIncomeId;
    }

    public String getLogSponsorName() {
        return logSponsorName;
    }

    public void setLogSponsorName(String logSponsorName) {
        this.logSponsorName = logSponsorName;
    }

    public int getLogIncomeQty() {
        return logIncomeQty;
    }

    public void setLogIncomeQty(int logIncomeQty) {
        this.logIncomeQty = logIncomeQty;
    }

    public byte[] getLogProof() {
        return logProof;
    }

    public void setLogProof(byte[] logProof) {
        this.logProof = logProof;
    }

    public int getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(int logisticId) {
        this.logisticId = logisticId;
    }
}