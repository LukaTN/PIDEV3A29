package com.example.gestionconference.Controllers.Sponsoring;
import javafx.scene.control.TableView;
import com.example.gestionconference.Models.Sponsoring.Sponsor;
import javafx.fxml.FXML;

public class ViewAcceptedSponsors {


//    public ViewAcceptedSponsors(SponsorService sponsorService) {
//    }

    @FXML
    private TableView<Sponsor> acceptedSponsorsTable;

    // Method to fetch and display accepted sponsors
    @FXML
    private void initialize() {
        // Call the service method to retrieve accepted sponsors
        // List<Sponsor> acceptedSponsors = sponsorService.getAcceptedSponsors();

        // Populate the TableView with the retrieved sponsor data
        // acceptedSponsorsTable.setItems(FXCollections.observableList(acceptedSponsors));
    }
}
