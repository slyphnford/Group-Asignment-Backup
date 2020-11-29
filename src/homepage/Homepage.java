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
public class Homepage{
    
    //display Homepage format 
    static void displayHomepage(){
        System.out.println("--------------------------------------------------------------" + 
                "--------------------------------------------------------------"
                + "--------------------------------");
        System.out.println("");
        System.out.println("\t\t\t\t\t\tWelcome to Yuu-Tube v1.0!");
        System.out.println("");
        System.out.printf("%11s%30s%24s%21s%22s%24s\n", "Homepage", "Trending Videos", 
                "Playlist", "Login", "Logout", "Register");
        System.out.println("");
        System.out.println("--------------------------------------------------------------" + 
                "-------------------------------------------------------------"
                + "---------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    
    //Allow users to perform operations on Yuu-tube page, still haven't complete yet
    static void userOperations(){
        
        //Scanner class to get the user's choice
        Scanner scan = new Scanner(System.in);
        
        //Display the options available to the users
        System.out.println("Hello user, please select\nA. To watch random videos in "
                + "the homepage\nB. To watch trending videos\nC. To go to your "
                + "playlist\nD. To login\nE. To logout\nF. To register");
        System.out.println("");
       
        /*Start looping to check the user's input, if the input out of range, 
        the loop will continue over and over again*/
        boolean status = true;
        while(status){
                System.out.printf("Please select: ");
                String user_choice = scan.nextLine();
                switch(user_choice){
                case "A":
                    displayVideosFormat();
                    displayRandomVideoOptions();
                    userVideoOperations();
                    status = false;
                    break;
                case "B":
                    System.out.println("Display trending videos");
                    status = false;
                    break;
                case "C":
                    System.out.println("Go to playlist");
                    status = false;
                    break;
                case "D":
                    System.out.println("Login");
                    status = false;
                    break;
                case "E":
                    System.out.println("Logout");
                    status = false;
                    break;
                case "F":
                    System.out.println("Register");
                    status = false;
                    break;
                default:
                    System.out.println("Please enter correct choice");
                }
        }
    }
    
    //Display videos format in Homepage
    static void displayVideosFormat(){
        Formatter f1 = new Formatter();
        f1.format("\n%s%58s%20s%25s%25s%25s", "No.", "Video Title","View", 
                "Duration", "Time uploaded", "Uploader");
        System.out.println(f1 + "\n");
    }
    
    //Display 5 random videos from database
    static void displayRandomVideoOptions(){
        
        //Initialising variables needed to access to database
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/yuutube?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USER = "root";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;
        
        //try to connect to database
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT video_title, video_views, uploaded_time, uploader_name, video_duration "
                    + "FROM homepage ORDER BY RAND() LIMIT 5";
            ResultSet rs = stmt.executeQuery(sql);
            
            //Display 5 Random videos
            int count = 1;
            while(rs.next()){
                Formatter f2 = new Formatter();
                String title = rs.getString("video_title");
                int view = rs.getInt("video_views");
                String duration = rs.getString("video_duration");
                String uploadedTime = rs.getString("uploaded_time");
                String uploaderName = rs.getString("uploader_name");
                f2.format("%d%60s%20d%25s%25s%25s", count, title, view, duration, uploadedTime, uploaderName);
                System.out.println(f2);
                System.out.println("");
                count += 1;
                
            }
            rs.close();
            //Exception to handle error occured when retrieving data 
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null){
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
    
    static void userVideoOperations(){
        Scanner scan = new Scanner(System.in);
          boolean status = true;
          while(status){
              System.out.print("Please select the video you want to watch [1-5]:");
              int user_choice = scan.nextInt();
              System.out.println("");
              
              switch(user_choice){
                  case 1:
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                  case 2:
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                  case 3:
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                  case 4:
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                  case 5:
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                  default:
                      System.out.println("Please enter correct input");
                      System.out.println("");
              }   
          }
    }
    
    
    
    public static void main(String[] args) {
        displayHomepage();
        userOperations();
//          Scanner scan = new Scanner(System.in);
//          boolean status = true;
//          while(status){
//              System.out.print("Please select the video you want to watch [1-5]:");
//              int user_choice = scan.nextInt();
//              System.out.println("");
//              
//              switch(user_choice){
//                  case 1:
//                      System.out.println("call displayVideo method");
//                      status = false;
//                      break;
//                  case 2:
//                      System.out.println("call displayVideo method");
//                      status = false;
//                      break;
//                  case 3:
//                      System.out.println("call displayVideo method");
//                      status = false;
//                      break;
//                  case 4:
//                      System.out.println("call displayVideo method");
//                      status = false;
//                      break;
//                  case 5:
//                      System.out.println("call displayVideo method");
//                      status = false;
//                      break;
//                  default:
//                      System.out.println("Please enter correct input");
//                      System.out.println("");
//              }   
//          }
    }
}
