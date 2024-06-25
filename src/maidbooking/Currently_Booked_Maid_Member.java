/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package maidbooking;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 *
 * @author fisot
 */
public class Currently_Booked_Maid_Member extends javax.swing.JFrame {

    int rating = 0;
    boolean selected = false;
    String memberemail = null;
    String maidemail = null;

    public Currently_Booked_Maid_Member() {
        UserSession session = UserSession.getInstance();
        memberemail = session.getUserEmail();
        System.out.println(memberemail);

        Member_APIs memberapi = new Member_APIs();
        maidemail = memberapi.getAcceptedMaidEmail(memberemail);
        
        if(maidemail == null){
            System.out.println("maidemail" + maidemail);
            Member_Homepage memberhomepage = new Member_Homepage();
            memberhomepage.setVisible(true);
            JOptionPane.showMessageDialog(null, "No Current Maid Booked");
            this.dispose();
            return;
        }
        
        session.setmaidemail(maidemail);
        session.setisaccepted("accept");

        // Initialization code...
        myinitComponents(); // Call the correct method
        Pageeditor();

        // Create and initialize the JComboBox
        String[] options = {"Very Good +2", "Good +1", "Mediocre 0", "Bad -1", "Very bad -2"};
        JComboBox<String> dropdown = new JComboBox<>(options);

        // Add an ActionListener to the JComboBox
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
                System.out.println("Selected Option: " + selectedOption);
                if (selectedOption.equals("Very Good +2")) {
                    rating = 2;
                } else if (selectedOption.equals("Good +1")) {
                    rating = 1;
                } else if (selectedOption.equals("Bad -1")) {
                    rating = -1;
                } else if (selectedOption.equals("Very bad -2")) {
                    rating = -2;
                } else if (selectedOption.equals("Mediocre 0")) {
                    rating = 0;
                }

                selected = true;
            }
        });

        // Use GridBagLayout for the content pane
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(jLabel1, gbc);

        gbc.gridy = 1;
        getContentPane().add(dropdown, gbc);

        gbc.gridy = 2;
        getContentPane().add(jLabel3, gbc);

        gbc.gridy = 3;
        getContentPane().add(jLabel2, gbc);

        gbc.gridy = 4;  // Add this line to set the position of the button
        getContentPane().add(jButton2, gbc);
    }

    public void Pageeditor() {
        jLabel2.setText(maidemail);
    }

    private void myinitComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton2.setText("Work Completed");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Currently Booked Maid");

        jLabel2.setText("jLabel2");

        jLabel3.setText("Rate before setting complete work");

        jButton2.setText("Work Completed");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton2)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(202, 202, 202)
                                                        .addComponent(jLabel1))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(50, 50, 50)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel3)
                                                                .addComponent(jLabel2)))))
                                .addContainerGap(213, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel1)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2)
                                .addGap(117, 117, 117)
                                .addComponent(jLabel3)
                                .addGap(70, 70, 70)
                                .addComponent(jButton2)
                                .addContainerGap(94, Short.MAX_VALUE))
        );

        pack();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Currently Booked Maid");

        jLabel2.setText("jLabel2");

        jLabel3.setText("Rate before setting complete work");

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 210, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(205, 205, 205))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(jLabel3)
                .addGap(78, 78, 78)
                .addComponent(jButton2)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
//        System.out.println("Hello World");
        if(selected){
           SwingUtilities.invokeLater(() -> {
            Member_APIs memberapi = new Member_APIs();
            memberapi.markMaidRequestComplete(memberemail);
            memberapi.updateMaidRating(maidemail, rating);
            Member_Homepage memberhomepage = new Member_Homepage();
            UserSession session = UserSession.getInstance();
            session.setmaidemail(null);
            session.setisaccepted(null);
            memberhomepage.setVisible(true);
            System.out.println("Before dispose");
            this.dispose();
            System.out.println("After dispose");
        });
       }else{
            JOptionPane.showMessageDialog(null, "Plz Provide Rating");
       }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Currently_Booked_Maid_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Currently_Booked_Maid_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Currently_Booked_Maid_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Currently_Booked_Maid_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Currently_Booked_Maid_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Currently_Booked_Maid_Member().setVisible(true);
            }
        });
    }
    private javax.swing.GroupLayout layout;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
