/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Commands;

import com.manuwebdev.ghomes.Caching.HomeCache;
import com.manuwebdev.ghomes.Home;
import com.manuwebdev.ghomes.IO.MYSQLActions;
import com.manuwebdev.ghomes.IO.MYSQLInterface;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
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
    /**
     * Permission for /sethome
     */
    private final String SETHOME_PERMISSION="ghomes.sethome";
    
    /**
     * Permission for /home
     */
    private final String HOME_PERMISSION="ghomes.home";
    
    /**
     * Permission for /deletehome
     */
    private final String DELETEHOME_PERMISSION="ghomes.deletehome";
    
     /**
     * Permission for /deletehome
     */
    private final String ADMIN_PERMISSION="ghomes.admin";
    
    /**
     * Interface to MYSQL Database
     */
    private MYSQLInterface mysqlInterface;
    
    /**
     * Methods for manipulating MYSQL data
     */
    private MYSQLActions mysqlActions;
    
    /**
     * Color to use for messsages
     */
    private ChatColor MessageColor=ChatColor.AQUA;
    
    /**
     * Caching of homes for use with nearbyhomes
     */
    private HomeCache hcm;
    public GHomesCommandExecutor(MYSQLInterface mysqlInterface, HomeCache hcm){
        this.mysqlInterface=mysqlInterface;
        mysqlActions = new MYSQLActions(this.mysqlInterface);
        this.hcm=hcm;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = null;

        //Check if the sender is a player of the console
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        // <editor-fold defaultstate="collapsed" desc="/sethome">
        if (cmd.getName().equalsIgnoreCase("sethome")) { // If the player typed /sethome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(SETHOME_PERMISSION)||player.isOp()){
                    //Create hoem obejct form user input
                    Home home = new Home(player, args[0], player.getLocation());
                    //Add home to the MYSQL database
                    mysqlActions.addHome(home);
                    //Add home to cache
                    hcm.addHometoCache(home);
                    //Send confirmation message
                    player.sendMessage(MessageColor+"Home Added");
                }
                
            }
            return true;

        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="/home command">
        if (cmd.getName().equalsIgnoreCase("home")) { // If the player typed /home then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(HOME_PERMISSION)||player.isOp()){
                    // <editor-fold defaultstate="collapsed" desc="Get Homes Directly from MYSQL">
                    /*
                    
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
                    */
                    // </editor-fold>
                    
                    //Get home from cache
                    Home home=hcm.getHomeByName(args[0]);
                    //If hoem is null then it does not exist
                    if(home != null&&home.getOwner().getName().equals(player.getName())){
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
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="/deletehome">
        if (cmd.getName().equalsIgnoreCase("deletehome")) { // If the player typed /deletehome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(DELETEHOME_PERMISSION)||player.isOp()){
                    //Attempt to delete home from MYSQL server
                    mysqlActions.deleteHome(args[0], player);
                    
                     //Send a message to the player even if the home didn't exist in the database :P
                     
                    player.sendMessage(MessageColor+"Home "+args[0]+" was deleted.");
                }
                
            }
            return true;

        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="/nearbyhomes">
        if (cmd.getName().equalsIgnoreCase("nearbyhomes")) { // If the player typed /sethome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(HOME_PERMISSION)||player.isOp()){
                    ArrayList<Home> homes=NearbyHomes.getMyNearbyHomes(player, Integer.parseInt(args[0]), hcm);
                    player.sendMessage(MessageColor+"-----Homes Within "+args[0]+" Blocks-----");
                    for(Home home:homes){
                        player.sendMessage(MessageColor+"   "+home.getHomeName());
                    }
                    
                }
                
            }
            return true;

        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="/allnearbyhomes">
        if (cmd.getName().equalsIgnoreCase("allnearbyhomes")) { // If the player typed /sethome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(ADMIN_PERMISSION)||player.isOp()){
                    int radius=4;
                    if(args.length>0) radius=Integer.parseInt(args[0]);
                    ArrayList<Home> homes=NearbyHomes.getALLNearbyHomes(player, Integer.parseInt(args[0]), mysqlActions);
                    player.sendMessage(MessageColor+"-----All Homes Within "+radius+" Blocks-----");
                    for(Home home:homes){
                        player.sendMessage(MessageColor+"   "+home.getHomeName());
                    }
                    
                }
                
            }
            return true;

        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="/playerhomes">
        if (cmd.getName().equalsIgnoreCase("nearbyplayerhomes")) { // If the player typed /sethome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(ADMIN_PERMISSION)||player.isOp()){
                    ArrayList<Home> homes=NearbyHomes.getPlayersNearbyHomes(player, Integer.parseInt(args[0]), hcm, Bukkit.getPlayer(args[1]));
                    player.sendMessage(MessageColor+"-----"+args[1]+"'s Homes Within "+args[0]+" Blocks-----");
                    for(Home home:homes){
                        player.sendMessage(MessageColor+"   "+home.getHomeName());
                    }
                    
                }
                
            }
            return true;

        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="/homelist">
        if (cmd.getName().equalsIgnoreCase("homelist")) { // If the player typed /sethome then do the following...
            
            //If player is null then the command was sent from console
            if (player == null) {
                sender.sendMessage("this command can only be run by a player");
            } 
            
            //If player is not null a player sent it
            else {
                //check if player has permission
                if(player.hasPermission(HOME_PERMISSION)||player.isOp()){
                    ArrayList<Home> homes=hcm.getAllPlayerHomes(player);
                    player.sendMessage(MessageColor+"-----Your Homes-----");
                    for(int i=0;i<homes.size();i++){
                        player.sendMessage(MessageColor+"   "+homes.get(i).getHomeName());
                    }
                    
                }
                
            }
            return true;

        }
        // </editor-fold>
        
        
        
        //Our commands were not executed
        return false;
    }
}
