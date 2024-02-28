package com.example.gestionconference.Models;

public enum Status {
    APPROVED("approved"),
    REJECTED("rejected");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}