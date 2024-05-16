package com.example.gestionconference.Services.Sponsoring;

import com.example.gestionconference.Models.Sponsoring.Sponsor;
import com.example.gestionconference.Models.Sponsoring.SponsorRejected;
import com.example.gestionconference.Util.MyDB;
import javafx.beans.binding.ListBinding;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SponsorRejectedServices {

    private Connection connection;

    public SponsorRejectedServices() {
        connection = MyDB.getInstance().getCnx();
    }

    public List<SponsorRejected> getAllRejectedSponsors() throws SQLException {
        String sql = "SELECT * FROM sponsorrejected";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<SponsorRejected> rejectedSponsors = new ArrayList<>();
        while (rs.next()) {
            SponsorRejected sp = new SponsorRejected();
            sp.setId(rs.getInt("id"));
            sp.setId(rs.getInt("idsponsor"));
            sp.setCause(rs.getString("cause"));
            rejectedSponsors.add(sp);
        }
        return rejectedSponsors;
    }

    // Convert a single Sponsor object to a SponsorRejected object
    private SponsorRejected convertToSponsorRejected(ResultSet rs) throws SQLException {
        return new SponsorRejected(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("email"),
                rs.getString("numtel"),
                rs.getString("cause")
        );
    }

    // Method to convert a list of Sponsor objects to a list of SponsorRejected objects
    public List<SponsorRejected> convertToSponsorRejectedList(List<Sponsor> sponsors) {
        List<SponsorRejected> sponsorRejectedList = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            sponsorRejectedList.add(new SponsorRejected(
                    sponsor.getId(),
                    sponsor.getNom(),
                    sponsor.getEmail(),
                    sponsor.getNumtel(),
                    sponsor.getCause()
            ));
        }
        return sponsorRejectedList;
    }

    // Other methods specific to rejected sponsors can be added here if needed
}
