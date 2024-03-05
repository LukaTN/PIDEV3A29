package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ConferenceList {

    @FXML
    private ChoiceBox triConference;

    @FXML
    private CheckBox confType;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane event_gridPane;

    @FXML
    private Button addConf;

    private ConferenceServices ss = new ConferenceServices();

    @FXML
    public void initialize() {
        diplayConferences();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchConference(newValue);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        triConference.setItems(FXCollections.observableArrayList(
                "Sort by Name", "Sort by Budget", "Sort by Date"
        ));

        // Set default value
        triConference.setValue("Sorting");
    }


    @FXML
    void toAddConf(ActionEvent event) {
        ControllerCommon cc = new ControllerCommon();
        cc.jump("Confera", "/com/example/gestionconference/Fxml/ConferenceFXML/Conference.fxml", addConf);
    }


    public void diplayConferences() {
        try {
            List<Conference> eventList = ss.getAllConferences();
            event_gridPane.getChildren().clear();

            int column = 0;
            int row = 0;

            for (Conference conference : eventList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/items.fxml"));
                AnchorPane pane = loader.load();
                Items items = loader.getController();
                items.setData(conference);
                pane.getProperties().put("controller", this);

                if (column == 1) {
                    column = 0;
                    row++;
                }
                event_gridPane.add(pane, column++, row);

            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());

        }
    }

    public void searchConference(Object o) throws SQLException, IOException {
        //String text = searchField.getText();
        List<Conference> filteredConferences = ss.getAllConferenceObservable(o);


        setData(filteredConferences);

    }

    public void toConfType(ActionEvent actionEvent) {
        if (confType.isSelected()) {
            try {
                List<Conference> filteredConferences = ss.getPublicorPrivate(ConferenceType.PRIVATE);
                setData(filteredConferences);
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }else {
            diplayConferences();
        }
    }
    public void setData(List<Conference> filteredConferences) {
        try {

            event_gridPane.getChildren().clear();
            int column = 0;
            int row = 0;

            for (Conference conference : filteredConferences) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/items.fxml"));
                AnchorPane pane = loader.load();
                Items items = loader.getController();
                items.setData(conference);
                pane.getProperties().put("controller", this);

                if (column == 1) {
                    column = 0;
                    row++;
                }
                event_gridPane.add(pane, column++, row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void handleSortChoice(ActionEvent actionEvent) {
        String selectedSort = triConference.getValue().toString();

        try {
            switch (selectedSort) {
                case "Sort by Name":
                    setData(ss.triConferenceByname());
                    break;
                case "Sort by Budget":
                    setData(ss.triConferenceByBudget());
                    break;
                case "Sort by Date":
                    setData(ss.triConferenceByDate());
                    break;
                default:
                    // Handle default case if needed
                    break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}


