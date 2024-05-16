package com.example.gestionconference.Controllers.EvaluationController;


import com.example.gestionconference.Controllers.Sponsoring.ViewSponsor;
import com.example.gestionconference.Models.EvaluationModels.*;
import com.example.gestionconference.Services.EvaluationService.Crudcommentaire;
import com.example.gestionconference.Services.EvaluationService.Crudnote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentaireController {

    @FXML
    private TextField caractereField;

    @FXML
    private TextField notationField;

    @FXML
    private TextField imageField;

    @FXML
    private Label resultLabel;

    @FXML
    private ListView<Commentaire> commentListView;

    private Crudcommentaire crudcommentaire;
    private Crudnote crudnote;

    private FileChooser fileChooser;
    private Stage stage;

    public CommentaireController() {
        crudcommentaire = new Crudcommentaire();
        crudnote = new Crudnote();
        fileChooser = new FileChooser();
    }

    @FXML
    private void initialize() {
        updateListView();

        commentListView.setCellFactory(new Callback<ListView<Commentaire>, ListCell<Commentaire>>() {
            @Override
            public ListCell<Commentaire> call(ListView<Commentaire> listView) {
                return new ListCell<Commentaire>() {
                    private final Button deleteButton = new Button("Delete");

                    @Override
                    protected void updateItem(Commentaire item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.getCaractere());
                            setGraphic(deleteButton);
                            deleteButton.setOnAction(event -> {
                                crudcommentaire.supprimerCommentaire(item.getIdCommentaire());
                                CommentaireController.this.updateListView();
                            });
                        }
                    }
                };
            }
        });

        commentListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Commentaire selectedComment = commentListView.getSelectionModel().getSelectedItem();
                caractereField.setText(selectedComment.getCaractere());
            }
        });
    }

    @FXML
    private void handleAddComment() {
        String commentaireText = caractereField.getText();
        if (commentaireText.isEmpty()) {
            showAlert("Comment field is empty.");
            return;
        }
        Commentaire commentaire = new Commentaire(0, commentaireText);
        crudcommentaire.addCommentaire(commentaire);
        resultLabel.setText("Added comment: " + commentaireText);
        handleAddNote();
        caractereField.clear();
        updateListView();
    }



    /*private void handleControlleComment() {
        String commentaireText = caractereField.getText();
        if (commentaireText == "idiot") {
            showAlert("Ce mot n'est pas approprié.");
            return;
        }
        Commentaire commentaire = new Commentaire(0, commentaireText);
        crudcommentaire.addCommentaire(commentaire);
        resultLabel.setText("Added comment: " + commentaireText);
        handleAddNote();
        caractereField.clear();
        updateListView();
    }



    # Liste de gros mots
bad_words = ["merde ", "idiot", " fuck you"]

@app.route('/filter_bad_words', methods=['POST'])
def filter_bad_words():
    if request.method == 'POST':
        # Récupère le texte à filtrer depuis la requête
        data = request.json
        text = data['text']

        # Filtrer les gros mots
        filtered_text = filter_text(text)

        # Retourne le texte filtré
        return jsonify({'filtered_text': filtered_text})

def filter_text(text):
    # Remplace tous les gros mots par des astérisques
    for word in bad_words:
        text = text.replace(word, '*' * len(word))
    return text

if __name__ == '__main__':
    app.run(debug=True)
    */
    @FXML
    private AnchorPane JeanPane1;



    @FXML
    private void handleUpdateComment() {
        String commentaireText = caractereField.getText();
        if (commentaireText.isEmpty()) {
            showAlert("Comment field is empty.");
            return;
        }
        Commentaire commentaire = commentListView.getSelectionModel().getSelectedItem();
        commentaire.setCaractere(commentaireText);
        crudcommentaire.modifierCommentaire(commentaire);
        resultLabel.setText("Updated comment: " + commentaireText);
        caractereField.clear();
        updateListView();
    }

    @FXML
    private void handleChooseImage(ActionEvent event) {
        configureFileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            imageField.setText(file.getAbsolutePath());
        }
    }

    private void handleAddNote() {
        String notationText = notationField.getText();
        if (notationText.isEmpty()) {
            showAlert("Notation field is empty.");
            return;
        }
        int notation = Integer.parseInt(notationText);
        String image = imageField.getText();
        if (image.isEmpty()) {
            showAlert("Image field is empty.");
            return;
        }
        Note note = new Note(0, notation, image);
        crudnote.addNote(note);
        notationField.clear();
        imageField.clear();
        updateListView();
    }

    private void updateListView() {
        ObservableList<Commentaire> comments = FXCollections.observableArrayList(crudcommentaire.afficherCommentaire());
        commentListView.setItems(comments);
    }

    private void configureFileChooser() {
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleGoToNote(ActionEvent event) throws IOException {
     /*   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test/Affichernote.fxml"));
        Parent root = fxmlLoader.load();
        JeanPane1.getChildren().setAll(root); */


      /*  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test/Affichernote.fxml"));
        Parent root = fxmlLoader.load();
        JeanPane1.getChildren().setAll(root);*/

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/EvaluationFXML/Affichernote.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/


        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gestionconference/Fxml/EvaluationFXML/Affichernote.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewSponsor.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void OnStats(ActionEvent event) throws IOException {
      /*  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test/statistique.fxml"));
        Parent root = fxmlLoader.load();
        JeanPane1.getChildren().setAll(root);*/

       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/EvaluationFXML/statistique.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/



        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gestionconference/Fxml/EvaluationFXML/statistique.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewSponsor.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
