/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package maidbooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author fisot
 */
public class MaidBooking {  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MAIDBOOKING", "root", "admin");
            System.out.println(con);
            
            // Create a statement
            Statement statement = con.createStatement();
            

            // Define the SQL query to create a table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS MEMBER_DETAILS  (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    full_name VARCHAR(255),\n" +
            "    email VARCHAR(255),\n" +
            "    address VARCHAR(255),\n" +
            "    password VARCHAR(255),\n" +
            "    isnotification BOOLEAN,\n" +
            "    notification VARCHAR(255),\n" + 
            "    booked VARCHAR(255)\n" +
            ");";  
            
            
          
 

            // Execute the query to create the table
            statement.executeUpdate(createTableSQL);

            // Close resources
            statement.close();
            con.close();

            System.out.println("Table created successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(MaidBooking.class.getName()).log(Level.SEVERE, null, ex);
        }

  
        
        // TODO code application logic here
        MainCompnent maincompo = new MainCompnent();      
        maincompo.setVisible(true);
        
        
        
        
    }
    
}
