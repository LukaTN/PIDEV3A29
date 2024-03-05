package com.example.gestionconference.Models.SessionModels;

import javafx.beans.property.*;

import java.time.LocalTime;

public class Presence {
    private final StringProperty uid;
    private final IntegerProperty idParticipant;
    private final ObjectProperty<LocalTime> presenceTime;
    private final BooleanProperty presenceStatus;

    public Presence(String uid, int idParticipant, LocalTime presenceTime, boolean presenceStatus) {
        this.uid = new SimpleStringProperty(uid);
        this.idParticipant = new SimpleIntegerProperty(idParticipant);
        this.presenceTime = new SimpleObjectProperty<>(presenceTime);
        this.presenceStatus = new SimpleBooleanProperty(presenceStatus);
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

    public LocalTime getPresenceTime() {
        return presenceTime.get();
    }

    public ObjectProperty<LocalTime> presenceTimeProperty() {
        return presenceTime;
    }

    public void setPresenceTime(LocalTime presenceTime) {
        this.presenceTime.set(presenceTime);
    }

    public boolean isPresenceStatus() {
        return presenceStatus.get();
    }

    public BooleanProperty presenceStatusProperty() {
        return presenceStatus;
    }

    public void setPresenceStatus(boolean presenceStatus) {
        this.presenceStatus.set(presenceStatus);
    }
}
