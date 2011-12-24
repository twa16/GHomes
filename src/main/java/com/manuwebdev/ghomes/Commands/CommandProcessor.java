/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Commands;

import com.manuwebdev.ghomes.IO.FileInterface;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class CommandProcessor {
    
    /**
     * Method tat checks if command is part of GHome
     * @param sender
     * @param cmd
     * @param commandLabel
     * @param args
     * @return 
     */
    public boolean processCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Player player = null;
        Location loc;
	if (sender instanceof Player) {
		player = (Player) sender;
	}
 
	if (cmd.getName().equalsIgnoreCase("basic")){ // If the player typed /basic then do the following...
		if (player == null) {//Check if command is from console
			sender.sendMessage("this command can only be run by a player");
		} else {
			loc=player.getLocation();
                        
                        
		}
		return true;
	} 
	return false;
    }
}
