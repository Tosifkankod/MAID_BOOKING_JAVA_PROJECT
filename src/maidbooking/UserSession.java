package maidbooking;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fisot
 */
public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private String maid_email = null;
    private String isaccept = null; 
    

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

//    useremail
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        System.out.println("setuserEmail" + userEmail);
        this.userEmail = userEmail;
    }
    
    
//    maidemail
    public String getmaidEmail() {
        return maid_email;
    }
    
    public void setmaidemail(String maidemail) {
       System.out.println(maidemail);

        this.maid_email = maidemail;
    }
    
    
//    accepted 
    public String getisaccepted() {
        return isaccept;
    }
    
    public void setisaccepted(String accepted) {
        this.isaccept = accepted;
    }


    public void clearSession() {
        this.userEmail = null;
        // Other session variables can be cleared here if needed
    }
}
