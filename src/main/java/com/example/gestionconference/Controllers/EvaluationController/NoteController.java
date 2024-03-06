package com.example.gestionconference.Controllers.EvaluationController;



import com.example.gestionconference.Models.EvaluationModels.*;
import com.example.gestionconference.Services.EvaluationService.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

public class NoteController {

    @FXML
    private ListView<Note> noteListView;

    @FXML
    private Label resultLabel;

    private Crudnote crudnote;

    public NoteController() {
        crudnote = new Crudnote();
    }

    @FXML
    private void initialize() {
        updateListView();
    }
    private void updateListView() {
        ObservableList<Note> notes = FXCollections.observableArrayList( crudnote.afficherNote());
        noteListView.setItems(notes);
        noteListView.setCellFactory(param -> new ListCell<Note>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Note item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    Image image = new Image("file://" + item.getImage()); // Constructing the Image with proper URL
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set image width
                    imageView.setFitHeight(100); // Set image height
                    setGraphic(imageView);
                }
            }
        });
    }
    public void OnCommentaire(ActionEvent actionEvent) throws IOException {
       FXMLLoader fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gestionconference/Fxml/EvaluationFXML/Affichercom.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) noteListView.getScene().getWindow();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
