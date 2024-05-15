package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AnchorPaneNode extends AnchorPane{

    private ConferenceServices es = new ConferenceServices();

    // Date associated with this pane
    private LocalDate date;

    public AnchorPaneNode(Node... children) {
        super(children);

        // Add action handler for mouse clicked
        this.setOnMouseClicked(e ->
        {
            System.out.println("This pane's date is: " + date);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Conferences on " + date);
            alert.setHeaderText(null);
            try {
                List<String> confNames = es.getConfereceNameByDate(Date.valueOf(date));

                if (confNames != null && !confNames.isEmpty()) {
                    // Hauteur fixe par élément
                    double cellHeight = 24.0; // Vous pouvez ajuster cette valeur selon vos besoins

                    // Calcul de la hauteur totale du ListView
                    double totalHeight = Math.min(confNames.size() * cellHeight, 200.0); // Limitez à une hauteur maximale, par exemple, 200.0

                    ListView<String> listView = new ListView<>();
                    listView.setPrefHeight(totalHeight); // Définissez la hauteur du ListView

                    listView.getItems().addAll(confNames.toString().replace("[", "").replace("]", ""));

                    // Ajoutez ListView à la boîte de dialogue
                    alert.getDialogPane().setContent(listView);
                } else {
                    alert.setContentText("No conferneces on this date.");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            alert.showAndWait();
        });
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


