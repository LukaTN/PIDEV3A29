package com.example.gestionconference.Models.UserModels;

public class User {
private int id;
    private String username;
    private String nom;  // changed from 'name' to 'nom'
    private String prenom;  // added 'prenom'
    private String mail;
    private String password;
    private int phone;
    private String role;

    //getters and setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }





    public String getUsername() {
        return username;
    }

    public User(String username, String nom, String prenom, String mail, String password, int phone, String role) {
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public User(int id ,String username) {
        this.username = username;
     this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                '}';
    }

    public User() {
    }
}
