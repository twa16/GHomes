/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes;

import com.manuwebdev.ghomes.Commands.CommandProcessor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Manuel Gauto
 */
public class GHomes extends JavaPlugin{
    
    /**
     * GHomes Version
     */
    public static final String VERSION="0.1b";
    
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
    
    public void onDisable() {
        log.log(Level.INFO, "[GHomes "+VERSION+"] Disabled!");
    }

    public void onEnable() {
        log.log(Level.INFO, "[GHomes "+VERSION+"] Enabled!");
        plugin=this;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	CommandProcessor cp=new CommandProcessor();
        return cp.processCommand(sender, cmd, commandLabel, args);
}

}
