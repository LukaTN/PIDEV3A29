package com.example.gestionconference.Controllers;

import com.example.gestionconference.Models.Sponsor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSponsor implements Initializable {

    @FXML
    private TableView<Sponsor> sponsors;

    @FXML
    private TableColumn<Sponsor, Integer> idCol;

    @FXML
    private TableColumn<Sponsor, String> typeCol;

    @FXML
    private TableColumn<Sponsor, String> nameCol;

    @FXML
    private TableColumn<Sponsor, String> statusCol;

    @FXML
    private Text id;

    @FXML
    private TextField inputType;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        setupTable();
    }

    @FXML
    void submit(ActionEvent event) {
        ObservableList<Sponsor> currentTableData = sponsors.getItems();
        int currentSponsorId = Integer.parseInt(id.getText());

        for (Sponsor sponsor : currentTableData) {
            if (sponsor.getId() == currentSponsorId) {
                sponsor.setType(inputType.getText());
                sponsor.setName(inputName.getText());
                sponsor.setStatus(inputStatus.getText());

                sponsors.setItems(currentTableData);
                sponsors.refresh();
                break;
            }
        }
    }

    @FXML
    void rowClicked(MouseEvent event) {
        Sponsor clickedSponsor = sponsors.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(clickedSponsor.getId()));
        inputType.setText(clickedSponsor.getType());
        inputName.setText(clickedSponsor.getName());
        inputStatus.setText(clickedSponsor.getStatus());
    }

    private void setupTable() {
        Sponsor sponsor0 = new Sponsor(0, "Type A", "Company A", "Accepted");
        Sponsor sponsor1 = new Sponsor(1, "Type B", "Company B", "Rejected");
        Sponsor sponsor2 = new Sponsor(2, "Type C", "Company C", "Accepted");
        sponsors.getItems().addAll(sponsor0, sponsor1, sponsor2);
    }
}
