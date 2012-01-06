/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes;

//import com.manuwebdev.ghomes.Commands.CommandProcessor;
import com.manuwebdev.ghomes.Commands.GHomesCommandExecutor;
import com.manuwebdev.ghomes.IO.MYSQLInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static final String VERSION = "0.1b";
    /**
     * Load Plugin Manager
     */
    PluginManager pm = this.getServer().getPluginManager();
    /**
     * Plugin
     */
    public GHomes plugin;
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
        plugin = this;
        
        //Get COnfiguration
        config = getConfig();
        
        //MYSQL Configuration
        String host=config.getString("mysql-host");
        int port=config.getInt("mysql-port");
        
        
        MYSQLInterface mysql=new MYSQLInterface();
        //register commandexecutor
        GHomesCommandExecutor executor= new GHomesCommandExecutor();
	getCommand("home").setExecutor(executor);
        getCommand("sethome").setExecutor(executor);
        getCommand("deletehome").setExecutor(executor);
    }
}
