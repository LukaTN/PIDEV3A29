package com.example.gestionconference.Test.SponsoringMain;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.SponsorRejected;
import com.example.gestionconference.Services.Sponsoring.SponsorRejectedServices;
import com.example.gestionconference.Services.Sponsoring.SponsorServices;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Create a new sponsor object with details
      //  Sponsor sponsor = new Sponsor(1, "Example Sponsor", "sponsor@example.com", "123456789" ,"acced");

        // Instantiate the SponsorService
       SponsorServices sponsorService = new SponsorServices();
        SponsorRejectedServices sr = new SponsorRejectedServices();

        System.out.println(sr.getAllRejectedSponsors());
        // Call the add method to add the sponsor to the database
      //  sponsorService.add(sponsor);

        //
    }
}
