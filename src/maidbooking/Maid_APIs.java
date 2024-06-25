package maidbooking;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import maidbooking.DatabaseConnection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author fisot
 */
public class Maid_APIs {

    public static boolean ChangeMaidCharges(int newPrice, String email) {
        String sql = "UPDATE MAID_DETAILS SET price = ? WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the price and email parameters in the prepared statement
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, email);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean ChangeAvaliabilityTiming(
            String email,
            boolean t4am_6amRadioButton,
            boolean t6am_8amRadioButton,
            boolean t8am_10amRadioButton,
            boolean t10am_12amRadioButton,
            boolean t12pm_2pmRadioButton,
            boolean t2pm_4pmRadioButton,
            boolean t4pm_6pmRadioButton,
            boolean t6pm_8pmRadioButton,
            boolean t8pm_10pmRadioButton,
            boolean t10pm_12amRadioButton
    ) {
        String sql = "UPDATE MAID_DETAILS SET t4am_6am = ?, t6am_8am = ?, t8am_10am = ?, t10am_12pm = ?, "
                + "t12pm_2pm = ?, t2pm_4pm = ?, t4pm_6pm = ?, t6pm_8pm = ?, t8pm_10pm = ?, t10pm_12am = ? WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, t4am_6amRadioButton ? 1 : 0);
            preparedStatement.setInt(2, t6am_8amRadioButton ? 1 : 0);
            preparedStatement.setInt(3, t8am_10amRadioButton ? 1 : 0);
            preparedStatement.setInt(4, t10am_12amRadioButton ? 1 : 0);
            preparedStatement.setInt(5, t12pm_2pmRadioButton ? 1 : 0);
            preparedStatement.setInt(6, t2pm_4pmRadioButton ? 1 : 0);
            preparedStatement.setInt(7, t4pm_6pmRadioButton ? 1 : 0);
            preparedStatement.setInt(8, t6pm_8pmRadioButton ? 1 : 0);
            preparedStatement.setInt(9, t8pm_10pmRadioButton ? 1 : 0);
            preparedStatement.setInt(10, t10pm_12amRadioButton ? 1 : 0);
            preparedStatement.setString(11, email);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, List<String>> getDataByPendingStatus(String maidEmail) {
        Map<Integer, List<String>> maidDetails = new HashMap<>();
        String sql = "SELECT request_id, request_time, address, member_email "
                + "FROM MAID_REQUESTS "
                + "WHERE maid_email = ? AND is_accepted = 'pending'";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maidEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int requestId = resultSet.getInt("request_id");
                    String time = resultSet.getString("request_time");
                    String address = resultSet.getString("address");
                    String memberEmail = resultSet.getString("member_email");

                    // Storing retrieved details in a list
                    List<String> details = new ArrayList<>();
                    details.add(memberEmail);
                    details.add(time);
                    details.add(address);

                    // Putting the requestId as the key in the map
                    maidDetails.put(requestId, details);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maidDetails;
    }

//    public void updateMaidRequestStatus(String maidEmail, String memberEmail, String newStatus, String time) {
//        String sql = "UPDATE MAID_REQUESTS SET is_accepted = ? WHERE maid_email = ? AND member_email = ?";
//
//        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//            preparedStatement.setString(1, newStatus);
//            preparedStatement.setString(2, maidEmail);
//            preparedStatement.setString(3, memberEmail);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
//                        "Maid request status updated successfully");
//            } else {
//                JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
//                        "cannot update ");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void updateMaidRequestStatus(String maidEmail, String memberEmail, String newStatus, String time) {
        String sqlUpdate = "UPDATE MAID_REQUESTS SET is_accepted = ? WHERE maid_email = ? AND member_email = ?";
        String sqlRejectPending = "UPDATE MAID_REQUESTS SET is_accepted = 'rejected' WHERE maid_email = ? AND is_accepted = 'pending' AND request_time = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatementUpdate = connection.prepareStatement(sqlUpdate); PreparedStatement preparedStatementRejectPending = connection.prepareStatement(sqlRejectPending)) {

            // Update the specific request
            preparedStatementUpdate.setString(1, newStatus);
            preparedStatementUpdate.setString(2, maidEmail);
            preparedStatementUpdate.setString(3, memberEmail);

            int rowsAffected = preparedStatementUpdate.executeUpdate();

            if ("accept".equals(newStatus)) {
                // If the request is accepted, reject all pending requests for the maid at the provided time
                preparedStatementRejectPending.setString(1, maidEmail);
                preparedStatementRejectPending.setString(2, time);
                int rowsRejected = preparedStatementRejectPending.executeUpdate();

                // You can add a message or log the rejection if needed
                System.out.println(rowsRejected + " pending requests rejected for maid " + maidEmail + " at time " + time);
            }

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
                        "Maid request status updated successfully");
            } else {
                JOptionPane.showMessageDialog((Component) SwingUtilities.getRoot(null),
                        "Cannot update");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isMaidEmailExists(String maidEmail) {
        String query = "SELECT COUNT(*) AS count FROM MAID_DETAILS WHERE email = ?;";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maidEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // If count is greater than 0, email exists
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return false; // Return false if any error occurs
    }

    public Map<String, Object> getMaidDetails(String maidEmail) {
        String sql = "SELECT * FROM MAID_DETAILS WHERE email = ?";
        Map<String, Object> maidDetails = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maidEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maidDetails = new HashMap<>();
                    maidDetails.put("maidEmail", resultSet.getString("email"));
                    maidDetails.put("fullname", resultSet.getString("full_name"));
                    maidDetails.put("rating", resultSet.getString("rating"));
                    maidDetails.put("service", resultSet.getString("service"));



                    // Put other details accordingly
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maidDetails;
    }

}
