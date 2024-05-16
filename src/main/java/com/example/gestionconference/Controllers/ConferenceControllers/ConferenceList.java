package com.example.gestionconference.Controllers.ConferenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.List;


public class ConferenceList {

    private User user;

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
   // private User user;

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
                List<Conference> filteredConferences = ss.getPublicorPrivate(true);
                setData(filteredConferences);
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        } else {
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
    @FXML
    void showCalendar(ActionEvent event) throws IOException {
        // Charger le fichier FXML pour la nouvelle fenêtre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/ConferenceFXML/Calendar.fxml"));
        Parent calendarRoot = loader.load();

        // Créer une nouvelle scène pour la nouvelle fenêtre
        Scene calendarScene = new Scene(calendarRoot);
        calendarScene.getStylesheets().add(getClass().getResource("/com/example/gestionconference/Styles/calendar.css").toExternalForm());

        // Créer une nouvelle fenêtre
        Stage calendarStage = new Stage();
        calendarStage.setTitle("Calendar");  // Vous pouvez définir le titre de la nouvelle fenêtre ici
        calendarStage.setScene(calendarScene);

        // Charger le contrôleur de calendrier et ajouter la vue du calendrier à son conteneur
        Calendar calendarController = loader.getController();
        calendarController.calendarPane.getChildren().add(new FullCalendarView(YearMonth.now(), ss).getView());

        // Afficher la nouvelle fenêtre
        calendarStage.show();
    }

    public void initData(User user) {
        this.user = user;
//        username.setText(user.getUsername());
//        role.setText(user.getRole());
//        try {
//            Image image = new Image(new ByteArrayInputStream(user.getProfilePicture()));
//            imageUser.setImage(image);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}

