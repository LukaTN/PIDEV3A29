package com.example.gestionconference.Controllers.PresenceControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.Lieu;
import com.example.gestionconference.Models.PresenceModels.Presence;
import com.example.gestionconference.Models.SessionModels.Session;
import com.example.gestionconference.Models.UserModels.User;
import com.example.gestionconference.Services.ConferenceService.ConferenceServices;
import com.example.gestionconference.Services.ConferenceService.LieuServices;
import com.example.gestionconference.Services.PresenceServices.CardService;
import com.example.gestionconference.Services.SessionServices.SessionServices;
import com.example.gestionconference.Services.UserServices.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.JSONObject;

import javax.security.auth.callback.Callback;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.apache.commons.math3.ml.neuralnet.FeatureInitializerFactory.function;
import static org.apache.http.conn.params.ConnManagerParams.setTimeout;

public class CardControllers implements Initializable {

    @FXML
    private TableColumn<Presence, Presence> ActionColumn;

    @FXML
    private TableColumn<Presence, String> CardColumn;

    @FXML
    private TableView<Presence> CardParticipantTable;

    @FXML
    private Text statusTimeT;

    @FXML
    private Text statusTimeT2;

    @FXML
    private Text NumberT;

    @FXML
    private Text ParticipantNameT;

    @FXML
    private Text TimeSpentT;

    @FXML
    private Label ConfNameT;

    @FXML
    private Label PresenceT;


    @FXML
    private Label SessionNameT;

    @FXML
    private Label OrganizationNameT1;

    @FXML
    private AnchorPane inAnchor;

    @FXML
    private AnchorPane outAnchor;

    @FXML
    private AnchorPane waiting;

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
    private StackedBarChart<String, Number> sessionsChart;
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
    private Label dateLabel;

    @FXML
    private Text statusT;
    @FXML
    private Text presenceT;

    @FXML
    private ImageView UserImage;

    public ComboBox<String> ConferenceListCB;
    public ComboBox<String> ConferenceListCB1;

    private final ObservableList<Presence> data = FXCollections.observableArrayList();
    private final ObservableList<User> dataParticipant = FXCollections.observableArrayList();
    private final CardService cs = new CardService();
    private final UserService us = new UserService();
    private final ConferenceServices conf = new ConferenceServices();
    private final LieuServices lieu = new LieuServices();
    private ObservableList<User> conferenceList = FXCollections.observableArrayList();
    Map<Integer, LocalTime> presenceTime = new HashMap<>();
    List<Integer> allTimes = new ArrayList<>();
    SessionServices ss = new SessionServices();
    Session newSession = null;


    int notifierIncr=0;

    String lastValue = "";
    String confName="Tech Conf";

    private static final String API_URL = "http://worldtimeapi.org/api/ip";
    private static final String API_URL_RFID = "http://192.168.1.18/rfid";
    private static final String API_URL_PREDICT =  "http://127.0.0.1:5000/predict";

    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    XYChart.Series<String, Number> series6 = new XYChart.Series<>();
    XYChart.Series<String, Number> series4 = new XYChart.Series<>();
    XYChart.Series<String, Number> series5 = new XYChart.Series<>();
    XYChart.Series<String, Number> series3 = new XYChart.Series<>();
    String tabValue="";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Listen To the Pin
        Thread uid =  getUidThread();
        uid.start();

