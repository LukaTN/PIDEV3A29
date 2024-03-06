package com.example.gestionconference.Models.PresenceModels;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Presence {
    private final StringProperty uid;
    private final IntegerProperty idParticipant;
    private final ObjectProperty<Timestamp> presenceTime;
    private final IntegerProperty presenceStatus;




    public Presence(String uid, int idParticipant, Timestamp presenceTime, int presenceStatus) {
        this.uid = new SimpleStringProperty(uid);
        this.idParticipant = new SimpleIntegerProperty(idParticipant);
        this.presenceTime = new SimpleObjectProperty<>(presenceTime);
        this.presenceStatus = new SimpleIntegerProperty(presenceStatus);
    }
    public Presence(String uid, int idParticipant, int presenceStatus) {
        this.uid = new SimpleStringProperty(uid);
        this.idParticipant = new SimpleIntegerProperty(idParticipant);
        this.presenceTime = new SimpleObjectProperty<>(null); // or you can leave it uninitialized
        this.presenceStatus = new SimpleIntegerProperty(presenceStatus);
    }

    public String getUid() {
        return uid.get();
    }

    public StringProperty uidProperty() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid.set(uid);
    }

    public int getIdParticipant() {
        return idParticipant.get();
    }

    public IntegerProperty idParticipantProperty() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant.set(idParticipant);
    }

    public Timestamp getPresenceTime() {
        return presenceTime.get();
    }

    public ObjectProperty<Timestamp> presenceTimeProperty() {
        return presenceTime;
    }

    public void setPresenceTime(Timestamp presenceTime) {
        this.presenceTime.set(presenceTime);
    }

    public int getPresenceStatus() {
        return presenceStatus.get();
    }

    public IntegerProperty presenceStatusProperty() {
        return presenceStatus;
    }

    public void setPresenceStatus(int presenceStatus) {
        this.presenceStatus.set(presenceStatus);
    }
}

