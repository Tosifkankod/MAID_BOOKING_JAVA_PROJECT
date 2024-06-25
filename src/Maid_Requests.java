/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import maidbooking.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map;

/**
 *
 * @author fisot
 */
public class Maid_Requests extends javax.swing.JFrame {

    public String email = "";
    private JTable table;

    /**
     * Creates new form Maid_Requests
     */
    public Maid_Requests() {
        myInitComponents();
        UserSession session = UserSession.getInstance();
        email = session.getUserEmail();
        fetchAndDisplayData(email);

    }

    private void myInitComponents() {
        setTitle("Maid Requests");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Create the table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the "Action" column is editable
            }
        };

        model.addColumn("Request ID");
        model.addColumn("Member Email");
        model.addColumn("Request Time");
        model.addColumn("Address");
        model.addColumn("Action");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add a JComboBox as the editor for the "Action" column
        JComboBox<String> actionComboBox = new JComboBox<>(new String[]{"Accept", "Reject"});
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(actionComboBox));

        // Add ActionListener to the JComboBox
        actionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                String selectedAction = (String) comboBox.getSelectedItem();
                // Perform your function based on the selected action (Accept/Reject)
                // You can retrieve the selected row data using table.getSelectedRow()
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int requestId = (int) table.getValueAt(selectedRow, 0);
                    // Perform your function here
                    performFunction(requestId, selectedAction);
                }
            }
        });
    }

    private int getColumnIndex(String columnName) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1; // Column not found
    }

    private void fetchAndDisplayData(String maidEmail) {
        // Assuming you have a method to get data by pending status
        Maid_APIs maidapi = new Maid_APIs();
        Map<Integer, List<String>> data = maidapi.getDataByPendingStatus(maidEmail);

        for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
            Vector<Object> row = new Vector<>();
            row.add(entry.getKey());
            row.addAll(entry.getValue());
            row.add(""); // Placeholder for the "Action" column
            ((DefaultTableModel) table.getModel()).addRow(row);
        }
    }

   private void performFunction(int selectedRow, String selectedAction) {
    // Get the table model
    DefaultTableModel model = (DefaultTableModel) table.getModel();

    // Check if the selectedRow is within the bounds of the table model
    if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
        // Get data from all columns for the selected row
        int requestIdColumnIndex = 0;
        int memberEmailColumnIndex = 1;
        int requestTimeColumnIndex = 2;
        int addressColumnIndex = 3;
        // Add more columns as needed

        int requestId = (int) model.getValueAt(selectedRow, requestIdColumnIndex);
        String memberEmail = (String) model.getValueAt(selectedRow, memberEmailColumnIndex);
        String requestTime = (String) model.getValueAt(selectedRow, requestTimeColumnIndex);
        String address = (String) model.getValueAt(selectedRow, addressColumnIndex);
        // Add more columns as needed

        // Implement your logic here based on the selected action and data
        System.out.println("Performing function for Request ID: " + requestId
                + ", Member Email: " + memberEmail
                + ", Request Time: " + requestTime
                + ", Address: " + address
                + ", Selected Action: " + selectedAction);

        // Example: You can now use these values to perform some database operation or other actions.
    } else {
        System.out.println("Invalid selected row index: " + selectedRow);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 776, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Maid_Requests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Maid_Requests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Maid_Requests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Maid_Requests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Maid_Requests().setVisible(true);
            }
        });
        /* Create and display the form */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
