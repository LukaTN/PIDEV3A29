package com.example.gestionconference.Models.SessionModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataModel {
    private final StringProperty uid;

    public DataModel(String uid) {
        this.uid = new SimpleStringProperty(uid);
    }

    public String getName() {
        return uid.get();
    }

    public StringProperty nameProperty() {
        return uid;
    }

    public void setName(String uid) {
        this.uid.set(uid);
    }
}
