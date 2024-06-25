/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maidbooking;

import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author fisot
 */
public class UserAccountValidator {
    //    checking Login

    public static String ValidateLogin(String email, String tableName) {
        String password = null;
        String sql = "SELECT password, address FROM " + tableName + " WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the email parameter in the prepared statement
            preparedStatement.setString(1, email);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if the email exists
                if (resultSet.next()) {
                    // Retrieve the password from the result set
                    password = resultSet.getString("password");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

//    checking Email exist
    public static boolean emailExists(String email, String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE email = '" + email + "'";
        try (Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    Email Validator
    public static boolean EmailValidator(String email) {

        // Define the regular expression pattern for email validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a Matcher object to match the input against the regex
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the regex
        if (matcher.matches()) {
            return true;
        }

        return false;

    }

//  Strong password
    public static boolean StrongPassword(String password) {

        // Define the regular expression pattern for a strong password
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(passwordRegex);

        // Create a Matcher object to match the input against the regex
        Matcher matcher = pattern.matcher(password);

        // Check if the password matches the regex
        if (matcher.matches()) {
            return true;
        }

        return false;

    }

    public static void updateMaidPassword(String maidEmail, String newPassword, String table) {
        String updateSql = "UPDATE " + table + " SET password = ? WHERE email = ?;";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            
            updateStatement.setString(1, newPassword);
            updateStatement.setString(2, maidEmail);
            
            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
                        "password Updated successfully");
            } else {
                JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
                        "password cannot update");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
}
