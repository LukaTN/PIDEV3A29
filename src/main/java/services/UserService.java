package services;

import models.User;

import java.sql.*;
import java.util.List;
import utils.Mydatabase;

public class UserService implements iCrud<User> {

    private Connection connection;

    public UserService() {
        connection = Mydatabase.getInstance().getConnection();
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(User user) throws SQLException {
        if (userExistsByUsername(user.getUsername())) {
            System.out.println("User with username '" + user.getUsername() + "' already exists.");
            return false;
        }

        // SQL query to insert a new user
        String sql = "INSERT INTO user (username, mail, password, numtel, nom, prenom) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getMail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getPhone());
            preparedStatement.setString(5, user.getNom());
            preparedStatement.setString(6, user.getPrenom());

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
        String sql = "update user set username = ?,  mail = ?, password = ?, numtel = ?, nom = ?, prenom = ? where username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getMail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getPhone());
            preparedStatement.setString(5, user.getNom());
            preparedStatement.setString(6, user.getPrenom());
            preparedStatement.setString(7, user.getUsername());
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
                user.setUsername(resultSet.getString("username"));
                user.setMail(resultSet.getString("mail"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getInt("numtel"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));

                return user;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
