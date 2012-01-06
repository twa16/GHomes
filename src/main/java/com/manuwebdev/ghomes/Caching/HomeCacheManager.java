/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Caching;

import com.manuwebdev.ghomes.Home;
import com.manuwebdev.ghomes.IO.MYSQLActions;
import com.manuwebdev.ghomes.IO.MYSQLInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class HomeCacheManager {

    /**
     * Hashmap that contains the player's homes and their homelists
     * Key = Player
     * Value = PlayerHomeList object
     */
    private Map<Player, PlayerHomeList> HomeLists = new HashMap<Player, PlayerHomeList>();
    /**
     * Interface with MYSQL server
     */
    MYSQLInterface mysqlInterface;
    /**
     * Wrapper for MYSQLInterface that provides access
     * to queries that the plugin needs.
     */
    MYSQLActions mysqlActions;

    /**
     * Constructor for the HomeCaching system
     * @param mysqlInterface Interface with MYSQL server
     */
    public HomeCacheManager(MYSQLInterface mysqlInterface) {
        this.mysqlInterface = mysqlInterface;
        mysqlActions = new MYSQLActions(mysqlInterface);
    }

    public void cacheHomes() {
        //Get all homes from MYSQL database
        ArrayList<Home> Homes = mysqlActions.getAllHomes();
        
        //Loop through array
        for (int i = 0; i < Homes.size(); i++) {
            //Check if the user is already in the cache
            if(HomeLists.containsKey(Homes.get(i).getOwner())){
                //Get PlayerHomeList
                PlayerHomeList homelist=HomeLists.get(Homes.get(i).getOwner());
                //Add home to the list
                homelist.addHome(Homes.get(i).getHomeName(), Homes.get(i));
            }
            //If user does not have PlayerHomeList then give them one
            else{
                //Create PlayerHomeList instance
                PlayerHomeList newplayerhomelist=new PlayerHomeList(Homes.get(i).getOwner());
                //add it to the cache
                HomeLists.put(Homes.get(i).getOwner(), newplayerhomelist);
            }
        }
    }
    
    /**
     * Gets PlayerHomeList for a player. This method returns null if 
     * the Player does not have a PlayerHomeList.
     * @param player Player to get list for
     * @return PlayerHomeList or null if it does not exist.
     */
    public PlayerHomeList getPlayerHomeList(Player player){
        //Check if it exists
        if(HomeLists.containsKey(player)){
            //Return Homelist
            return HomeLists.get(player);
        }
        else{
            //Return null if it doesnt exist
            return null;
        }
    }
}
