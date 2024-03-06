package com.example.gestionconference.Controllers.PresenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.PresenceModels.Presence;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import com.example.gestionconference.Services.PresenceServices.CardService;
import com.example.gestionconference.Services.UserServices.UserService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CardControllers implements Initializable {

    @FXML
    private TableColumn<Presence, Presence> ActionColumn;

    @FXML
    private TableColumn<Presence, String> CardColumn;

    @FXML
    private TableView<Presence> CardParticipantTable;

    @FXML
    private TableColumn<Presence, String> ParticipantColumn;

    @FXML
    private TableColumn<User, String> ParticipantStatusC;

    @FXML
    private TableColumn<User, String> ParticipantNameC;

    @FXML
    private TableColumn<User, String> ParticipantEmailC;

    @FXML
    private TableColumn<User, Integer> ParticipantNumberC;

    @FXML
    private TableView<User> ParticipantTable;

    @FXML
    private StackedBarChart<String, Number> DashbordSC;

    @FXML
    private Tab tabFour;

    @FXML
    private Tab tabOne;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabThree;

    @FXML
    private Tab tabTwo;

    int presence = 0;
    @FXML
    private Label AvailableCardT;

    @FXML
    private Label timeLabel;

    @FXML
    private Label AvailableParticipantT;

    @FXML
    private Label tester;

    @FXML
    private Label dateLabel;

    @FXML
    private Text statusT;

    private static final String API_URL = "http://worldtimeapi.org/api/ip";


    private final ObservableList<Presence> data = FXCollections.observableArrayList();
    private final ObservableList<User> dataParticipant = FXCollections.observableArrayList();

    private final CardService cs = new CardService();
    private final UserService us = new UserService();
    private final ConferenceServices conf = new ConferenceServices();
    private final LieuServices lieu = new LieuServices();
    private ObservableList<User> conferenceList = FXCollections.observableArrayList();
    String testerr="0";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::updateTime, 0, 1, TimeUnit.SECONDS);

        Thread updateUidThread = getUidThread();
        updateUidThread.start();

        Thread uidd =  getUidThreadd();
        uidd.start();
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    switch (newValue.getText()) {
                        case "Card Management":
                            Thread updateUidThread = getUidThread();
                            updateUidThread.start();
                            break;
                        case "Test":
                            Thread updateParticipantThread = getParticipantThread();
                            updateParticipantThread.start();
                            try {
                                update();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            break;
                        case "Sessions":
                            System.out.println("Selected tab: " + "sesion");

                            break;
                        case "Live":
                            System.out.println("Selected tab: " + "live");
                            statusT.setText("still");
                            break;
                        // add more cases as needed
                        default:
                            // default code block
                    }
                }



            }
        });







    }


    private Thread getParticipantThread() {
        Thread updateUidThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100); // Adjust the update interval as needed
                    presence=0;

                    Platform.runLater(() -> {

                    ParticipantNameC.setCellValueFactory(new PropertyValueFactory<>("username"));
                    ParticipantNumberC.setCellValueFactory(new PropertyValueFactory<>("phone"));
                    ParticipantEmailC.setCellValueFactory(new PropertyValueFactory<>("mail"));
                        ParticipantStatusC.setCellValueFactory( cellData -> {

                            String presenceState = "Absent";
                            try {

                                if (cs.participantPresence(cellData.getValue().getId(),"test2")){
                                    presence++;
                                    tester.setText(presence/2+"/"+lieu.getLieuByid(conf.getConferenceByName("test2").get(0).getConferenceLocation()).getCapacity());
                                    presenceState = "Present" ;

                                }


                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            return new SimpleStringProperty(presenceState);
                        });



                        try {
                            conferenceList.clear();
                            conferenceList.addAll(us.getAll());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        ParticipantTable.setItems(conferenceList);
                    });
                } catch (InterruptedException e) {
                    // Handle interruption if needed
                    e.printStackTrace();
                }
            }
        });
        updateUidThread.setDaemon(true); // Set the thread as daemon if needed
        return updateUidThread;
    }


    private void updateTime() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            String datetime = jsonObject.getString("utc_datetime");

            // Parse datetime into separate time and date parts
            String[] parts = datetime.split("T");
            String date = parts[0];
            String time = parts[1].substring(0, 8); // Get only the HH:mm:ss part

            // Adjust for time zone offset
            String timezone = jsonObject.getString("timezone");
            TimeZone tz = TimeZone.getTimeZone(timezone);
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            dateFormat.setTimeZone(tz);
            time = dateFormat.format(new Date());

            String finalTime = time;
            Platform.runLater(() -> {
                timeLabel.setText( finalTime);
                dateLabel.setText( date);
            });

        } catch (IOException e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                timeLabel.setText("Error fetching time");
                dateLabel.setText("");
            });
        }
    }

    private Thread getUidThread() {
        Thread updateUidThread = new Thread(() -> {
            while (true) {
                try {
                    //Thread.sleep(1); // Adjust the update interval as needed
                    ObservableList<Presence> cardList = cs.getAllTopics();

                    if (cardList.size() != data.size()) {
                        Platform.runLater(() -> {
                            data.clear();
                            data.addAll(cardList);
                            int availableCardNbr = 0;
                            for (Presence card : cardList) {
                                if (card.getIdParticipant() == 0) {
                                    availableCardNbr++;
                                }
                            }
                            AvailableCardT.setText(availableCardNbr + "/" + cardList.size());

                            try {
                                ObservableList<User> participantList = cs.getAllParticipantNonRegistered();
                                dataParticipant.clear();

                                dataParticipant.addAll(participantList);

                                try {
                                    ObservableList<User> allParticipantList = us.getAll();
                                    AvailableParticipantT.setText(participantList.size()+"/"+allParticipantList.size());
                                    // Create series for the chart

                                    CardColumn.setCellValueFactory(cellData -> cellData.getValue().uidProperty());
                                    ParticipantColumn.setCellFactory(col -> new TableCell<>() {
                                        public final ComboBox<String> comboBox = new ComboBox<>();

                                        {
                                            comboBox.setOnAction(event -> {
                                                String newValue = comboBox.getSelectionModel().getSelectedItem();
                                                if (newValue != null && getTableRow() != null && getTableRow().getItem() != null) {
                                                    Presence selectedPresence = getTableRow().getItem();
                                                    selectedPresence.setIdParticipant(dataParticipant.stream()
                                                            .filter(participant -> newValue.equals(participant.getUsername()))
                                                            .mapToInt(User::getId)
                                                            .findFirst()
                                                            .orElse(-1));

                                                    try {
                                                        cs.updateCard(selectedPresence);
                                                        data.clear();
                                                    } catch (SQLException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        protected void updateItem(String item, boolean empty) {
                                            super.updateItem(item, empty);
                                            comboBox.getStyleClass().add("my-combobox");
                                            comboBox.setItems(FXCollections.observableArrayList(
                                                    dataParticipant.stream().map(User::getUserName).collect(Collectors.toList())));

                                            if (empty) {
                                                setGraphic(null);
                                            } else {
                                                Presence selectedPresence = getTableView().getItems().get(getIndex());
                                                if (selectedPresence.getIdParticipant() == 0) {
                                                    comboBox.setPromptText("Select Participant");
                                                } else {

                                                    comboBox.setPromptText(allParticipantList.stream()
                                                            .filter(participant -> participant.getId() == selectedPresence.getIdParticipant())
                                                            .map(User::getUserName)
                                                            .findFirst()
                                                            .orElse(null));
                                                }
                                                setGraphic(comboBox);
                                            }
                                        }
                                    });

                                    ActionColumn.setCellFactory(col -> new TableCell<>() {
                                        private final Button deleteButton = new Button("Delete");

                                        {
                                            deleteButton.setOnAction(event -> {
                                                Presence presence = getTableView().getItems().get(getIndex());

                                                try {
                                                    cs.deleteCard(presence.getUid());
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            });
                                        }

                                        @Override
                                        protected void updateItem(Presence item, boolean empty) {
                                            deleteButton.getStyleClass().add("my-deleteButton");

                                            super.updateItem(item, empty);
                                            if (empty) {
                                                setGraphic(null);
                                            } else {
                                                setGraphic(deleteButton);
                                            }
                                        }
                                    });

                                    CardParticipantTable.setItems(data);
                                   // data.addAll(cardList);

                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        updateUidThread.setDaemon(true);
        return updateUidThread;
    }

    private void tester(){



    }


    private Thread getUidThreadd() {

        Thread updateUidThreadd = new Thread(() -> {

            while (true) {
                try {
                    // Specify the URL of your ESP32 endpoint
                    URL url = new URL("http://192.168.231.134/rfid");

                    // Open a connection to the URL
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Read the response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    String test = response.toString();
                    String test2 = test.substring(test.indexOf(",") + 1);
                    if (!response.toString().isEmpty()) {
                        if (!testerr.equals(test2)) {
                            System.out.println("Response from ESP32: " + response.toString());
                            Presence newPresence;
                            newPresence = cs.addCard(test.substring(0,test.indexOf(",")));
                            if (newPresence!=null){
                                int pair = newPresence.getPresenceStatus()%2;
                                if(pair==1){
                                    statusT.setText("in");
                                }else {
                                    statusT.setText("out");
                                }
                            }
                            testerr = test2;                        }

                    }

                    // Close the connection
                    conn.disconnect();

                    // Sleep for 1 second before making the next request
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        updateUidThreadd.setDaemon(true);
        return updateUidThreadd;
    }


    void update() throws SQLException {

    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    series1.setName("Series 1");

    // Add data to the series
    series1.getData().add(new XYChart.Data<>("Category 1", 3));
    series1.getData().set(0,new XYChart.Data<>("Category 1", lieu.getLieuByid(conf.getConferenceByName("test2").get(0).getConferenceLocation()).getCapacity()));

    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    series2.setName("Series 2");
    series2.getData().clear();
    // Add data to the series
    series2.getData().add(new XYChart.Data<>("Category 1", 18));
    series2.getData().set(0,new XYChart.Data<>("Category 1", presence));

    DashbordSC.getData().clear();
    // Add series to the StackedBarChart
    DashbordSC.getData().addAll(series1, series2);
}

    @FXML
    void onParticipantGet(ActionEvent event)  {}
}
