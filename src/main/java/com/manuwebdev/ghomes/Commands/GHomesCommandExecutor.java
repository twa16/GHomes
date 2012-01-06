/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Commands;

import com.manuwebdev.ghomes.Home;
import com.manuwebdev.ghomes.IO.MYSQLActions;
import com.manuwebdev.ghomes.IO.MYSQLInterface;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class GHomesCommandExecutor implements CommandExecutor {
    private final String SETHOME_PERMISSION="ghomes.sethome";
    private final String HOME_PERMISSION="ghomes.home";
    private final String DELETEHOME_PERMISSION="ghomes.deletehome";
    private MYSQLInterface mysqlInterface;
    private MYSQLActions mysqlActions;
    private ChatColor MessageColor=ChatColor.DARK_BLUE;
    
    
    public GHomesCommandExecutor(MYSQLInterface mysqlInterface){
        this.mysqlInterface=mysqlInterface;
        mysqlActions = new MYSQLActions(mysqlInterface);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = null;

        //Check if the sender is a player of the console
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        
        if (cmd.getName().equalsIgnoreCase("sethome")) { // If the player typed /sethome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(SETHOME_PERMISSION)){
                    //Create hoem obejct form user input
                    Home home = new Home(player, args[0], player.getLocation());
                    //Add home to the MYSQL database
                    mysqlActions.addHome(home);
                    //Send confirmation message
                    player.sendMessage(MessageColor+"Home Added");
                }
                
            }
            return true;

        }
        
        if (cmd.getName().equalsIgnoreCase("home")) { // If the player typed /home then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(HOME_PERMISSION)){
                    //Get hoem form myslq
                    Home home=mysqlActions.getHome(args[0], player);
                    //If hoem is null then it does not exist
                    if(home != null){
                        player.teleport(home.getHomeLocation());
                        player.sendMessage(MessageColor+"Teleported to "+home.getHomeName());
                    }
                    else{
                        player.sendMessage(MessageColor+"Home Does Not Exist");
                    }
                }
                
            }
            return true;

        }
        
        if (cmd.getName().equalsIgnoreCase("deletehome")) { // If the player typed /deletehome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(DELETEHOME_PERMISSION)){
                    //Attempt to delete home from MYSQL server
                    mysqlActions.deleteHome(args[0], player);
                    
                     //Send a message to the player even if the home didn't exist in the database :P
                     
                    player.sendMessage(MessageColor+"Home "+args[0]+" was deleted.");
                }
                
            }
            return true;

        }
        
        //Our commands were not executed
        return false;
    }
}
