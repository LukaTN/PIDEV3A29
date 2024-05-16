package com.example.gestionconference.Services.EvaluationService;

import com.example.gestionconference.Models.EvaluationModels.Commentaire;
import com.example.gestionconference.Util.EvaluationUtils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crudcommentaire {

    private Connection cnx;

    public Crudcommentaire() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addCommentaire(Commentaire commentaire) {
        String req = "INSERT INTO `commentaires`(`idcommentaire`, `caractere`) VALUES (?,?)";

        try {

            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, commentaire.getIdCommentaire());
            stm.setString(2, commentaire.getCaractere());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void supprimerCommentaire(int id) {
        try {
            String req = "DELETE FROM `Commentaires` WHERE idcommentaire = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("commentaire deleted !");
        } catch (SQLException ex) {
            System.out.println("commentaire Not deleted !");
            System.out.println(ex.getMessage());
        }
    }




    public List<Commentaire> afficherCommentaire() {
        List<Commentaire> listCommentaires = new ArrayList<>();
        try {
            String req = "SELECT * FROM Commentaires";
            Statement ste = cnx.createStatement();
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {

                Commentaire resultCommentaire = new Commentaire(result.getInt("idcommentaire"), result.getString("caractere"));
                listCommentaires.add(resultCommentaire);
            }
            //System.out.println(listCommentaires);
            return listCommentaires;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listCommentaires;
    }


    public void modifierCommentaire( Commentaire  c) {
        try {
            String req = "UPDATE `Commentaires` SET "
                    + "`caractere` = '" + c.getCaractere()
                    /*+ "', `URLImage` = '" + p.getUrlImage()*/
                    + "' WHERE `Commentaires`.`idcommentaire` = " + c.getIdCommentaire();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("commentaire updated !");
        } catch (SQLException ex) {
            System.out.println("commentaire not updated !");
            System.out.println(ex.getMessage());
        }
    }
    public int getCommentCount() {
        String query = "SELECT COUNT(*) FROM comments";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1); // Get the count from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return 0; // Return 0 if there's an error or no comments found
    }
    public double getAverageCommentLength() {
        String query = "SELECT AVG(LENGTH(comment_text)) FROM comments";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble(1); // Get the average length from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return 0; // Return 0 if there's an error or no comments found
    }


}
