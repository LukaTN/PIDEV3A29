package com.example.gestionconference.Services.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.SponsorAccepted;
import com.example.gestionconference.Util.MyDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SponsorAcceptedServices {

    private Connection connection;

    public SponsorAcceptedServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public List<SponsorAccepted> getAllAcceptedSponsors() throws SQLException {
        String sql = "SELECT * FROM sponsor WHERE status = 'ACCEPTED'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<SponsorAccepted> acceptedSponsors = new ArrayList<>();
        while (rs.next()) {
            SponsorAccepted sponsorAccepted = convertToSponsorAccepted(rs);
            acceptedSponsors.add(sponsorAccepted);
        }
        return acceptedSponsors;
    }

    // Convert a single Sponsor object to a SponsorAccepted object
    private SponsorAccepted convertToSponsorAccepted(ResultSet rs) throws SQLException {
        return new SponsorAccepted(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("email"),
                rs.getString("numtel"),
                rs.getDouble("budget")
        );
    }

    // Method to convert a list of Sponsor objects to a list of SponsorAccepted objects
    public List<SponsorAccepted> convertToSponsorAcceptedList(List<Sponsor> sponsors) {
        List<SponsorAccepted> sponsorAcceptedList = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            sponsorAcceptedList.add(new SponsorAccepted(
                    sponsor.getId(),
                    sponsor.getNom(),
                    sponsor.getEmail(),
                    sponsor.getNumtel(),
                    sponsor.getBudget()
            ));
        }
        return sponsorAcceptedList;
    }

    // Other methods specific to accepted sponsors can be added here if needed
}
