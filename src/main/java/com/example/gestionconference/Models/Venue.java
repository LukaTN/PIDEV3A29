package com.example.gestionconference.Models;


import java.util.Objects;

public class Venue {
    private int id;
    private String venueName;
    private String email;
    private String phone;
    private String city;
    private String adresse;
    private String output;

    private Status status;




        public Venue() {
        }

    public Venue(String venueName, String email, String phone, String city, String adresse, String output, Status status) {
        this.venueName = venueName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.adresse = adresse;
        this.output = output;
        this.status = status;
    }

    public Venue(int id, String venueName, String email, String phone, String city, String adresse, String output, Status status) {
        this.id = id;
        this.venueName = venueName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.adresse = adresse;
        this.output = output;
        this.status = status;
    }


    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", venueName='" + venueName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", adresse='" + adresse + '\'' +
                ", output='" + output + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return id == venue.id && status == venue.status && Objects.equals(venueName, venue.venueName) && Objects.equals(email, venue.email) && Objects.equals(phone, venue.phone) && Objects.equals(city, venue.city) && Objects.equals(adresse, venue.adresse) && Objects.equals(output, venue.output);
    }

}
