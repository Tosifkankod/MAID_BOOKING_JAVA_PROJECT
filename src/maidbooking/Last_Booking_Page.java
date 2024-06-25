/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package maidbooking;

import java.util.Map;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author fisot
 */
public class Last_Booking_Page extends javax.swing.JFrame {

    public int booked = 0;
    public String service;
    public int budget;
    public String time;

    /**
     * Creates new form Last_Booking_Page
     */
    public Last_Booking_Page() {
        myinitComponents();
    }

    public Last_Booking_Page(String serv, String tme, int bgt) {
        myinitComponents();
        this.service = serv;
        this.budget = bgt;
        this.time = tme;

        fetchDataFromDatabase();
    }

    private void myinitComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonSortByPrice = new javax.swing.JButton();
        jButtonSortByRating = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{{null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}},
                new String[]{"name", "budget", "rating"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        jButtonSortByPrice.setText("Sort by Price");
        jButtonSortByPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSortByPriceActionPerformed(evt);
            }
        });

        jButtonSortByRating.setText("Sort by Rating");
        jButtonSortByRating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSortByRatingActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new Dimension(1500, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(119, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSortByPrice)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonSortByRating)
                                .addGap(124, 124, 124))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(141, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonSortByPrice)
                                        .addComponent(jButtonSortByRating))
                                .addGap(45, 45, 45))
        );

        pack();
    }   

     private void jButtonSortByPriceActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        Member_APIs memberAPIs = new Member_APIs();
        Map<Integer, List<String>> maidData = memberAPIs.getMaidDetailsSortedByPrice(this.service, this.budget, this.time);

        updateTable(maidData);
    }                                                 

    private void jButtonSortByRatingActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        Member_APIs memberAPIs = new Member_APIs();
        Map<Integer, List<String>> maidData = memberAPIs.getMaidDetailsSortedByRating(this.service, this.budget, this.time);

        updateTable(maidData);
    }        
    
    private void updateTable(Map<Integer, List<String>> maidData) {
        // Create a DefaultTableModel with column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Rating");
        model.addColumn("Budget");
        model.addColumn("Email");
        model.addColumn("Action");

        // Populate the table model with maid data
        for (Map.Entry<Integer, List<String>> entry : maidData.entrySet()) {
            List<String> details = entry.getValue();
            model.addRow(new Object[]{entry.getKey(), details.get(0), details.get(1), details.get(2), details.get(3), "Book"});
        }

        // Set the table model to your jTable
        jTable1.setModel(model);

        // Add action listeners for the button column
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JButton(), jTable1));
    }          

//    private void fetchDataFromDatabaseSortedByPrice() {
//        Member_APIs memberAPIs = new Member_APIs();
//        Map<Integer, List<String>> maidData = memberAPIs.getMaidDetailsSortedByPrice(this.service, this.budget, this.time);
//        displayMaidData(maidData);
//    }
//
//    private void fetchDataFromDatabaseSortedByRating() {
//        Member_APIs memberAPIs = new Member_APIs();
//        Map<Integer, List<String>> maidData = memberAPIs.getMaidDetailsSortedByRating(this.service, this.budget, this.time);
//        displayMaidData(maidData);
//    }
    
    private void displayMaidData(Map<Integer, List<String>> maidData) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Budget");
        model.addColumn("Rating");
        model.addColumn("Email");
        model.addColumn("Action");

        List<Map.Entry<Integer, List<String>>> sortedEntries = maidData.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getValue().get(1), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        for (Map.Entry<Integer, List<String>> entry : sortedEntries) {
            List<String> details = entry.getValue();
            model.addRow(new Object[]{details.get(0), details.get(2), details.get(1), details.get(3), "Book"});
        }

        jTable1.setModel(model);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JButton(), jTable1));
    }

     private void fetchDataFromDatabase() {
        Member_APIs memberAPIs = new Member_APIs();
        Map<Integer, List<String>> maidData = memberAPIs.getMaidDetails(this.service, this.budget, this.time);

        // Create a DefaultTableModel with column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Rating");
        model.addColumn("Budget");
        model.addColumn("Email");
        model.addColumn("Action");

        // Populate the table model with maid data
        for (Map.Entry<Integer, List<String>> entry : maidData.entrySet()) {
            List<String> details = entry.getValue();
            model.addRow(new Object[]{entry.getKey(), details.get(0), details.get(1), details.get(2), details.get(3), "Book"});
        }

        // Set the table model to your jTable
        jTable1.setModel(model);

        // Add action listeners for the button column
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JButton(), jTable1));
    }

   private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }


      private class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton button;
        private final JTable table;
        private String label;
        private int maidId;
        private String email;

        public ButtonEditor(JButton button, JTable table) {
            this.button = button;
            this.button.addActionListener(this);
            this.table = table;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.label = (value == null) ? "" : value.toString();
            this.maidId = (int) table.getModel().getValueAt(row, 0);
            this.email = (String) table.getModel().getValueAt(row, 4);
            this.button.setText(this.label);
            return this.button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            int maidId = (int) table.getModel().getValueAt(row, 0);
            String name = (String) table.getModel().getValueAt(row, 1);
            String rating = (String) table.getModel().getValueAt(row, 2);
            String budget = (String) table.getModel().getValueAt(row, 3);
            String maidemail = (String) table.getModel().getValueAt(row, 4);

            System.out.println("Name: " + name);
            System.out.println("Rating: " + rating);
            System.out.println("Budget: " + budget);
            System.out.println("Email: " + email);

            Member_APIs memberapi = new Member_APIs();
            memberapi.insertMaidRequest(maidemail, time);

            fireEditingStopped();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "name", "budget", "rating"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
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
            java.util.logging.Logger.getLogger(Last_Booking_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Last_Booking_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Last_Booking_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Last_Booking_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        System.out.println("Helloworld");

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Last_Booking_Page().setVisible(true);
            }
        });
    }
    private javax.swing.JButton jButtonSortByPrice;
    private javax.swing.JButton jButtonSortByRating;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
