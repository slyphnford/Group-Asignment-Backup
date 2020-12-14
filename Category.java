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
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
public class Category {
    //Initialise all of the variables below to be use for the whole program
    static final Scanner scan = new Scanner(System.in);
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/yuutube?zeroDateTimeBehavior=CONVERT_TO_NULL";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn = null;
    static PreparedStatement stmt = null;
    static Statement statement = null;
    
    
    public static void displayCategories(){
        ArrayList<String> categoryNameList = new ArrayList<String>();
        System.out.println("");
        System.out.println("Hello user we have the following video categories");
        //try to connect to database
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = conn.createStatement();
            String sql = "SELECT DISTINCT category from video";
            ResultSet rs = statement.executeQuery(sql);
            
            //Display 5 Random videos
            int count = 1;
            while(rs.next()){
                String category = rs.getString("category");
                categoryNameList.add(rs.getString("category"));
                System.out.printf("%d. %s", count, category);
                System.out.println("");
                count += 1;
                
            }
            while(true){
                System.out.print("Please select[1-" + categoryNameList.size() + "]: ");
                int user_input = scan.nextInt();
                if(user_input >= 1 && user_input <= 8){
                    displayCategoryVideo(categoryNameList.get(user_input-1));
                    break;
                }
                else{
                    System.out.println("Please enter correct input");
                }
            }
            
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
    
    public static void displayCategoryVideo(String categoryName){
        ArrayList<Integer> videoIdList = new ArrayList<Integer>();
        Homepage.displayVideosFormat();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT* FROM video WHERE category=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            
            //Display 5 Random videos
            int count = 1;
            while(rs.next()){
                Formatter f2 = new Formatter();
                videoIdList.add(rs.getInt("video_id"));
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
            categoryVideoOperation(videoIdList);
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
    
    public static void categoryVideoOperation(ArrayList<Integer> videoIdList){
        System.out.println("Please select the video you want to watch [1-" 
                + videoIdList.size() + "]");
        scan.next();
        while(true){
            System.out.print("Please select: ");
            int user_input = scan.nextInt();
            if(user_input >=1 && user_input <= videoIdList.size()){
                Homepage.displayVideos(videoIdList.get(user_input-1));
                break;
            }
            else{
                System.out.println("Please enter the correct input");
            }
        }
    }
}


