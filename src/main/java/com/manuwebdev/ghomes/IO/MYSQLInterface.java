/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.IO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Gauto
 */
public class MYSQLInterface {

    /**
     * JDBC URL
     */
    private String url;
    
    /**
     * User for MYSQL
     */
    private String user = "testuser";
    
    /**
     * Password for MYSQL
     */
    private String password = "test623";
    
    /**
     * Host address of MYSQL server
     */
    private String host;
    
    /**
     * MYSQL Port
     */
    private int port=3306;
    
    /**
     * Schema to access
     */
    private String Schema;
    
    /**
     * Connection to MYSQL
     */
    private Connection connection;
    
    /**
     * Logger to print out to Server terminal
     */
    private Logger log;
    
    /**
     * Prefix that will be added to table names
     */
    private String prefix;
    
    private final String PLUGIN_NAME="GHomes";
    
    /**
     * Constructor for the MYSQLInterface
     * 
     * @param host Address of MYSQL server
     * @param port Port to use
     * @param user User to connect as
     * @param password Password to use
     * @param Schema Database to access
     * @param log Log object used to write to the server console
     * @param prefix Prefix to be added to the name of every table
     */
    public MYSQLInterface(String host, int port, String user, String password, String Schema, Logger log, String prefix) {
        //Set logger for class
        this.log=log;
        
        //Set prefix
        this.prefix=prefix;
        
        //Load JDBC driver for MYSQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            log.log(Level.INFO, "["+PLUGIN_NAME+"] JDBC Loaded");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MYSQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            log.log(Level.SEVERE, "\n["+PLUGIN_NAME+"] JDBC Loading Failed.");
        }
        
        //Generate JDBC URL
        this.url="jdbc:mysql://"+host+":"+String.valueOf(port)+"/"+Schema;
        
        //Conection to MYSQL and get Connection object
        log.log(Level.INFO, "\n["+PLUGIN_NAME+"] Connecting to MYSQL Server.");
        try {
            //Initialize Connection Object
            this.connection = DriverManager.getConnection(url,user,password);
            
            //Inefficent Concatenation :)
            log.log(Level.INFO, "\n["+PLUGIN_NAME+"] Connected to " + host);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            log.log(Level.SEVERE, "\n["+PLUGIN_NAME+"] MYSQL Connection Failed.");
        }
    }
    
    /**
     * Returns Connection object representing the
     * connection to the MYSQL server
     * @return Connection to MYSQL server
     */
    public Connection getMYSQLConnection(){
        return connection;
    }   
    
    /**
     * Returns the prefix that should be added to the 
     * names of the table
     * @return Table name prefix
     */
    public String getPrefix(){
        return prefix;
    }
    
}
