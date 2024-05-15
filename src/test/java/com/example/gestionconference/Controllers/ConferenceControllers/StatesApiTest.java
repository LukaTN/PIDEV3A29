package com.example.gestionconference.Controllers.ConferenceControllers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatesApiTest {

    @Test
    void getbyCountry() {
        List<String> States = new ArrayList<>();
        States = StatesApi.getbyCountry("Tunisia");
        System.out.println(States);
    }
}