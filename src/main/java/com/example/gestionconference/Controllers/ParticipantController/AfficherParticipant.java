package com.example.gestionconference.Controllers.ParticipantController;


import com.example.gestionconference.Models.Participant.Participant;
import com.example.gestionconference.Services.ParticipantServices.ParticipantServices;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherParticipant implements Initializable {


    @FXML
    private TableColumn<Participant, Date> birthdayTC;

    @FXML
    private TableColumn<Participant, String> cinTC;

    @FXML
    private TextField cinTF;

    @FXML
    private TableColumn<Participant, String> emailTC;

    @FXML
    private TableColumn<Participant, String> nameTC;

    @FXML
    private TextField nameTF;

    @FXML
    private TableView<Participant> participantTV;

    @FXML
    private TableColumn<Participant, String> phoneTC;

    @FXML
    private Label nbrOfParticipants;

    ParticipantServices ps = new ParticipantServices();

    private void updateNumberOfParticipants() {
        try {
            int numberOfParticipants = ps.getAll().size();
            nbrOfParticipants.setText(String.valueOf(numberOfParticipants));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Reset(ActionEvent event) {
        try {
            refreshTable();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTC.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cinTC.setCellValueFactory(new PropertyValueFactory<>("cin"));
        birthdayTC.setCellValueFactory(new PropertyValueFactory<>("DateN"));
        phoneTC.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            refreshTable();
            updateNumberOfParticipants();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Participant> participants = null;
        try {
            participants = FXCollections.observableArrayList(ps.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FilteredList<Participant> filtredData = new FilteredList<>(participants, b -> true);

        // Ajouter une ListChangeListener pour écouter les changements dans la liste filtrée
        filtredData.addListener((ListChangeListener<Participant>) c -> {
            participantTV.setItems(new SortedList<>(filtredData));
        });

        // Ajouter un ChangeListener pour écouter les changements dans le TextField nameTF
        nameTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filtredData.setPredicate(participant -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (participant.getNom().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        // Ajouter un ChangeListener pour écouter les changements dans le TextField cinTF
        cinTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filtredData.setPredicate(participant -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (participant.getCin().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    }

    private void refreshTable() throws SQLException {
        ObservableList<Participant> participants = FXCollections.observableArrayList(ps.getAll());
        participantTV.setItems(participants);
    }
    public void clearFields(){
        nameTF.clear();
        cinTF.clear();

    }

    @FXML
    void exportToExcel(ActionEvent event) {
        try {
            List<Participant> participants = ps.getAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Participants");

            // Créer la première ligne avec les titres de colonnes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("CIN");
            headerRow.createCell(2).setCellValue("Date de naissance");
            headerRow.createCell(3).setCellValue("Numéro de téléphone");
            headerRow.createCell(4).setCellValue("Email");

            // Remplir les données des participants dans le fichier Excel
            int rowNum = 1;
            for (Participant participant : participants) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(participant.getNom());
                row.createCell(1).setCellValue(participant.getCin());
                row.createCell(2).setCellValue(participant.getDateN().toString());
                row.createCell(3).setCellValue(participant.getNumTel());
                row.createCell(4).setCellValue(participant.getEmail());
            }

            // Ouvrir une boîte de dialogue de sélection de fichier pour choisir l'emplacement de téléchargement
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            // Vérifier si un fichier a été sélectionné
            if (selectedFile != null) {
                // Enregistrer le fichier Excel dans l'emplacement sélectionné
                try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {
                    workbook.write(outputStream);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Download Successful");
                alert.setContentText("The Excel file has been downloaded successfully.");
                alert.showAndWait();


                System.out.println("Excel file created successfully in the selected location!");
            } else {
                System.out.println("No download location selected.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
