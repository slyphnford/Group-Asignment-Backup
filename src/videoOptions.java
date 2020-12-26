/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 15-P248TX
 */
import java.util.Formatter;
import java.sql.*;
import java.util.Scanner;

public class videoOptions{
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
    
    
    //Display videos format in Homepage
    public static void displayVideosFormat(){
        
        //use formatter to specify the video format
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
                      Homepage.displayVideos(videoIdList[user_choice-1]);
                      status = false;
                      break;
                      
                  case 2:
                      Homepage.displayVideos(videoIdList[user_choice-1]);
                      status = false;
                      break;
                      
                  case 3:
                      Homepage.displayVideos(videoIdList[user_choice-1]);
                      status = false;
                      break;
                      
                  case 4:
                      Homepage.displayVideos(videoIdList[user_choice-1]);
                      System.out.println("call displayVideo method");
                      status = false;
                      break;
                      
                  case 5:
                      Homepage.displayVideos(videoIdList[user_choice-1]);
                      status = false;
                      break;
                      
                  default:
                      System.out.println("Please enter correct input");
                      System.out.println("");
              }   
          }
    }
}
