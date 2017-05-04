/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;
import com.mmocore.constants.ConsoleMessageType;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author draks
 */
public class DatabaseAPI {
    
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static Connection connection;
    
    private static void connect() {
        try {
            Class.forName(driverClass);            
            ForgeAPI.sendConsoleEntry("Loaded MySQL Driver: " + driverClass, ConsoleMessageType.INFO);
            connection = DriverManager.getConnection(connectionString, username, password);
            ForgeAPI.sendConsoleEntry("Connected to MySQL Database: " + connectionString, ConsoleMessageType.INFO);
        } catch (ClassNotFoundException | SQLException e) {
            ForgeAPI.sendConsoleEntry("Failed to connect to database, error: " + e.getMessage(), ConsoleMessageType.FATAL);
        }
    }
    
    public static ResultSet query(String query) {
        if (!DatabaseAPI.isConnected()) DatabaseAPI.connect();
        PreparedStatement statement = DatabaseAPI.getPreparedStatement(query);
        ResultSet result = null;
        try {
            if (query.startsWith("INSERT") || query.startsWith("UPDATE") || query.startsWith("DELETE") || query.startsWith("CREATE")) {
                statement.execute();
            } else {
                result = statement.executeQuery();
            }
        } catch (Exception e) {
            ForgeAPI.sendConsoleEntry("Failed database query: " + e.getMessage(), ConsoleMessageType.WARNING);
        }
        return result;
    }
    
    public static boolean tableExists(String table) {
        try {
            if (!isConnected()) connect();
            DatabaseMetaData dbm = getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, table, null);
            return tables.next();
        } catch (SQLException e) {
            ForgeAPI.sendConsoleEntry("Failed to check database for table: " + table + ", error: " + e.getMessage(), ConsoleMessageType.WARNING);
            return false;
        }
    }
    
    private static Connection getConnection() {
        return DatabaseAPI.connection;
    }
    
    private static boolean isConnected() {
        try {
            return DatabaseAPI.getConnection() != null && !DatabaseAPI.getConnection().isClosed();
        } catch (SQLException e) {
            ForgeAPI.sendConsoleEntry("Failed to check database connectivity, error: " + e.getMessage(), ConsoleMessageType.FATAL);
        }
        return false;
    }
    
//    private static void disconnect() {
//        try {
//            getConnection().close();
//        } catch (Exception e) {
//            ForgeAPI.sendConsoleEntry("Failed to close connection, error: " + e.getMessage(), ConsoleMessageType.FATAL);
//        }
//    }
    
    private static PreparedStatement getPreparedStatement(String query) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseAPI.getConnection().prepareStatement(query);
        } catch (Exception e) {
            ForgeAPI.sendConsoleEntry("Failed to generate PreparedStatement for : " + query, ConsoleMessageType.SEVERE);
        }
        return statement;
    }

}
