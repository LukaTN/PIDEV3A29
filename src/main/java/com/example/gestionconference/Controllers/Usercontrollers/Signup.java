package GestionUser.Usercontrollers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import GestionUser.UserModels.User;
import GestionUser.UserServices.UserService;

public class Signup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField confpass;

    @FXML
    private TextField email;

    @FXML
    private TextField login;

    @FXML
    private TextField nom;

    @FXML
    private TextField numtel;

    @FXML
    private PasswordField password;

    @FXML
    private TextField prenom;

    @FXML
    private Button signup;
    @FXML
    private ImageView profileImageView;

    @FXML
    private ChoiceBox<String> role;
    private String[] roles = {"Organizer", "Participant"};
    @FXML
    void initialize() {
        assert confpass != null : "fx:id=\"confpass\" was not injected: check your FXML file 'signup.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'signup.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'signup.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'signup.fxml'.";
        assert numtel != null : "fx:id=\"numtel\" was not injected: check your FXML file 'signup.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'signup.fxml'.";
        assert prenom != null : "fx:id=\"prenom\" was not injected: check your FXML file 'signup.fxml'.";
        assert signup != null : "fx:id=\"signup\" was not injected: check your FXML file 'signup.fxml'.";
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'signup.fxml'.";
        role.getItems().addAll(roles);

    }
    @FXML
    private File choosePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image and display it in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }
    @FXML

    void signupButtonOnAction(ActionEvent e) {
        String username = login.getText();
        String mail = email.getText();
        String pass = password.getText();
        String confirmpass = confpass.getText();
        String nom = this.nom.getText();
        String prenom = this.prenom.getText();
        String numtelText = this.numtel.getText();
        String role = this.role.getValue();

        if (validateInput(username, mail, pass, confirmpass, nom, prenom, numtelText) &&
                validateEmail(mail) && validatePassword(pass, confirmpass)& validatenumtel(numtelText) && role != null) {
            int numtel = Integer.parseInt(numtelText);
            UserService us = new UserService();

            try {
                if (us.getByUsername(username) != null)  {
                    showAlert(Alert.AlertType.ERROR, "Error", "Username already exists", "Please choose a different username.");
                } else {
                    User u = new User(username, nom, prenom, mail, pass, numtel, role);
                    us.add(u);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Successful", "You have successfully registered.");
                    try {
                        us.add(u);

                        // Load the new FXML page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signin.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);

                        // Get the current stage
                        Stage stage = (Stage) signup.getScene().getWindow();

                        // Set the new scene
                        stage.setScene(scene);
                    } catch (IOException | SQLException er) {
                        er.printStackTrace();
                    }
                }
            } catch (SQLException er) {
                er.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Registration Failed", "An error occurred during registration.");
            }
        }
    }
    private boolean validatenumtel(String numtel) {
        String numtelRegex = "^[0-9]{8}$";
        if (!numtel.matches(numtelRegex)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Phone Number", "Please enter a valid phone number.");
            return false;
        }
        return true;
    }
    private boolean validateEmail(String email) {
        // Use a regular expression for basic email format validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Email", "Please enter a valid email address.");
            return false;
        }
        return true;
    }
    private boolean validateInput(String username, String mail, String pass, String confirmpass, String nom, String prenom, String numtel) {

        if (username.isEmpty() || mail.isEmpty() || pass.isEmpty() || confirmpass.isEmpty() || nom.isEmpty() || prenom.isEmpty() || numtel.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Fields", "Please fill in all the fields.");
            return false;
        }
        return true;
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean validatePassword(String password, String confirmPassword) {

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Weak Password", "Password must be at least 8 characters long.");
            return false;
        } else if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password Mismatch", "Password and confirm password do not match.");
            return false;
        }
        return true;
    }

}
