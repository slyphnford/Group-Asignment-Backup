/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package homepage;
//import java.sql.*;
//
import homepage.LoggedIn;
import java.util.Formatter;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
public class Homepage{
    
    //Initialise all of the variables below to be use for the whole program
    static final Scanner scan = new Scanner(System.in);
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/yuutube?zeroDateTimeBehavior=CONVERT_TO_NULL";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn = null;
    static PreparedStatement stmt = null;
    static Statement statement = null;
    
    
    //display Homepage format 
    public static void displayHomepage(){
        System.out.println("--------------------------------------------------------------" + 
                "--------------------------------------------------------------"
                + "-------------------------------------------------------------");
        System.out.println("");
        System.out.printf("%84s","Welcome to Yuu-Tube v1.0!");
        System.out.println("");
        System.out.println("");
        System.out.printf("%14s%17s%26s%21s%19s%17s%17s%19s\n", "Homepage", "Search"
                ,"Trending Videos", "Categories", "Playlist", "Login", "Logout", "Register");
        System.out.println("");
        System.out.println("--------------------------------------------------------------" + 
                "--------------------------------------------------------------"
                + "-------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    
    //Allow users to perform operations on Yuu-tube page, still haven't complete yet
    public static void userOperations(){
        String sql;
        //Display the options available to the users
        System.out.println("Hello user, please select\nA. To watch random videos in "
                + "the homepage\nB. To search videos\nC. To watch trending videos"
                + "\nD. Watch videos according to categories "
                + "\nE. To go to your playlist\nF. To login\nG. To logout\nH. To register");
        System.out.println("");
        
        /*Start looping to check the user's input, if the input out of range, 
        the loop will continue over and over again*/
        boolean status = true;
        while(status){
            
                System.out.printf("Please select: "); 
                String user_choice = scan.nextLine();
                switch(user_choice){
                    
                case "A":
                    sql = "SELECT* FROM video ORDER BY RAND() LIMIT 5";
                    videoOptions.displayVideosFormat();
                    videoOptions.displayVideoOptions(sql);
                    status = false;
                    break;
                    
                case "B":
                    search();
                    status = false;
                    break;
                    
                case "C":
                    sql = "SELECT* FROM video ORDER BY ABS(video_views) DESC LIMIT 5";
                    videoOptions.displayVideosFormat();
                    videoOptions.displayVideoOptions(sql);
                    status = false;
                    break;
                    
                case "D":
                    Category.displayCategories();
                    status = false;
                    break;
                    
                case "E":
                    int user_id = LoggedIn.LoggedIn();
                    if(user_id != 0){
                        Playlist.PlaylistGeneralOperation();
                    }
                    else{
                        System.out.println("You need to login before you can access"
                                + " the playlist.");
                        while(true){
                            System.out.print("Enter \'Y\' to login and \'E\' to exit: ");
                            String user_input = scan.nextLine();
                            if(user_input.equals("Y")){
                                RegistrationForm.Login();
                                break;
                            }
                            else if(user_input.equals("E")){
                                System.out.println("");
                                userOperations();
                                break;
                            }
                            else{
                                System.out.println("Please enter correct input");
                            }
                        }
                    }
                    status = false;
                    break;
                    
                case "F":
                    RegistrationForm.Login();
                    status = false;
                    break;
                    
                case "G":
                    RegistrationForm.Logout();
                    status = false;
                    break;
                    
                case "H":
                    RegistrationForm.Register();
                    status = false;
                    break;
                    
                default:
                    System.out.println("Please enter correct choice");
                }
        }
    }
    
    
   
    //Call the displayVideo method to display video choosen by the user
    public static void displayVideos(int video_id){
        System.out.println("Displaying videos");
    }
    
    //call the call Login method the allow the user to login
    public static void Login(){
        System.out.println("Login");
    }
    
    //call the call Logout method to allow the user to logout
    public static void Logout(){
        System.out.println("Logout");
    }
    //call the call Register method to allow the user to register an Yuu-Tube account
    
    public static void Register(){
        System.out.println("Register");
    }
    
    //method for the user the search for videos manually
    public static void search(){
        System.out.println("Search");
    }
    
    
 
    public static void main(String[] args) {
        displayHomepage();
        userOperations();
          Playlist.addVideo(10);
          
    }
}
