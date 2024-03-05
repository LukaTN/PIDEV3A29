package com.example.gestionconference.Models.UserModels;

import java.util.Arrays;

public class User {

    private int id;

    private String username;
    private String nom;  // changed from 'name' to 'nom'
    private String prenom;  // added 'prenom'
    private String mail;
    private String password;
    private int phone;
    private String role;
    private byte[] profilePicture;



    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    //getters and setters for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    //getters and setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public User(String username, String nom, String prenom, String mail, String password, int phone, String role, byte[] profilePicture) {
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.profilePicture = profilePicture;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", role='" + role + '\'' +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                '}';
    }

    public User() {
    }



}
