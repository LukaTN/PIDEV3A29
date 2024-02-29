package com.example.gestionconference.Controllers;


import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Models.Topic;
import com.example.gestionconference.Services.SessionServices;
import com.example.gestionconference.Services.TopicServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    int getTime(int a, int b) {
        return Integer.parseInt(String.format("%02d%02d", a, b)+"00");
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


        int startTime = getTime(startHours.getValue(),startMinutes.getValue());
        int endTime = getTime(endHours.getValue(),endMinutes.getValue());
        //Controle de Saisie
        if (SessionNameTF.getText().isEmpty()) {
            errorMsg.setText("Session name is Empty");

        }  else if (startTime >= endTime) {
            errorMsg.setText("Make sure Start time is after the end Time");

        } else if (TopicNumber==1) {
            errorMsg.setText("You need to add at least 1 Topic");
        } else {

            //Add Session to list
            Session s = new Session(SessionNameTF.getText(), startTime, endTime, 1);
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
        } else if (TopicNameTF.getText().isEmpty()) {
            errorMsg.setText("Topic name is Empty");
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



            } catch (Exception e) {
                errorMsg.setText("Error adding session: " + e.getMessage());
            }
        }else {
            errorMsg.setText("Add at least one Session");
        }

    }
}

