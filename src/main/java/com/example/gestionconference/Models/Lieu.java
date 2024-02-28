package com.example.gestionconference.Models;

public class Lieu {
    private int id;
    private String label;
    private String place;
    private String zone;
    private int capacity;

    // Constructor for Lieu
    public Lieu(  String zone,String place,  int capacity,String label) {
        this.zone = zone;
        this.place = place;
        this.capacity = capacity;
        this.label = label;
    }
    public Lieu(){};

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", place='" + place + '\'' +
                ", zone='" + zone + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
