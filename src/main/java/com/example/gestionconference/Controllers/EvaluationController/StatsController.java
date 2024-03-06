package com.example.gestionconference.Controllers.EvaluationController;

import com.example.gestionconference.Models.EvaluationModels.Note;
import com.example.gestionconference.Services.EvaluationService.*;
import com.example.gestionconference.Services.EvaluationService.Crudnote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class StatsController {

    @FXML
    private Label commentCountLabel;

    @FXML
    private Label averageCommentLengthLabel;

    @FXML
    private Label noteCountLabel;

    @FXML
    private Label averageNoteValueLabel;

    private Crudcommentaire crudcommentaire;
    private Crudnote crudnote;

    public StatsController() {
        crudcommentaire = new Crudcommentaire();
        crudnote = new Crudnote();
    }

    @FXML
    private void initialize() {
        updateCommentStatistics();
        updateNoteStatistics();
    }

    private void updateCommentStatistics() {
        int commentCount = crudcommentaire.getCommentCount();
        double averageCommentLength = crudcommentaire.getAverageCommentLength();

        commentCountLabel.setText("Total Comments: " + commentCount);
        averageCommentLengthLabel.setText("Average Comment Length: " + averageCommentLength);
    }

    private void updateNoteStatistics() {
        int noteCount = crudnote.getNoteCount();
        double averageNoteValue = crudnote.getAverageNoteValue();

        noteCountLabel.setText("Total Notes: " + noteCount);
        averageNoteValueLabel.setText("Average Note Value: " + averageNoteValue);
    }
}



