/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maidbooking;

import java.awt.Component;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import maidbooking.DatabaseConnection;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author fisot
 */
public class Member_APIs {

    public Map<String, Object> getMaidDetails(String memberEmail) {
        String sql = "SELECT * FROM MEMBER_DETAILS WHERE email = ?";
        Map<String, Object> memberDetails = null;

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, memberEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    memberDetails = new HashMap<>();
                    memberDetails.put("email", resultSet.getString("email"));
                    memberDetails.put("fullname", resultSet.getString("full_name"));
                    memberDetails.put("address", resultSet.getString("address"));

                    // Put other details accordingly
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberDetails;
    }

    public int getAvailableMaidCount(String service, int budget, String timeSlot) {
        int availableMaidCount = 0;
        String sql = "SELECT COUNT(*) AS available_maid_count FROM MAID_DETAILS "
                + "WHERE service = ? AND price <= ? AND " + timeSlot + " = 1";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, service);
            preparedStatement.setDouble(2, budget);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    availableMaidCount = resultSet.getInt("available_maid_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableMaidCount;
    }

    public Map<Integer, List<String>> getMaidDetails(String service, int budget, String timeSlot) {
        Map<Integer, List<String>> maidDetails = new HashMap<>();
        String sql = "SELECT id, full_name, rating, price, email "
                + "FROM MAID_DETAILS "
                + "WHERE service = ? AND price <= ? AND " + timeSlot + " = 1";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, service);
            preparedStatement.setInt(2, budget);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int maidId = resultSet.getInt("id");
                    String name = resultSet.getString("full_name");
                    String rating = resultSet.getString("rating");
                    String maidBudget = resultSet.getString("price");
                    String email = resultSet.getString("email");

                    // Storing retrieved details in a list
                    List<String> details = new ArrayList<>();
                    details.add(name);
                    details.add(rating);
                    details.add(maidBudget);
                    details.add(email);

                    // Putting the maidId as the key in the map
                    maidDetails.put(maidId, details);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maidDetails;
    }

//    checking if email is in maid_request
    private boolean isRequestEmailAlreadyPresent(String memberEmail) {
        String sql = "SELECT COUNT(*) FROM MAID_REQUESTS WHERE member_email = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, memberEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Return true if count is greater than 0 (email already present)
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertMaidRequest(String maidEmail, String requestTime) {
        // Getting member Email
        UserSession session = UserSession.getInstance();
        String memberEmail = session.getUserEmail();

        // Check if the member has already booked a maid
        if (isRequestEmailAlreadyPresent(memberEmail)) {
            JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
                    "Sorry, you can only have one maid booking at a time.");
            return; // Stop further execution
        }

        // Retrieve the address based on the member_email
        String address = getAddressByEmail(memberEmail);

        // If the address is not null, proceed with the insertion
        if (address != null) {
            String sql = "INSERT INTO MAID_REQUESTS (maid_email, member_email, is_accepted, request_time, address) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, maidEmail);
                preparedStatement.setString(2, memberEmail);
                preparedStatement.setString(3, "pending"); // Set is_accepted to "Pending"
                preparedStatement.setString(4, requestTime);
                preparedStatement.setString(5, address);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
                            "Maid request inserted successfully");
                } else {
                    System.out.println("Failed to insert maid request.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to insert maid request. Unable to retrieve address for the member_email.");
        }
    }

    // Method to get the address based on email
    private String getAddressByEmail(String email) {
        String address = null;
        String sql = "SELECT address FROM MEMBER_DETAILS WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    address = resultSet.getString("address");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }

//   getting user data isaccepted=accepted from maid_request
    public String getAcceptedMaidEmail(String memberEmail) {
        System.out.println("getacceptedmaidemail fucntion " + memberEmail);
        String sql = "SELECT maid_email FROM MAID_REQUESTS WHERE member_email = ? AND is_accepted = 'accept';";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, memberEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("getacceptedmaidemail return from function " + resultSet.getString("maid_email"));

                    return resultSet.getString("maid_email"); // Return maid_email if it exists
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return null; // Return null if no matching record is found
    }

    public void markMaidRequestComplete(String memberEmail) {
        String deleteSql = "DELETE FROM MAID_REQUESTS WHERE member_email = ? AND is_accepted = 'accept';";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {

            deleteStatement.setString(1, memberEmail);

            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Maid request marked as complete and row deleted successfully!");
            } else {
                System.out.println("No record deleted. Maybe no matching record found.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public void updateMaidRating(String maidEmail, int providedRating) {
        String updateSql = "UPDATE MAID_DETAILS SET rating = rating + ? WHERE email = ?;";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            updateStatement.setInt(1, providedRating);
            updateStatement.setString(2, maidEmail);

            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Maid rating updated successfully!");
            } else {
                System.out.println("No record updated. Maybe no matching record found.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public Map<Integer, List<String>> getMaidDetailsSortedByPrice(String service, int budget, String time) {
        Map<Integer, List<String>> maidDetails = getMaidDetails(service, budget, time);

        // Sort the maid details by price in ascending order
        maidDetails = maidDetails.entrySet().stream()
                .sorted(Comparator.comparing(entry -> Double.parseDouble(entry.getValue().get(2))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return maidDetails;
    }

    public Map<Integer, List<String>> getMaidDetailsSortedByRating(String service, int budget, String time) {
        Map<Integer, List<String>> maidDetails = getMaidDetails(service, budget, time);

        // Sort the maid details by rating in descending order
        maidDetails = maidDetails.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(entry -> Double.parseDouble(entry.getValue().get(1)))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return maidDetails;
    }

}
