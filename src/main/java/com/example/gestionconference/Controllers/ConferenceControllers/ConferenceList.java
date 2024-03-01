package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ConferenceList  {


    ConferenceServices ss = new ConferenceServices();
    LieuServices ls = new LieuServices();

    @FXML
    private GridPane event_gridPane;

    @FXML
    private Button addConf;

    @FXML
    public void initialize() {
        diplayConferences();
    }



    @FXML
    void toAddConf(ActionEvent event) {
        ControllerCommon cc = new ControllerCommon();
        cc.jump("Confera", "/com/example/gestionconference/Fxml/ConferenceFxml/Conference.fxml",addConf);
    }



    public void diplayConferences(){
            try {
                List<Conference> eventList = ss.getAllConferences();
               event_gridPane.getChildren().clear();

                int column=0;
                int row = 0;

                for (Conference conference : eventList) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFxml/items.fxml"));
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
}

