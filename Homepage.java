/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package homepage;
//import java.sql.*;
//
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
    public static int user_id;
    
    //set the value for the user_id attribute
    public Homepage(int user_id){
        this.user_id = user_id;
    }
    //Get user ID
    public static int getUserId(){
        int user_id = NewClass2.userId();
        return user_id;
    }
    
    
    //display Homepage format 
    public static void displayHomepage(){
        System.out.println("--------------------------------------------------------------" + 
                "--------------------------------------------------------------"
                + "--------------------------------");
        System.out.println("");
        System.out.printf("%84s","Welcome to Yuu-Tube v1.0!");
        System.out.println("");
        System.out.println("");
        System.out.printf("%14s%17s%26s%21s%19s%17s%17s%19s\n", "Homepage", "Search"
                ,"Trending Videos", "Categories", "Playlist", "Login", "Logout", "Register");
        System.out.println("");
        System.out.println("--------------------------------------------------------------" + 
                "-------------------------------------------------------------"
                + "---------------------------------");
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
                    sql = "SELECT video_id, video_title, video_views, uploaded_time, "
                    + "uploader_name, video_duration, category "
                    + "FROM video ORDER BY RAND() LIMIT 5";
                    displayVideosFormat();
                    displayVideoOptions(sql);
//                    displayVideos();
                    status = false;
                    break;
                    
                case "B":
                    search();
                    status = false;
                    break;
                    
                case "C":
                    sql = "SELECT video_id, video_title, video_views, uploaded_time, "
                    + "uploader_name, video_duration, category "
                    + "FROM video ORDER BY ABS(video_views) DESC LIMIT 5";
                    displayVideosFormat();
                    displayVideoOptions(sql);
                    status = false;
                    break;
                    
                case "D":
                    Category.displayCategories();
                    status = false;
                    break;
                    
                case "E":
                    Playlist.PlaylistGeneralOperation(user_id);
                    status = false;
                    break;
                    
                case "F":
                    callLogin();
                    status = false;
                    break;
                    
                case "G":
                    callLogout();
                    status = false;
                    break;
                    
                case "H":
                    callRegister();
                    status = false;
                    break;
                    
                default:
                    System.out.println("Please enter correct choice");
                }
        }
    }
    
    //Display videos format in Homepage
    public static void displayVideosFormat(){
        
        //user formatter to specify the format for the Yuu-Tube options
        Formatter f1 = new Formatter();
        f1.format("\n%s%58s%20s%25s%25s%25s%25s", "No.", "Video Title","View", 
                "Duration", "Time uploaded", "Uploader", "Category");
        System.out.println(f1 + "\n");
    }
    
    /*Display 5 random videos from database, need to store the video id selected
    by the user and pass to userVideoOperations(), then pass to displayVideo() method*/
    public static void displayVideoOptions(String sql){
        int[] videoIdList = new int[5];
        String sql_command = sql;
        //try to connect to database
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql_command);
            
            //Display 5 Random videos
            int count = 1;
            while(rs.next()){
                Formatter f2 = new Formatter();
                videoIdList[count-1] = rs.getInt("video_id");
                String title = rs.getString("video_title");
                int view = rs.getInt("video_views");
                String duration = rs.getString("video_duration");
                String uploadedTime = rs.getString("uploaded_time");
                String uploaderName = rs.getString("uploader_name");
                String category = rs.getString("category");
                f2.format("%d%60s%20d%25s%25s%25s%25s", count, title, view, 
                        duration, uploadedTime, uploaderName, category);
                System.out.println(f2);
                System.out.println("");
                count += 1;
                
            }
            userVideoOperations(videoIdList);
            rs.close();
            //Exception to handle error occured when retrieving data
            
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null){
                    conn.close();
                }
            }catch(SQLException se){
            }
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        } 
    }
    
    //Allow the user to choose which video they want to watch
    public static void userVideoOperations(int[] videoIdList){
        
          boolean status = true;
          //Start looping to check user input
          while(status){
              System.out.print("Please select the video you want to watch [1-5]:");
              int user_choice = scan.nextInt();
              System.out.println("");
              
              switch(user_choice){
                  case 1:
                      System.out.println("call displayVideo method" + videoIdList[user_choice]);
                      displayVideos(videoIdList[user_choice-1]);
                      status = false;
                      break;
                      
                  case 2:
                      displayVideos(videoIdList[user_choice-1]);
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                      
                  case 3:
                      displayVideos(videoIdList[user_choice-1]);
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                      
                  case 4:
                      displayVideos(videoIdList[user_choice-1]);
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                      
                  case 5:
                      displayVideos(videoIdList[user_choice-1]);
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                      
                  default:
                      System.out.println("Please enter correct input");
                      System.out.println("");
              }   
          }
    }
    
   
    //Call the displayVideo method to display video choosen by the user
    public static void displayVideos(int video_id){
        System.out.println("Displaying videos");
    }
    
    //call the call Login method the allow the user to login
    public static void callLogin(){
        System.out.println("Login");
    }
    
    //call the call Logout method to allow the user to logout
    public static void callLogout(){
        System.out.println("Logout");
    }
    //call the call Register method to allow the user to register an Yuu-Tube account
    
    public static void callRegister(){
        System.out.println("Register");
    }
    
    //method for the user the search for videos manually
    public static void search(){
        System.out.println("Search");
    }
    
    
    
    
    public static void main(String[] args) {
        int user_id = getUserId();
        Homepage obj = new Homepage(user_id);
        Playlist.addVideo(3, 10);
//        System.out.println(obj.user_id);
        displayHomepage();
        userOperations();
    }
}
