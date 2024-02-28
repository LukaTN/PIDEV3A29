package com.example.gestionconference.Controllers;

import com.example.gestionconference.Models.Session;
import com.example.gestionconference.Models.Topic;
import com.example.gestionconference.Services.SessionServices;
import com.example.gestionconference.Services.TopicServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SessionController  implements Initializable {


    @FXML
    private AnchorPane EditSessionViewA;

    @FXML
    private Text EndTimeT;

    @FXML
    private AnchorPane GetSessionDetailsA;

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
    private VBox SessionsDetailsT;

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
    private Text prompetStartMinutes;

    @FXML
    private Text prompetStartMinutes1;

    @FXML
    private Text promptStartHours;

    @FXML
    private Text promptStartHours1;

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
    private ImageView doneImage;

    @FXML
    private ImageView editImage;

    @FXML
    private ImageView doneImage1;

    @FXML
    private ImageView editImage1;





    List<Session> sessionList = new ArrayList<>();
    Boolean editView = false;
    Boolean editTopicView = false;

    SessionServices ss = new SessionServices();
    TopicServices ts = new TopicServices();

    int currentSession=0;
    int currentTopic=0;
    String convertTime(int t) {
        int m = t % 10000;
        int h = t / 10000;
        return String.format("%02d:%02d", h, m/100);
    }
    int getTime(int a, int b) {
        return Integer.parseInt(String.format("%02d%02d", a, b)+"00");
    }












    void setValueFactoryPrime(Spinner<Integer> spinner , int max ,int min , int initValue){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initValue);
        spinner.setValueFactory(valueFactory);
    }




    void setDataTopicEdit(List<Topic> topics,int currentTopic){
        TopicCurrentNumberT.setText(String.valueOf(currentTopic));

        TopicNameTF.setText(String.valueOf(topics.get(currentTopic).getTopicName()));

        SpeakerNameTF.setText(String.valueOf(topics.get(currentTopic).getSpeakerName()));
        TopicDescriptionTF.setText(String.valueOf(topics.get(currentTopic).getTopicDescription()));


    }
    void setDataSessionEdit(List<Session> session,int currentSession){
        SessionNameTF.setText(session.get(currentSession).getSessionName());
        int minminutes;
        int minhours;

        int startminutes = session.get(currentSession).getStartTime() %10000;
        int starthours = session.get(currentSession).getStartTime() /10000;
        int endminutes = session.get(currentSession).getEndTime() %10000;
        int endhours = session.get(currentSession).getEndTime() /10000;

        if(currentSession!=0){
            minminutes = session.get(currentSession-1).getEndTime() %10000;
            minhours = session.get(currentSession-1).getEndTime() /10000;
        }else {
            minminutes = endminutes;
            minhours = endhours;
        }



        System.out.println(starthours);
        setValueFactoryPrime(startMinutes,23,0,startminutes/10);
        setValueFactoryPrime(startHours,23,0,starthours);
        setValueFactoryPrime(endMinutes,23,0,endminutes/10);
        setValueFactoryPrime(endHours,23,0,endhours);


    }


    public void setDataSession(List<Session> session,int currentSession){
        SessioNumberT.setText(String.valueOf(currentSession));

        SessionNameT.setText(session.get(currentSession).getSessionName());
        StartTimeT.setText(convertTime(session.get(currentSession).getStartTime()));
        EndTimeT.setText(convertTime(session.get(currentSession).getEndTime()));
        TopicsNumberT.setText(session.get(currentSession).getTopicList().size()+" Topics");
        PresenceNumberT.setText(String.valueOf(session.get(currentSession).getPresenceNbr())+" Participants");

    }
    void setDataTopic(List<Topic> topics,int currentTopic){
        TopicCurrentNumberT.setText(String.valueOf(currentTopic));

        TopicNameT.setText(String.valueOf(topics.get(currentTopic).getTopicName()));
        SpeakerNameT.setText(String.valueOf(topics.get(currentTopic).getSpeakerName()));
        TopicDescriptionT.setText(String.valueOf(topics.get(currentTopic).getTopicDescription()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doneImage.setVisible(false);
        doneImage1.setVisible(false);
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
        if (currentSession>0){
            currentSession--;
            setDataSession(sessionList,currentSession);
            currentTopic=0;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
        }

    }

    @FXML
    void onBackTopic(ActionEvent event) {
        if(currentTopic>0){
            currentTopic--;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
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
        doneImage.setVisible(!editView);
        editImage.setVisible(editView);
        editView=!editView;
        setDataSessionEdit(sessionList,currentSession);
        setDataSession(sessionList,currentSession);
        errorMsg.setText("");
    }

    @FXML
    void onEditTopic(ActionEvent event) {

        GetTopicViewA.setVisible(editTopicView);
        EditTopicViewA.setVisible(!editTopicView);
        TopicDeleteButton.setVisible(editTopicView);
        doneImage1.setVisible(!editTopicView);
        editImage1.setVisible(editTopicView);
        editTopicView=!editTopicView;
        setDataTopicEdit(sessionList.get(currentSession).getTopicList(),currentTopic);
        setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);

    }

    @FXML
    void onNextSession(ActionEvent event) {
        if (currentSession<sessionList.size()-1){
            currentSession++;
            setDataSessionEdit(sessionList,currentSession);
            setDataSession(sessionList,currentSession);
            currentTopic=0;
            setDataTopicEdit(sessionList.get(currentSession).getTopicList(),currentTopic);
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
        }
    }

    @FXML
    void onNextTopic(ActionEvent event) {
        if(currentTopic<sessionList.get(currentSession).getTopicList().size()-1){
            currentTopic++;
            setDataTopic(sessionList.get(currentSession).getTopicList(),currentTopic);
            setDataTopicEdit(sessionList.get(currentSession).getTopicList(),currentTopic);
        }
    }

    @FXML
    void onSaveSession(ActionEvent event) throws SQLException {

        int startTime = getTime(startHours.getValue(),startMinutes.getValue());
        int endTime = getTime(endHours.getValue(),endMinutes.getValue());
        boolean prob=false;
        boolean prob2=false;
        for(int i=0;i < currentSession; i++){
            if (startTime<sessionList.get(i).getEndTime()){
                prob=true;
            }
        }

        for(int i=currentSession+1;i < sessionList.size(); i++){
            if (endTime>sessionList.get(i).getStartTime()){
                prob2=true;
            }
        }

        if (SessionNameTF.getText().isEmpty()) {
            errorMsg.setText("Session name is Empty");

        }  else if (startTime >= endTime) {
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
            sessionList.get(currentSession).setStartTime(startTime);
            sessionList.get(currentSession).setEndTime(endTime);
            errorMsg.setText("");
            ss.updateSession(sessionList.get(currentSession));
        }

    }

    @FXML
    void  onSaveTopic(ActionEvent event) throws SQLException {

        //Control de Saisie
        if (SpeakerNameTF.getText().isEmpty()){
            errorMsg.setText("Speaker name is Empty");
        } else if (TopicNameTF.getText().isEmpty()) {
            errorMsg.setText("Topic name is Empty");
        }else if (TopicDescriptionTF.getText().isEmpty()) {
            errorMsg.setText("Topic Description is Empty");
        }else {

            sessionList.get(currentSession).getTopicList().get(currentTopic).setTopicName(TopicNameTF.getText());
            sessionList.get(currentSession).getTopicList().get(currentTopic).setSpeakerName(SpeakerNameTF.getText());
            sessionList.get(currentSession).getTopicList().get(currentTopic).setTopicDescription(TopicDescriptionTF.getText());
            //add topic to list

            //clean outputs
            errorMsg.setText("");

            ts.updateTopic(sessionList.get(currentSession).getTopicList().get(currentTopic));
        }


    }
    @FXML
    void onAddSessionNav(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/AddSession.fxml"));
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
    }




}
