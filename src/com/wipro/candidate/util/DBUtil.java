package com.wipro.candidate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    
    /**
     * Establish a connection to the database and return the java.sql.Connection reference
     * @return Connection object
     */
    public static Connection getDBConn() {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Database credentials - Update these with your actual credentials
            String url = "jdbc:mysql://localhost:3306/candidate_dbs";
            String username = "root"; // Replace with your MySQL username
            String password = "root"; // Replace with your MySQL password
            
            // Establish connection
            conn = DriverManager.getConnection(url, username, password);
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed! Check output console");
            e.printStackTrace();
        }
        
        return conn;
    }
}