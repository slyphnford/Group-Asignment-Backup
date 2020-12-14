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
import java.util.ArrayList;
import java.util.Scanner;
 public class Playlist {
    
    public static final Scanner scan = new Scanner(System.in);
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/yuutube?"
            + "zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String USER = "root";
    public static final String PASS = "";
    public static Connection conn = null;
    public static PreparedStatement stmt = null;
    public static Statement statement = null;
    
    
    public static void getUserId(){
        int user_id = NewClass2.userId();
        System.out.println(user_id);
    }
   
    /*call the displayPlaylist method to display the user's available playlist, 
    need to receive user id as parameter*/
    public static void displayPlaylist(int user_id){
        
        //Initialise playListIdList array to be used for storing the playlist_id
//        ArrayList<Integer> playlistIdList = new ArrayList<Integer>();
//        ArrayList<String> playlistNameList = new ArrayList<String>();
//        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT playlist_name, playlist_id FROM playlistuser "
                    + "WHERE user_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("");
            
            //start to display user's available playlist
            System.out.println("Hello user, you have the following playlist");
            int count = 1;
            while(rs.next()){
//                playlistIdList.add(rs.getInt("playlist_id"));
                String playlistName = rs.getString("playlist_name");
                System.out.println(count + "." + " " + playlistName);
                count += 1;
                
            }
            
            PlaylistOperation(user_id);
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
  
    //Method to let the user to select the playlist they want to watch
    public static void PlaylistOperation(int user_id){
        
        ArrayList<Integer> playlistIdList = new ArrayList<Integer>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT playlist_id FROM playlistuser "
                    + "WHERE user_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("");
            
            while(rs.next()){
                playlistIdList.add(rs.getInt("playlist_id"));
                
            }
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
        
         //Start to prompt user to choose the playlist they want to watch
            System.out.println("Please select the above playlist to watch your "
                    + "playlist videos [1-" + (playlistIdList.size()) + "]");
            
            //Start looping to check user's input
            while(true){
                System.out.print("Please select: ");
                int user_input = scan.nextInt();
                scan.nextLine();
                /*If the user's input is in the range, call userPlaylistVideo 
                with playlist_id as argument
                */
         
                if(user_input > 0 && user_input <= (playlistIdList.size())){
                    displayPlaylistVideo(playlistIdList.get(user_input-1));
                    break;
                }
                else{
                    System.out.println("Please enter correct input");
                }
            }
    }
    
     //Method for users to perform general actions on playlist
    public static void PlaylistGeneralOperation(int user_id){
        System.out.println("\nYou can perform the following actions on your "
                + "playlists");
        System.out.println("A. Watch the videos in the playlist\nB. Create new playlist"
                + "\nC. Delete playlist\nD. Delete video in the playlist");
        System.out.println("");
        
        boolean status = true;
        while(status){
            System.out.print("Please select: ");
            String user_input = scan.nextLine();
            switch(user_input){
                case "A":
                    checkPlaylist(user_id);
                    status = false;
                    break;
                case "B":
                    createPlaylist(user_id);
                    status = false;
                    break;
                case "C":
                    deletePlaylist(user_id);
                    status = false;
                    break;
                case "D":
                    deletePlaylistVideo(user_id);
                    status = false;
                    break;
                default:
                    System.out.println("Please enter correct input");
                    status = true;
            }
        }
    }
    
    //To check the user got any playlist or not, need to receive user id as parameter
    public static void checkPlaylist(int user_id){
        
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT user_id FROM playlistuser WHERE EXISTS"
                    + "(SELECT user_id FROM playlistuser WHERE user_id=?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            
            boolean checkPlaylist= false;
            while(rs.next()){
                //rs.getBoolean will return true if the user_id exist
                checkPlaylist =rs.getBoolean("user_id");
            }
            rs.close();
            
            //If user id exist in playlistuser Database, call displayPlaylist()
            if(checkPlaylist){
                displayPlaylist(user_id);
            }
            /*If the user id does not exist, prompt the user to create playlist
            or go back to homepage*/
            else{
                System.out.print("\nIt seems that you don't have any playlist yet,"
                        + "do you want to create a playlist? (Y/N) ");
                String user_input = scan.nextLine();
                if(user_input.equalsIgnoreCase("Y")){
                    createPlaylist(user_id);
                }
                else{
                    Homepage.userOperations();
                }
                
            }
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
    //Display the videos inside the user's playlist
    public static void displayPlaylistVideo(int playlist_id){
        
        Scanner scan = new Scanner(System.in);
        /*Initalise videoIdList to store the video_id that will be passed to 
        displayVideo method
        */
        ArrayList<Integer> videoIdList = new ArrayList<Integer>();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT video.video_id, video.video_title, "
                    + "video.video_views, video.video_duration, "
                    + "video.uploaded_time, video.uploader_name FROM "
                    + "video INNER JOIN playlistvideo ON "
                    + "playlistvideo.video_id=video.video_id WHERE playlist_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playlist_id);
            ResultSet rs = stmt.executeQuery();
            
            //display the video format before displaying available video
            Homepage.displayVideosFormat();
            
            //Start to display videos insides user playlist
            int count  = 1;
            while(rs.next()){
                Formatter f2 = new Formatter();
                videoIdList.add(rs.getInt("video_id"));
                String title = rs.getString("video_title");
                int view = rs.getInt("video_views");
                String duration = rs.getString("video_duration");
                String uploadedTime = rs.getString("uploaded_time");
                String uploaderName = rs.getString("uploader_name");
                f2.format("%d%60s%20d%25s%25s%25s", count, title, view, duration, 
                        uploadedTime, uploaderName);
                System.out.println(f2);
                System.out.println("");
                count += 1;
                
                playlistVideoOperations(videoIdList);
           }
            //Exception to handle error occured when retrieving data 
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null){
                }
                    conn.close();
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
    
    public static void playlistVideoOperations(ArrayList<Integer> videoIdList){
        //Prompt the user to give choose the video wanted to watch
            while(true){
                System.out.print("Please select[1-" + videoIdList.size() + 
                        "] to watch the "+ "videos in this playlist or press "
                        + "0 to exit: ");
                int user_input = scan.nextInt();
                scan.nextLine();

                /*if user_input is within the range, call displayVideos method 
                with video_id as parameter
                */
                if(user_input >= 1 && user_input <= videoIdList.size()){
                    Homepage.displayVideos(videoIdList.get(user_input-1));
                    break;
                }
                
                //if user_input is zero, go back to homepage
                else if(user_input == 0){
                    System.out.println("");
                    Homepage.userOperations();
                     break;
                    
                }
                //if input out of the range, prompt the user over and over again
                else{
                    System.out.println("Please enter correct input");
                    System.out.println("");
                }
            }
    }
    
    public static void createPlaylist(int user_id){
//        displayPlayList(user_id);

        //Prompt the user to give a name for the new playlist
        System.out.print("Please enter the name for your new playlist: ");
        String playlist_name = scan.nextLine();
        
        //Start to create the new playlist
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO playlistuser (playlist_id, playlist_name, "
                    + "user_id) VALUES (playlist_id, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, playlist_name);
            stmt.setInt(2, user_id);
            stmt.executeUpdate();
            System.out.printf("Your new playlist \'%s\' has been created", playlist_name);
            System.out.println("");
            System.out.println("");
            //After created the playlist, go back to homepage
//            Homepage.userOperations();
            displayPlayList(user_id);
            System.out.println("Do you want to create another playlist?");
            System.out.println("Please enter Y to create another playlist or"
                    + " enter E to exit");
            stop:while(true){
                System.out.print("Please enter your options: ");
                String user_input = scan.nextLine();
                switch(user_input){
                    case "Y":
                        createPlaylist(user_id);
                        break stop;
                    case "E":
                        System.out.println("");
                        Homepage.userOperations();
                    default:
                        System.out.println("Please enter correct input");
                }
            }
            
            
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
    //Method called by the deletePlayList() to display the users available playlist
   public static void displayPlayList(int userId){
       //Display the user's available playlist
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT playlist_name, playlist_id FROM playlistuser "
                    + "WHERE user_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("");
            
            //Display the playlist where the user can choose to delete
            System.out.println("Hello user, you have the following playlist");
            int count = 1;
            while(rs.next()){
                String playlistName = rs.getString("playlist_name");
                System.out.println(count + "." + " " + playlistName);
                count += 1;
                
            }
            rs.close();
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
    
    //Help user to delete playlist, this method need to receive user id
   }
    public static void deletePlaylist(int user_id){
        
        //playlistIdList is initalised to store the playlist_id of the user
        ArrayList<Integer> playListIdList = new ArrayList<Integer>();
        //playListName is initialised to store the playlist_name of the user
        ArrayList<String> playListName = new ArrayList<String>();
      
        
        /*Retrive the playlist's name and playlist's id from the database and store
        in the array list*/
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT playlist_name, playlist_id FROM playlistuser "
                    + "WHERE user_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("");
            
            while(rs.next()){
                playListIdList.add(rs.getInt("playlist_id"));
                playListName.add(rs.getString("playlist_name"));
                String playlistName = rs.getString("playlist_name");
            }
            rs.close();
            
            //call the displayPlsyList method to display user's available playList
            displayPlayList(user_id);
            
            //Start the playlist deletion process
            System.out.println("Please select the above playlist to be deleted"
                    + "[1-" + playListIdList.size() + "]");
            
            while(true){
                System.out.print("Please select: ");
                int user_input = scan.nextInt();
                scan.nextLine();
                if(user_input >= 1 && user_input <= playListIdList.size()){
                    
                    /*declare playlist_id to retrieve the id of the playlist the 
                    user wish to delete*/
                    int playlist_id = playListIdList.get(user_input-1);
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    sql = "DELETE FROM playlistuser WHERE playlist_id=?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, playlist_id);
                    stmt.executeUpdate();
                    
                    sql = "DELETE FROM playlistvideo WHERE playlist_id=? ";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, playlist_id);
                    stmt.executeUpdate();
                    
                    System.out.printf("Your playlist \'%s\' has been deleted", 
                            playListName.get(user_input-1));
                    System.out.println("");
                    break;
                }
                else{
                    System.out.println("Please enter correct input");
                }
            }
            displayPlayList(user_id);
            System.out.println("Do you want to delete another playlist?");
            System.out.println("Please enter \'Y\' to delete another playlist or "
                    + "enter \'E\' to exit");
            stop: while(true){
                System.out.print("Please select: ");
                String user_input = scan.nextLine();
                
                switch(user_input){
                    case "Y":
                        deletePlaylist(user_id);
                        break stop;
                    case "E":
                        System.out.println("");
                        Homepage.userOperations();
                        break;
                    default:
                        System.out.println("Please enter correc input");
                            
                }
            rs.close();
            }
        
            
            
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
    
    //Method to allow users the delete video stored in the playlist
    public static void deletePlaylistVideo(int user_id){
        //Initialise playListIdList array to be used for storing the playlist_id
        ArrayList<Integer> playlistIdList = new ArrayList<Integer>();
        
        //Initialise videoIdList array to be used for storing the video_id
        ArrayList<Integer> videoIdList = new ArrayList<Integer>();
        
        //Initialise videoNameList array to be used for storing the video_name
        ArrayList<String> videoNameList = new ArrayList<String>();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT playlist_name, playlist_id FROM playlistuser "
                    + "WHERE user_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("");
            
            //start to display user's available playlist
            System.out.println("Hello user, you have the following playlist");
            int count = 1;
            while(rs.next()){
                playlistIdList.add(rs.getInt("playlist_id"));
                String playlistName = rs.getString("playlist_name");
                System.out.println(count + "." + " " + playlistName);
                count += 1;
                
            }
            
            //Start to prompt user to choose the video playlist they want to delete
            System.out.println("Please select the above playlist to delete your "
                    + "playlist videos [1-" + (count-1) + "]");
            
            //Start looping to check user's input
            while(true){
                System.out.print("Please select: ");
                int user_input = scan.nextInt();
                scan.nextLine();
                
                /*If the user's input is in the range,display the user 
                available video
                */
         
                if(user_input > 0 && user_input <= (count-1)){
                    sql = "SELECT video.video_id, video.video_title, video.video_views, "
                            + "video.video_duration, video.uploaded_time, "
                            + "video.uploader_name, video.category FROM video INNER JOIN "
                            + "playlistvideo ON playlistvideo.video_id=video.video_id "
                            + "WHERE playlist_id=?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, playlistIdList.get(user_input-1));
                    rs = stmt.executeQuery();
            
                    //display the video format before displaying available video
                    Homepage.displayVideosFormat();
            
                    //Start to display videos insides user playlist
                    count  = 1;
                    while(rs.next()){
                        
                    Formatter f2 = new Formatter();
                    String title = rs.getString("video_title");
                    String duration = rs.getString("video_duration");
                    String uploadedTime = rs.getString("uploaded_time");
                    String uploaderName = rs.getString("uploader_name");
                    String category = rs.getString("category");
                    int view = rs.getInt("video_views");
                    videoIdList.add(rs.getInt("video_id"));
                    videoNameList.add(title);
                    f2.format("%d%60s%20d%25s%25s%25s%25s", count, title, view, 
                            duration, uploadedTime, uploaderName, category);
                    System.out.println(f2);
                    System.out.println("");
                    count += 1;
                    
                    }
                    break;
                }
                else{
                    System.out.println("Please enter correct input");
                }
            
                rs.close();
            }
            
            System.out.println("Please select the videos you wanted to delete[1-"
                    + ""+ (count-1) + "]");
            
            //Start looping to check user's choice
            while(true){
                System.out.print("Please select: ");
                int user_input = scan.nextInt();
                scan.nextLine();
                /*If the user input is in the range, delete the video selected 
                by the user*/
                if(user_input >= 1 && user_input <= count-1){
                    
                    sql = "DELETE FROM playlistvideo WHERE video_id=?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, videoIdList.get(user_input-1));
                    stmt.executeUpdate();
                    System.out.printf("Your video \'%s\' has been deleted", 
                            videoNameList.get(user_input-1));
                    System.out.println("");
                    break;
                    
                }
                
                //Repeat the loop if user enter wrong input
                else{
                    System.out.println("Please enter correct input");
                }
            }
            
            System.out.println("");
            System.out.println("Do you want to delete another video in your playlist?");
            System.out.println("Please enter \'Y\' to delete video in your playlist or"
                    + " enter \'E\' to exit");
            stop:while(true){
                System.out.print("Please select: ");
                String user_input = scan.nextLine();
                switch(user_input){
                    case "Y":
                        System.out.println("");
                        deletePlaylistVideo(user_id);
                        break stop;
                    case "E":
                        System.out.println("");
                        Homepage.userOperations();
                        break stop;
                    default:
                        System.out.println("Please enter correct input");
                }
            }
            
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
    
    public static void addVideo(int playlist_id, int video_id){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO playlistvideo(playlist_id, video_id) VALUES"
                    + "(?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playlist_id);
            stmt.setInt(2, video_id);
            stmt.executeUpdate();
            
            
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
}

