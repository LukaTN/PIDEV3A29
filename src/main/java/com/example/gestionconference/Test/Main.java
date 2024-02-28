package com.example.gestionconference.Test;

import com.example.gestionconference.Models.Sponsor;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Create a new sponsor object with details
        Sponsor sponsor = new Sponsor(1, "Example Sponsor", "sponsor@example.com", 123456789);

        // Instantiate the SponsorService
      //  SponsorService sponsorService = new SponsorService();

        // Call the add method to add the sponsor to the database
      //  sponsorService.add(sponsor);
    }
}
