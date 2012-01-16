/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.Over9000HomeImport.IO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Gauto
 */
public class ImportingMYSQLInterface {

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
     * Returns Connection object representing the
     * connection to the MYSQL server
     * @return Connection to MYSQL server
     */
    public Connection getMYSQLConnection(){
        return connection;
    }   
    
    public void setConnection(Connection conn){
        this.connection=conn;
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
