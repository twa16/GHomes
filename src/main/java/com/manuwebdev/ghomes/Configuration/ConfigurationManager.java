/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Manuel Gauto
 */
public class ConfigurationManager {
    /**
     * Configuration file object
     */
    private FileConfiguration config;
    
    public ConfigurationManager(FileConfiguration config){
        this.config=config;
        if(config.getString("mysql-host")==null){
            createConfigurationFile();
        }
    }
    
    /**
     * Creates default values if the file is empty
     */
    public void createConfigurationFile(){
        /*
         * mysql-host
         * mysql-port
         * mysql-user
         * mysql-password
         * mysql-database
         * mysql-prefix
         */
        config.set("mysql-host", "localhost");
        config.set("mysql-port", "3306");
        config.set("mysql-user", "MINECRAFT");
        config.set("mysql-password", "MINECRAFT");
        config.set("mysql-database", "MINECRAFT");
        config.set("mysql-prefix", "GH-");
    }
    
    
    
}
