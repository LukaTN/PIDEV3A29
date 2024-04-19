package com.example.gestionconference.Controllers.SessionControllers;


import com.example.gestionconference.Controllers.Sponsoring.ViewRejectedSponsors;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSessionController implements Initializable {

    @FXML
    private TextField SessionNameTF;

    @FXML
    private TextArea SessionSubjectTF;

    @FXML
    private Spinner<Integer> endHours;

    @FXML
    private Spinner<Integer> endMinutes;

    @FXML
    private Spinner<Integer> startHours;

    @FXML
    private Spinner<Integer> startMinutes;

    @FXML
    private Text errorMsg;

    @FXML
    private Button stepConference;

    @FXML
    private Button stepParticipant;

    @FXML
    private Button stepSessions;

    @FXML
    private Button stepSponsoring;

    @FXML
    private Label sessionNumberOutput;

    @FXML
    private Label topicNumberOutput;

    @FXML
    private TextField SpeakerNameTF;

    @FXML
    private TextArea TopicDescriptionTF;

    @FXML
    private TextField TopicNameTF;

    private int conferenceId;

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }
    int SessionNumber=1;
    int TopicNumber=1;

    List<Session> sessionToAdd = new ArrayList<>();
    List<Topic> topicToAdd = new ArrayList<>();

    SessionServices ss = new SessionServices();
    TopicServices ts = new TopicServices();
    void setValueFactoryPrime(Spinner<Integer> spinner , int max , int initValue){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(initValue, max, initValue);
        spinner.setValueFactory(valueFactory);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setValueFactoryPrime(startHours,24,0);
        setValueFactoryPrime(endHours,24,0);
        setValueFactoryPrime(startMinutes,58,0);
        setValueFactoryPrime(endMinutes,58,0);

        stepConference.setOpacity(0.5);
        stepParticipant.setOpacity(0.5);
        stepSponsoring.setOpacity(0.5);


    }





    @FXML
    void onNextSession(ActionEvent event)  {


        LocalTime startTime = LocalTime.of(startHours.getValue(), startMinutes.getValue());
        LocalTime endTime = LocalTime.of(endHours.getValue(), endMinutes.getValue());
        //Controle de Saisie
        if (SessionNameTF.getText().isEmpty()) {
            errorMsg.setText("Session name is Empty");
        } else if(!SessionNameTF.getText().matches("[a-zA-Z ]*")||(SessionNameTF.getText().length() > 30)||(SessionNameTF.getText().length() < 3)){
            errorMsg.setText("Please verify Session name");
        }else if(SessionNameTF.getText().length() > 30){
            errorMsg.setText("Please verify Session name");
        } else if (startTime.isAfter(endTime) ) {
            errorMsg.setText("Make sure Start time is after the end Time");
        } else if (TopicNumber==1) {
            errorMsg.setText("You need to add at least 1 Topic");
        } else {

            //Add Session to list
            System.out.println(conferenceId);
            Session s = new Session(SessionNameTF.getText(), startTime, endTime, conferenceId);
            s.setTopicList(topicToAdd);
            sessionToAdd.add(s);

            //clear outputs Topic and Session
            SessionNumber++;
            sessionNumberOutput.setText(String.valueOf(SessionNumber));

            setValueFactoryPrime(startHours,24,endHours.getValue());
            setValueFactoryPrime(endHours,24,endHours.getValue());
            setValueFactoryPrime(startMinutes,58,endMinutes.getValue());
            setValueFactoryPrime(endMinutes,58,endMinutes.getValue());
            errorMsg.setText("");
            SessionNameTF.setText("");

            TopicNumber=1;
            topicNumberOutput.setText(String.valueOf(TopicNumber));

            SpeakerNameTF.setText("");
            TopicNameTF.setText("");
            TopicDescriptionTF.setText("");

        }
    }




    @FXML
    void onNextTopic(ActionEvent event)  {

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


            //add topic to list
            Topic t = new Topic(SpeakerNameTF.getText(),TopicNameTF.getText(),TopicDescriptionTF.getText());
            topicToAdd.add(t);

            //clean outputs
            errorMsg.setText("");
            TopicNumber++;
            topicNumberOutput.setText(String.valueOf(TopicNumber));

            TopicNameTF.setText("");
            TopicDescriptionTF.setText("");
        }

    }


    @FXML
    void AddSessions(ActionEvent event)  {
        if(SessionNumber!=1){
            try {
                //add session data to database
                ss.addSessions(sessionToAdd);


                ts.addTopics(topicToAdd);

                //clear list
                sessionToAdd.clear();
                topicToAdd.clear();

                //clear session output
                SessionNumber=1;
                sessionNumberOutput.setText(String.valueOf(SessionNumber));

                SessionNameTF.setText("");
                setValueFactoryPrime(startHours,24,0);
                setValueFactoryPrime(endHours,24,0);
                setValueFactoryPrime(startMinutes,58,0);
                setValueFactoryPrime(endMinutes,58,0);

                //clear topic output
                TopicNumber=1;
                topicNumberOutput.setText(String.valueOf(TopicNumber));

                SpeakerNameTF.setText("");
                TopicNameTF.setText("");
                TopicDescriptionTF.setText("");

                    // Load the interface for viewing accepted sponsors
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/Sponsoring/ViewSponsor.fxml"));
                    Parent root = loader.load();

                    // Add the AddSponsor scene to the current scene
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) SessionNameTF.getScene().getWindow(); // Get the current stage
                    stage.setScene(scene);
                    stage.show();


            } catch (Exception e) {
                errorMsg.setText("Error adding session: " + e.getMessage());
            }
        }else {
            errorMsg.setText("Add at least one Session");
        }
        System.out.println("zffzf");
    }

}