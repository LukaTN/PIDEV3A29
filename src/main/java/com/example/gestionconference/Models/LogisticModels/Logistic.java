package com.example.gestionconference.Models.LogisticModels;

public class Logistic {

    private int logisticId;
    private String ProvidedLog;
    private int quantity;

    public Logistic() {
    }
    public Logistic(String providedLog, int quantity) {

        ProvidedLog = providedLog;
        this.quantity = quantity;
    }
    public Logistic(int logisticId, String providedLog, int quantity) {
        this.logisticId = logisticId;
        ProvidedLog = providedLog;
        this.quantity = quantity;
    }

    public int getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(int logisticId) {
        this.logisticId = logisticId;
    }

    public String getProvidedLog() {
        return ProvidedLog;
    }

    public void setProvidedLog(String providedLog) {
        ProvidedLog = providedLog;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Logistic{" +
                "logisticId=" + logisticId +
                ", ProvidedLog='" + ProvidedLog + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
