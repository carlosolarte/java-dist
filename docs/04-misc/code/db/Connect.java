/**
 * An example using JDBC
 * This example requires SQLite installed
 * and the driver sqlite-jdbc-3.36.0.3.jar
 * Compilation and execution with the option -cp .:./sqlite-jdbc-3.36.0.3.jar
 */

import java.sql.*;
import java.util.Random;

public class Connect {
    // Name of the file with the data
	public static final String database="mydatabase.db";

    /**
     * A connection to the database
     */
    private static Connection conn;

    /**
     * Opening the connection
     */
    public static void connect() throws SQLException {
        String url = "jdbc:sqlite:" + Connect.database;
        conn = DriverManager.getConnection(url);
        System.out.println("Connection OK");
    }

    /**
     * Closing the connection
     */
    public static void close() throws SQLException {
        conn.close();
        System.out.println("Disconnected OK");
    }

    /**
     * An example of a code creating a table in the database
     */
    public static void createTable()throws SQLException{
         Statement stmt = conn.createStatement();
         String sql = "CREATE TABLE IF NOT EXISTS PEOPLE( " + 
                     " ID INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                     " NAME TEXT NOT NULL, " + 
                     " AGE INT NOT NULL);" ;

         stmt.execute(sql);
         System.out.println("Table PEOPLE created.");

    }

    /**
     * Adding some people to the database
     */
    public static void addPeople() throws SQLException{
         PreparedStatement stmt = conn.prepareStatement("INSERT INTO PEOPLE(NAME, AGE) VALUES (?,?)");
         Random R = new Random();
         for(int i=1;i<=10;i++){
             stmt.setString(1, "Person " + i);
             stmt.setInt(2, R.nextInt(100));
             int row = stmt.executeUpdate();
             System.out.println(row + " row(s) updated");
         }

    }

    /** 
     * Listing the people in the database
     */
    public static void list() throws SQLException{
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("SELECT ID, NAME, AGE FROM PEOPLE");
        while (rs.next()){
            System.out.println(rs.getInt("ID") + ". " + rs.getString("NAME") + " " + rs.getInt("AGE") + " years old.");
        }
    }
    public static void main(String[] args) {
        try{
            connect();
            createTable();
            addPeople();
            list();
            close();
        }
        catch (SQLException E){
            E.printStackTrace();
        }
    }
}
