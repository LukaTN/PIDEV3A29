package com.example.gestionconference.Services.EvaluationService;

import com.example.gestionconference.Models.EvaluationModels.*;
import com.example.gestionconference.Util.EvaluationUtils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crudnote {
    private Connection cnx;

    public Crudnote() {
        cnx = MyDB.getInstance().getCnx();
    }


    public void addNote(Note note) {
        String req = "INSERT INTO `Notes`(`idnotation`, `notation`,`image`) VALUES (?,?,?)";

        try {

            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, note.getIdNotation());
            stm.setInt(2, note.getNotation());
            stm.setString(3, note.getImage());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimerNote(int id) {
        try {
            String req = "DELETE FROM `Notes` WHERE idnotation= " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("notation deleted !");
        } catch (SQLException ex) {
            System.out.println("notation Not deleted !");
            System.out.println(ex.getMessage());
        }
    }



    public List<Note> afficherNote() {
        List<Note> listNotes = new ArrayList<>();
        try {
            String req = "SELECT * FROM Notes";
            Statement ste = cnx.createStatement();
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {

                Note resultNote = new Note(result.getInt("id"), result.getInt("notation"), result.getString("image"));
                listNotes.add(resultNote);
            }
            //System.out.println(listNotes);
            return listNotes;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listNotes;
    }


    public void modifierNote(Note a) {
        try {
            String req = "UPDATE `Notes` SET "
                    + "`notation` = " + a.getNotation()
                    + ", `URLImage` =" + a.getImage()
                    + " WHERE `Notes`.`idnotation` = " + a.getIdNotation();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("note updated !");
        } catch (SQLException ex) {
            System.out.println("note not updated !");
            System.out.println(ex.getMessage());
        }
    }
    public int getNoteCount() {
        String query = "SELECT COUNT(*) FROM notes";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1); // Get the count from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return 0; // Return 0 if there's an error or no notes found
    }
    // Method to get the average note value
    public double getAverageNoteValue() {
        String query = "SELECT AVG(value) FROM notes";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble(1); // Get the average value from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return 0.0; // Return 0.0 if there's an error or no notes found
    }

}