        for (Tab tab : tabPane.getTabs()) {
            tab.setStyle(" ");
        }
        //Listen To Tab change
        tabPane.getTabs().get(0).setStyle(tabPane.getTabs().get(0).getStyle() + "-fx-background-radius: 15px 0 0 0;"); // Apply border radius to the first tab
        tabPane.getTabs().get(tabPane.getTabs().size() - 1).setStyle(tabPane.getTabs().get(tabPane.getTabs().size() - 1).getStyle() + "-fx-background-radius:  0 15px 0 0;"); // Apply border radius to the first tab

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    switch (newValue.getText()) {
                        case "Card Management":
                            tabValue="";

                            break;
                        case "Presence":
                            tabValue="";
                            presence=0;
                            try {
                                updatePresenceTable();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "Sessions":
                            tabValue="";
                            System.out.println("Selected tab: " + "sesion");
                            break;
                        case "Live":
                            outAnchor.setVisible(false);
                            tabValue="Live";
                            try {
                                updateLive(null);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Selected tab: " + "live");
                            waiting.setVisible(true);
                            break;
                        // add more cases as needed
                        default:
                            // default code block
                    }
                }
            }
        });

        //Initiate Data from DataBase
        try {
            updateCardList();
            updateSessionDash();
             ConferenceListCB1.setItems(FXCollections.observableArrayList(
              conf.getAllConferences().stream().map(Conference::getName).collect(Collectors.toList())));
            ConferenceListCB.setItems(FXCollections.observableArrayList(
                    conf.getAllConferences().stream().map(Conference::getName).collect(Collectors.toList())));
            confName=conf.getAllConferencesDone().get(0).getName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        //Initiate Table Presence
        series1.setName("Presence");
        series1.getData().add(new XYChart.Data<>("Participants", 30));
        series2.setName("Place Available");
        series2.getData().add(new XYChart.Data<>("Participants", 1));
        series6.setName("Place Available");
        series6.getData().add(new XYChart.Data<>("Participants", 0));
        DashbordSC.getData().addAll(series1,series6,series2);
        DashbordSC.setLegendVisible(false);



        //Initiate Time
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::updateTime, 0, 1, TimeUnit.SECONDS);

    }
    //Consume API and update time in live
    private void updateTime() {
        new Thread(() -> {
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
                SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                apiDateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure we're parsing in UTC
                Date apiDateTime = apiDateFormat.parse(datetime);

                // Add one hour
                apiDateTime.setTime(apiDateTime.getTime() + 3600 * 1000);

                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                dateFormat.setTimeZone(tz);
                time = dateFormat.format(apiDateTime);

                String finalTime = time;

                Platform.runLater(() -> {
                    // Update UI components
                    timeLabel.setText(finalTime);
                    dateLabel.setText(date);
                });

            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    timeLabel.setText("Error fetching time");
                    dateLabel.setText("");
                });
            } catch (ParseException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    timeLabel.setText("Error parsing time");
                    dateLabel.setText("");
                });
            }
        }).start();
    }


    //Consume API RFID and notify the changes
    private Thread getUidThread() {

        Thread updateUidThread = new Thread(() -> {

            while (true) {
                try {
                    // Specify the URL of your ESP32 endpoint
                    URL url = new URL(API_URL_RFID);

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
                    Presence newPresence;
                    Presence newerPresence;
                    //Check if initialization
                    if(notifierIncr==0){
                        lastValue =response.toString();
                        notifierIncr++;
                    }

                        if (!lastValue.equals(response.toString())) {
                            lastValue =response.toString();
                            System.out.println(notifierIncr+" : Response from ESP32: " + lastValue);


                            //notif from desktop
                            if (!lastValue.substring(0, lastValue.indexOf(",")).equals("00000000")) {
                                //enrigistré la nouvelle carte
                                newPresence = cs.addCard(lastValue.substring(0,lastValue.indexOf(",")));

                                //si la carte est enrigistré la premier fois update liste des carte si non update presence
                                if(newPresence!=null){
                                    updatePresenceTable();
                                    updateLive(newPresence);

                                }else {
                                    updateCardList();
                                }
                            }else{

                                //notif from web
                                newerPresence = cs.getLastUid();
                                if(newerPresence!=null){
                                    updatePresenceTable();
                                    updateLive(newerPresence);
                                    updateCardList();

                                }else {

                                    updateCardList();

                                }
                            }



                        }


                    // Close the connection
                    conn.disconnect();
                    // Sleep for 1 second before making the next request
                   // Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        updateUidThread.setDaemon(true);
        return updateUidThread;
    }

    //Consume API of Ai and send to the database
    public int getAiValueStability(List<Integer> allTimes){

        String url =API_URL_PREDICT;
        if(allTimes.size()==3){
            try {
                String data = "{\"data\": [";

                for (int i = 0; i < allTimes.size(); i++) {
                    data += allTimes.get(i);
                    if (i < allTimes.size() - 1) {
                        data += ",";
                    }
                }

                data += ",0]}";

                // Create connection
                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                // Set request method
                connection.setRequestMethod("POST");

                // Set request headers
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                // Enable writing data to the connection
                connection.setDoOutput(true);

                // Write data to the connection
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();



                // Read response from the connection
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

                System.out.println("Response: " + response.toString());

                return jsonObject.get("feedback").getAsInt();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;

    }



    void updateSomme()  {

        try {
            ObservableList<Presence> cardList = cs.getAllTopics();
            ObservableList<User> participantList = cs.getAllParticipantNonRegistered();
            int availableCardNbr = (int) cardList.stream().filter(card -> card.getIdParticipant() == 0).count();
            AvailableCardT.setText(availableCardNbr + "/" + cardList.size());
            ObservableList<User> allParticipantList = us.getAll();
            AvailableParticipantT.setText(participantList.size() + "/" + allParticipantList.size());

            dataParticipant.clear();
            dataParticipant.addAll(participantList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void setupTableView()  {

        try {
            updatePresenceTable();
            CardParticipantTable.getItems().clear();
            updateSomme();
            updateCardList(); // You might need to handle SQLException here
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    void updateCardList() throws SQLException {
        ObservableList<Presence> cardList = cs.getAllTopics();
        ObservableList<User> allParticipantList = us.getAll();

            Platform.runLater(() -> {
                data.clear();
                data.addAll(cardList);
                updateSomme();
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

                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    setupTableView();



                                }
                            });
                        }

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                            } else {
                                Presence selectedPresence = getTableView().getItems().get(getIndex());
                                comboBox.getStyleClass().add("my-combobox");

                                comboBox.setItems(FXCollections.observableArrayList(
                                        dataParticipant.stream().map(User::getUserName).collect(Collectors.toList())));

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

                    ActionColumn.setCellFactory(col -> new TableCell<Presence, Presence>() {
                        private final Button deleteButton = new Button("Delete");

                        {
                            deleteButton.setOnAction(event -> {
                                Presence presence = getTableView().getItems().get(getIndex());

                                try {
                                    cs.deleteCard(presence.getUid());
                                    setupTableView();
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

            });

    }

    void updatePresenceTable() throws SQLException {
        int capacity = lieu.getLieuByid(conf.getConferenceByName(confName).getConferenceLocation()).getCapacity();

        presence=0;
        ParticipantNameC.setCellValueFactory(new PropertyValueFactory<>("username"));
        ParticipantNumberC.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ParticipantEmailC.setCellValueFactory(new PropertyValueFactory<>("mail"));
        ParticipantStatusC.setCellFactory(column -> {
            return new TableCell<User, String>() {
                final HBox hBox = new HBox();
                final Text text = new Text();

                {
                    text.setStyle("-fx-font-size: 14px;");
                    hBox.getChildren().add(text);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setPadding(new Insets(5));
                    setGraphic(hBox);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty ) {
                        setText(null);
                        setGraphic(null);
                    } else {

                        User user = getTableView().getItems().get(getIndex());
                        boolean isPresent = false;
                        try {
                            isPresent = cs.participantPresence(user.getId(), confName);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        if (isPresent) {
                            presence++;

                                text.setText("Present");
                                text.setFill(Color.GREEN);
                                hBox.setStyle("-fx-background-color: #D8ECDA; -fx-background-radius: 20;");
                            } else {
                                text.setText("Absent");
                            text.setFill(Color.rgb(160, 14, 40));

                            hBox.setStyle("-fx-background-color: #EC565C; -fx-background-radius: 20;");
                            }
                            presenceT.setText(presence+"/"+capacity);
                            series1.getData().get(0).setYValue(presence);
                            //series6.getData().get(0).setYValue(0);
                            series2.getData().get(0).setYValue(capacity-presence);

                    }
                }
            };
        });



        try {
            conferenceList.clear();
            conferenceList.addAll(us.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ParticipantTable.setItems(conferenceList);
    }

    void updateLive(Presence newPresence) throws SQLException {
        LieuServices ls = new LieuServices();
        Platform.runLater(() -> {
            ConferenceServices css = new ConferenceServices();
            Conference newConf = null;
            try {
                newConf = css.getTodayConference();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (newConf != null) {
                ConfNameT.setText(newConf.getName());


                Lieu lieu = null;
                try {
                   newSession = ss.getCurrentSession(newConf);
                    lieu = ls.getLieuByid(newConf.getEmplacement());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (newSession != null) {
                    SessionNameT.setText(newSession.getSessionName());
                    PresenceT.setText(newSession.getPresenceNbr() + "/" + lieu.getCapacity());
                    OrganizationNameT1.setText(lieu.getLabel());

                    if (newPresence != null) {
                        User newUser;
                        try {
                            newUser = us.getById(newPresence.getIdParticipant());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        int pair = newPresence.getPresenceStatus() % 2;
                        ParticipantNameT.setText(newUser.getUserName());
                        if (newUser.getProfilePicture()!=null){
                            Image image = new Image(new ByteArrayInputStream(newUser.getProfilePicture()));
                            UserImage.setImage(image);
                        }

                        LocalTime tt = LocalTime.parse(timeLabel.getText());
                        statusTimeT.setText(timeLabel.getText());
                        NumberT.setText("Phone : " + newUser.getPhone());
                        if (pair == 1) {
                            waiting.setVisible(false);
                            statusT.setText("in");
                           // presenceTime.putIfAbsent(newUser.getId(), presenceTime.getOrDefault(newUser.getId(), tt));
                            inAnchor.setVisible(true);
                            outAnchor.setVisible(false);
                            TimeSpentT.setVisible(false);
                            statusTimeT2.setVisible(false);
                        } else {
                            waiting.setVisible(false);
                         //   statusTimeT.setText(presenceTime.get(newUser.getId()).toString());
                            TimeSpentT.setVisible(true);
                            statusTimeT2.setVisible(true);
                            //long minutesBetween = ChronoUnit.SECONDS.between(presenceTime.get(newUser.getId()), LocalTime.parse(timeLabel.getText()));
                            statusT.setText("out");
                            inAnchor.setVisible(false);
                            outAnchor.setVisible(true);
                           // statusTimeT2.setText(minutesBetween / 60 + "min");
                           // allTimes.add((int) minutesBetween / 60);



                        }
                    }
                } else {
                    System.out.println("No Session for Today");
                }
            } else {
                System.out.println("No Conference for Today");
            }
        });
    }


    void updateSessionDash() throws SQLException {
        sessionsChart.setAnimated(false);
        SessionServices ss = new SessionServices();
        ConferenceServices css = new ConferenceServices();
        int idConf = css.getConferenceByName(confName).getId();

        List<Session>  newSessions = ss.getSessionsByConferenceId(idConf);

       // List<Session>  newSessions = ss.getAllSessions();
        series3.setName("Considerable");

        series4.setName("Non Considerable");
        series5.setName("Session Time");
        //sessionsChart.getData().clear();
        series3.getData().clear();
        series4.getData().clear();
        series5.getData().clear();
        sessionsChart.getData().clear();


            for (int i = 0; i < newSessions.size(); i++) {
                Session session = newSessions.get(i);

                long minutesBetween = ChronoUnit.SECONDS.between(session.getStartTime(), session.getEndTime());

                if((session.getPresenceQuality()==1)&&(session.getPresenceTime()>0)){
                    series3.getData().add(new XYChart.Data<>(session.getSessionName(), session.getPresenceTime()/ session.getPresenceNbr()));
                    series4.getData().add(new XYChart.Data<>(session.getSessionName(), 0));
                    series5.getData().add(new XYChart.Data<>(session.getSessionName(), ((int)minutesBetween/60)- (session.getPresenceTime()/ session.getPresenceNbr())));


                }
                else if (session.getPresenceNbr()==0){
                    series5.getData().add(new XYChart.Data<>(session.getSessionName(), ((int)minutesBetween/60)));

                }
                else{
                    series5.getData().add(new XYChart.Data<>(session.getSessionName(), ((int)minutesBetween/60)- (session.getPresenceTime()/ session.getPresenceNbr())));
                    series3.getData().add(new XYChart.Data<>(session.getSessionName(), 0));
                    series4.getData().add(new XYChart.Data<>(session.getSessionName(), session.getPresenceTime()/ session.getPresenceNbr()));

                }
            }

            // Add data series to the chart
            sessionsChart.getData().addAll(series3,series4,series5);
            sessionsChart.setLegendVisible(false);


    }


    @FXML
    void onSelectConf(ActionEvent event) throws SQLException {
    confName = ConferenceListCB.getValue();
    presence=0;
    updatePresenceTable();
    }
    @FXML
    void onSelectConf2(ActionEvent event) throws SQLException {
        confName = ConferenceListCB1.getValue();

        updateSessionDash();

    }


}
