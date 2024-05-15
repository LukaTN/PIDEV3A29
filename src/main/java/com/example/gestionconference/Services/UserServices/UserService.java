package com.example.gestionconference.Services.UserServices;



import java.sql.*;
import java.util.List;
import com.example.gestionconference.Util.UserUtils.Mydatabase;
import com.example.gestionconference.Models.UserModels.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserService implements iCrud<User> {

    private Connection connection;

    public UserService() {
        connection = Mydatabase.getInstance().getConnection();
    }

    @Override
    public ObservableList<User> getAll() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setMail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getInt("numtel"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return users;
    }

    @Override
    public boolean add(User user) throws SQLException {
        if (userExistsByUsername(user.getUsername())) {
            System.out.println("User with username '" + user.getUsername() + "' already exists.");
            return false;
        }

        // SQL query to insert a new user
        String sql = "INSERT INTO user (username, email, password, numtel, nom, prenom,role,profile_picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getMail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getPhone());
            preparedStatement.setString(5, user.getNom());
            preparedStatement.setString(6, user.getPrenom());
            preparedStatement.setString(7, user.getRole());
            preparedStatement.setBytes(8, user.getProfilePicture());
            preparedStatement.executeUpdate();

            System.out.println("User added successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    private boolean userExistsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(User user) throws SQLException {
        try{
            if (userExistsByUsername(user.getUsername())){
                String sql = "delete from user where username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.executeUpdate();
                return true;
            }
            else {
                System.out.println("User with username '" + user.getUsername() + "' does not exist.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String username) throws SQLException {
        if (userExistsByUsername(username)){
        try {
            String sql = "delete from user where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }}
        else {
            System.out.println("User with username '" + username + "' does not exist.");
        }
        return false;
    }

    @Override
    public boolean update(User user) throws SQLException {
        if (userExistsByUsername(user.getUsername())){
        String sql = "update user set username = ?,  email = ?, password = ?, numtel = ?, nom = ?, prenom = ?, profile_picture=?,role=? where username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getMail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getPhone());
            preparedStatement.setString(5, user.getNom());
            preparedStatement.setString(6, user.getPrenom());
            preparedStatement.setString(9, user.getUsername());
            preparedStatement.setBytes(7, user.getProfilePicture());
            preparedStatement.setString(8, user.getRole());
            preparedStatement.executeUpdate();
            System.out.println("Updated successfully");
            return true;

        } catch
        (SQLException e) {
            System.err.println(e.getMessage());
        }}
        else {
            System.out.println("User with username '" + user.getUsername() + "' does not exist.");
        }
        return false;
    }



    // Get user by username
    public User getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setMail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getInt("numtel"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setRole(resultSet.getString("role"));
                user.setProfilePicture(resultSet.getBytes("profile_picture"));

                return user;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    // Get user by username
    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setMail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getInt("numtel"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setRole(resultSet.getString("role"));
                user.setProfilePicture(resultSet.getBytes("profile_picture"));

                return user;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
