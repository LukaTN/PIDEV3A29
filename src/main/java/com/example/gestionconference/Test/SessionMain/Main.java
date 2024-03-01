package com.example.gestionconference.Test.SessionMain;

/*
public class Main {

    public static void main(String[] args) throws SQLException {
       Lieu s = new Lieu("Ariana","test",18,"test");
        LieuServices us = new LieuServices();
        us.addConference(s);




        Conference s = new Conference("test2","test","test",60.7,transform("PUBLIC"),1,1);
        ConferenceServices us =new ConferenceServices();
        us.addConference(s);

        Organisateur s = new Organisateur("ahmed","ahmed@gmail.com",93269546,"ORGANISATION");
        OrganisateurServices us = new OrganisateurServices();
        us.addOrganisateur(s);
    }

    public static ConferenceType transform(String conferenceTypeString) {

        if ("PUBLIC".equals(conferenceTypeString)) {
            return ConferenceType.PUBLIC;
        } else if ("PRIVATE".equals(conferenceTypeString)) {
            return ConferenceType.PRIVATE;
        } else {
            return null;
        }
    }

        Session s =new Session("t0est",12,10004,1);
        SessionServices ss =new SessionServices();
        ss.addSession(s);


    }
}   */



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionconference/Fxml/SessionFXML/AddSession.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Confera");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}