package com.example.gestionconference.Controllers.SessionControllers;

import com.example.gestionconference.Models.ConferenceModels.Conference;
import com.example.gestionconference.Models.ConferenceModels.ConferenceType;
import com.example.gestionconference.Models.SessionModels.Session;
import com.example.gestionconference.Models.SessionModels.Topic;
import com.example.gestionconference.Services.SessionServices.SessionServices;
import com.example.gestionconference.Services.SessionServices.TopicServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SessionController  implements Initializable {


    @FXML
    private AnchorPane EditSessionViewA;

    @FXML
    private Text EndTimeT;

    @FXML
    private HBox GetSessionView;

    @FXML
    private Text PresenceNumberT;

    @FXML
    private Label SessioNumberT;

    @FXML
    private Button SessionDeleteButton;

    @FXML
    private Button TopicDeleteButton;

    @FXML
    private Text SessionNameT;

    @FXML
    private TextField SessionNameTF;


    @FXML
    private Text SpeakerNameT;

    @FXML
    private Text StartTimeT;

    @FXML
    private Label TopicCurrentNumberT;

    @FXML
    private Text TopicDescriptionT;

    @FXML
    private Text TopicNameT;

    @FXML
    private Text TopicsNumberT;

    @FXML
    private Spinner<Integer> endHours;

    @FXML
    private Spinner<Integer> endMinutes;

    @FXML
    private Spinner<Integer> startHours;

    @FXML
    private Spinner<Integer> startMinutes;


    @FXML
    private AnchorPane EditTopicViewA;


    @FXML
    private AnchorPane GetTopicViewA;


    @FXML
    private TextField SpeakerNameTF;


    @FXML
    private TextArea TopicDescriptionTF;


    @FXML
    private TextField TopicNameTF;

    @FXML
    private Text errorMsg;


    @FXML
    private ImageView editImage;


    @FXML
    private ImageView editImage1;


    @FXML
    private Button doneSessionB;

    @FXML
    private Button doneTopicB;
    @FXML
    private Button editSessionB;

    @FXML
    private Button editTopicB;

    @FXML
    private Button NextSessionB;

    @FXML
    private Button NextTopicB;

    @FXML
    private Button BackTopicB;

    @FXML
    private Button BackSessionB;


    List<Session> sessionList = new ArrayList<>();
    Boolean editView = false;
    Boolean editTopicView = false;

    SessionServices ss = new SessionServices();
    TopicServices ts = new TopicServices();

    int currentSession=0;
    int currentTopic=0;
    private Conference selectedConference;


    void setValueFactoryPrime(Spinner<Integer> spinner , int max ,int min , int initValue){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initValue);
        spinner.setValueFactory(valueFactory);
    }





    void setDataSession(List<Session> session,int currentSession){
        SessioNumberT.setText(String.valueOf(currentSession));

        SessionNameT.setText(session.get(currentSession).getSessionName());
        StartTimeT.setText(session.get(currentSession).getStartTime().toString());
        EndTimeT.setText(session.get(currentSession).getEndTime().toString());
        TopicsNumberT.setText(session.get(currentSession).getTopicList().size()+" Topics");
        PresenceNumberT.setText(String.valueOf(session.get(currentSession).getPresenceNbr())+" Participants");

        //edit view
        SessionNameTF.setText(session.get(currentSession).getSessionName());
        setValueFactoryPrime(startMinutes,23,0,session.get(currentSession).getStartTime().getMinute());
        setValueFactoryPrime(startHours,23,0,session.get(currentSession).getStartTime().getHour());
        setValueFactoryPrime(endMinutes,23,0,session.get(currentSession).getEndTime().getMinute());
        setValueFactoryPrime(endHours,23,0,session.get(currentSession).getEndTime().getHour());


    }


    void setDataTopic(List<Topic> topics,int currentTopic){
        TopicCurrentNumberT.setText(String.valueOf(currentTopic));
        TopicNameT.setText(String.valueOf(topics.get(currentTopic).getTopicName()));
        SpeakerNameT.setText(String.valueOf(topics.get(currentTopic).getSpeakerName()));
        TopicDescriptionT.setText(String.valueOf(topics.get(currentTopic).getTopicDescription()));


        //edit View

        TopicNameTF.setText(String.valueOf(topics.get(currentTopic).getTopicName()));
        SpeakerNameTF.setText(String.valueOf(topics.get(currentTopic).getSpeakerName()));
        TopicDescriptionTF.setText(String.valueOf(topics.get(currentTopic).getTopicDescription()));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackSessionB.setDisable(true);
        BackTopicB.setDisable(true);
        doneSessionB.setVisible(false);
        doneTopicB.setVisible(false);
        EditSessionViewA.setVisible(false);
        EditTopicViewA.setVisible(false);
        try {
            sessionList= ss.getAllSessions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDataSession(sessionList,currentSession);
        setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);

    }


    @FXML
    void onBackSession(ActionEvent event) {
        NextSessionB.setDisable(false);
        BackTopicB.setDisable(true);
        NextTopicB.setDisable(false);
        if (currentSession>0){
            currentSession--;
            setDataSession(sessionList,currentSession);

            currentTopic=0;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
            }
        if (currentSession==0) {
            BackSessionB.setDisable(true);
        }

    }

    @FXML
    void onBackTopic(ActionEvent event) {
        NextTopicB.setDisable(false);
        if(currentTopic>0){
            currentTopic--;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
              }
        else if(sessionList.get(currentSession).getTopicList().size()==1){
            NextTopicB.setDisable(true);
        }

    }

    @FXML
    void onDeleteSession(ActionEvent event) throws SQLException {
        if(sessionList.size()!=1){
            //delete from database
            ss.deleteSession(sessionList.get(currentSession).getId());
            //update front data
            sessionList= ss.getAllSessions();
            //update output
            if (currentSession==0){
                currentSession++;
            } else  {
                currentSession--;
            }

            setDataSession(sessionList,currentSession);
            setDataTopic(sessionList.get(currentSession).getTopicList(),0);
        } else  {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You Can't Have a Conference without Session \n I suggest you edit session");
            alert.initOwner(SessionDeleteButton.getScene().getWindow()); // Set the owner of the dialog
            alert.showAndWait();

        }

    }

    @FXML
    void onDeleteTopic(ActionEvent event) throws SQLException {
        if(sessionList.get(currentSession).getTopicList().size()!=1){
            //delete from database
            ts.deleteTopic(sessionList.get(currentSession).getTopicList().get(currentTopic).getId());
            //update front data
            sessionList= ss.getAllSessions();
            //update output
            currentTopic=0;
            TopicsNumberT.setText(String.valueOf(sessionList.get(currentSession).getTopicList().size())+"Topics");
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
        } else  {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You Can't Have a Session without Topics \n I suggest you edit this topic");
            alert.initOwner(SessionDeleteButton.getScene().getWindow()); // Set the owner of the dialog
            alert.showAndWait();

        }

    }

    @FXML
    void onEditSession(ActionEvent event) {
        GetSessionView.setVisible(editView);
        EditSessionViewA.setVisible(!editView);
        SessionDeleteButton.setVisible(editView);
        doneSessionB.setVisible(!editView);
        editSessionB.setVisible(editView);
        editView=!editView;

        setDataSession(sessionList,currentSession);
        errorMsg.setText("");
    }

    @FXML
    void onEditTopic(ActionEvent event) {

        GetTopicViewA.setVisible(editTopicView);
        EditTopicViewA.setVisible(!editTopicView);
        TopicDeleteButton.setVisible(editTopicView);
        editTopicB.setVisible(editTopicView);
        doneTopicB.setVisible(!editTopicView);
        editTopicView=!editTopicView;
        errorMsg.setText("");

    }
    @FXML
    void onDoneTopic(ActionEvent event){
        GetTopicViewA.setVisible(editTopicView);

        EditTopicViewA.setVisible(!editTopicView);
        TopicDeleteButton.setVisible(editTopicView);
        doneTopicB.setVisible(editView);
        editTopicB.setVisible(editTopicView);
        editTopicView=!editTopicView;
        errorMsg.setText("");

    }

    @FXML
    void onDoneSession(ActionEvent event){
        GetSessionView.setVisible(editView);
        EditSessionViewA.setVisible(!editView);
        SessionDeleteButton.setVisible(editView);
        doneSessionB.setVisible(!editView);
        editSessionB.setVisible(editView);
        editView=!editView;
        errorMsg.setText("");
    }

    @FXML
    void onNextSession(ActionEvent event) {
        BackSessionB.setDisable(false);
        BackTopicB.setDisable(true);
        NextTopicB.setDisable(false);
        if (currentSession<sessionList.size()-2){
            currentSession++;

            setDataSession(sessionList,currentSession);
            currentTopic=0;
             setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);

        }else {
            currentSession++;
            setDataSession(sessionList,currentSession);
            currentTopic=0;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
            NextSessionB.setDisable(true);
        }
    }

    @FXML
    void onNextTopic(ActionEvent event) {
        BackTopicB.setDisable(false);
        if(currentTopic<sessionList.get(currentSession).getTopicList().size()-2){
            currentTopic++;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
              }
        else if(sessionList.get(currentSession).getTopicList().size()==1){
            NextTopicB.setDisable(true);
            BackTopicB.setDisable(true);
        }
        else {
            currentTopic++;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
            NextTopicB.setDisable(true);
        }
    }

    @FXML
    void onSaveSession(ActionEvent event) throws SQLException {

        LocalTime startTime = LocalTime.of(startHours.getValue(), startMinutes.getValue());
        LocalTime endTime = LocalTime.of(endHours.getValue(), endMinutes.getValue());
        boolean prob=false;
        boolean prob2=false;
        for(int i=0;i < currentSession; i++){
            System.out.println(sessionList.get(i).getEndTime());
            if (!startTime.isAfter(sessionList.get(i).getEndTime())){
                prob=true;
            }
        }

        for(int i=currentSession+1;i < sessionList.size(); i++){
            if (endTime.isAfter(sessionList.get(i).getStartTime())){
                prob2=true;
            }
        }

        if (SessionNameTF.getText().isEmpty()) {
            errorMsg.setText("Session name is Empty");
        } else if(!SessionNameTF.getText().matches("[a-zA-Z ]*") ||
                SessionNameTF.getText().length() > 30 ||
                SessionNameTF.getText().length() < 3) {
            errorMsg.setText("Please verify Session name");
        }
         else if (startTime.isAfter(endTime)) {
            errorMsg.setText("Make sure Start time is after the end Time");
        }else if (prob) {
            errorMsg.setText("Make sure Start time is after the end Time of previous session");

        }
        else if (prob2) {
            errorMsg.setText("Make sure End time doesn't interface with the next session");

        }
        else {

            //Add Session to list

            sessionList.get(currentSession).setSessionName(SessionNameTF.getText());
            sessionList.get(currentSession).setStartTime(LocalTime.of(startHours.getValue(), startMinutes.getValue()));
            sessionList.get(currentSession).setEndTime(LocalTime.of(endHours.getValue(), endMinutes.getValue()));
            errorMsg.setText("");
            ss.updateSession(sessionList.get(currentSession));
        }

    }

    @FXML
    void  onSaveTopic(ActionEvent event) throws SQLException {

        //Control de Saisie
        if (SpeakerNameTF.getText().isEmpty()){
            errorMsg.setText("Speaker name is Empty");
        } else if (((!SpeakerNameTF.getText().matches("[a-zA-Z ]*")||(SpeakerNameTF.getText().length() > 30)||(SpeakerNameTF.getText().length() < 3)))) {
            errorMsg.setText("Please verify Speaker name");
        }else if (TopicNameTF.getText().isEmpty()) {
            errorMsg.setText("Topic name is Empty");
        }else if (((!TopicNameTF.getText().matches("[a-zA-Z ]*")||(TopicNameTF.getText().length() > 30)||(TopicNameTF.getText().length() < 3)))) {
            errorMsg.setText("Please verify Topic name");
        }else if ((!TopicDescriptionTF.getText().matches("[a-zA-Z\n- ]*")||(TopicDescriptionTF.getText().length() > 300)||(TopicDescriptionTF.getText().length() < 10))) {
            errorMsg.setText("Please verify Topic Description name");
        }else if (TopicDescriptionTF.getText().isEmpty()) {
            errorMsg.setText("Topic Description is Empty");
        }else {

            sessionList.get(currentSession).getTopicList().get(currentTopic).setTopicName(TopicNameTF.getText());
            sessionList.get(currentSession).getTopicList().get(currentTopic).setSpeakerName(SpeakerNameTF.getText());
            sessionList.get(currentSession).getTopicList().get(currentTopic).setTopicDescription(TopicDescriptionTF.getText());


            //clean outputs
            errorMsg.setText("");

            ts.updateTopic(sessionList.get(currentSession).getTopicList().get(currentTopic));
        }


    }
    @FXML
    void onAddSessionNav(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/SessionFXML/AddSession.fxml"));
            Parent root = loader.load();

            // Get the reference to the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create the scene using the loaded FXML
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);
            stage.setTitle("Confera");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("uznjf,");
    }

    @FXML
    void onDash(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/PresenceFXML/CardManagement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("confera");
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) doneSessionB.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void setSelectedConference(Conference selectedConference) {
        this.selectedConference = selectedConference;
        sessionList.addAll(ss.getSessionsByConferenceId(selectedConference.getId()));

    }



}
