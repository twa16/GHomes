/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes;

//import com.manuwebdev.ghomes.Commands.CommandProcessor;
import com.manuwebdev.ghomes.Caching.HomeCache;
import com.manuwebdev.ghomes.Commands.GHomesCommandExecutor;
import com.manuwebdev.ghomes.Configuration.ConfigurationManager;
import com.manuwebdev.ghomes.IO.MYSQLActions;
import com.manuwebdev.ghomes.IO.MYSQLInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Manuel Gauto
 */
public class GHomes extends JavaPlugin {

    /**
     * GHomes Version
     */
    public static final String VERSION = "1.0";
    /**
     * Load Plugin Manager
     */
    PluginManager pm = Bukkit.getServer().getPluginManager();
    /**
     * Plugin
     */
    public GHomes plugin;
    /**
     * Color to use for messsages
     */
    private ChatColor MessageColor=ChatColor.AQUA;
    /**
     * Get Minecraft Logger
     */
    private Logger log = Logger.getLogger("Minecraft");
    /**
     * Configuration file object
     */
    protected FileConfiguration config;

    public void onDisable() {
        log.log(Level.INFO, "[GHomes " + VERSION + "] Disabled!");
    }

    public void onEnable() {
        //Set notification to console
        log.log(Level.INFO, "[GHomes " + VERSION + "] Enabled!");
        
        //This is me
        plugin = this;
        

        //Get COnfiguration
        config = getConfig();

        //Create ConfigurationManager
        ConfigurationManager cm = new ConfigurationManager(config);
        saveConfig();
        //MYSQL Configuration
        String host = config.getString("mysql-host");
        int port = config.getInt("mysql-port");
        String user = config.getString("mysql-user");
        String password = config.getString("mysql-password");
        String schema = config.getString("mysql-database");
        String prefix = config.getString("mysql-prefix");


        MYSQLInterface mysql = new MYSQLInterface(host, port, user, password, schema, log, prefix);

        //Check if table exists and if it does not create it
        MYSQLActions ma = new MYSQLActions(mysql,plugin);
        ma.createTableIfNeeded();

        //Set up Cache
        final HomeCache hcm = new HomeCache(mysql, plugin);
        hcm.cacheHomes();
        log.log(Level.INFO, "[GHomes] Initial Home Caching Completed.");

        //register commandexecutor
        GHomesCommandExecutor executor = new GHomesCommandExecutor(mysql, hcm, plugin);
        getCommand("home").setExecutor(executor);
        getCommand("sethome").setExecutor(executor);
        getCommand("homelist").setExecutor(executor);
        getCommand("nearbyhomes").setExecutor(executor);
        getCommand("allnearbyhomes").setExecutor(executor);
        getCommand("nearbyplayerhomes").setExecutor(executor);
        getCommand("deletehome").setExecutor(executor);
        getCommand("import").setExecutor(executor);
        getCommand("ghcache").setExecutor(executor);

        //Make sure verison numbers match :0
        if(plugin.getDescription().getVersion().equals(VERSION)==false)log.log(Level.SEVERE,"Manuel Your version numbers do not match");
        
    }
}
