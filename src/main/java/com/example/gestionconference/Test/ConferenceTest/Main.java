package com.example.gestionconference.Test.ConferenceTest;

import com.example.gestionconference.Controllers.ConferenceControllers.StatesApi;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
//        ConferenceType ct = ConferenceType.PUBLIC;
//        Conference c = new Conference(29,"messiii", Date.valueOf("2024-02-21"),"gouhn",641,ct,2);
//
//        ConferenceServices cs = new ConferenceServices();
//        cs.updateConference(c);

        StatesApi ss = new StatesApi();
        System.out.println(ss.getbyCountry("Tunisia"));
    }



}