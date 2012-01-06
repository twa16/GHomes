/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Caching;

import com.manuwebdev.ghomes.Home;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class PlayerHomeList {
    /**
     * Hashmap that contains the player's homes and their names
     * Key = Name of Home
     * Home = Home
     */
    private Map<String, Home> Homes = new HashMap<String, Home>();
    
    /**
     * Player who is contained in this PlayerHomeList instance
     */
    Player player;
    
    /**
     * Makes a new caching unit for the specified player's homes
     * @param player Player whose homes are cached
     */
    public PlayerHomeList(Player player){
        this.player=player;
    }
    
    public void addHome(String name, Home home){
        Homes.put(name, home);
    }
    
    public ArrayList<Home> getHomes(){
        //Declare homes arraylist
        ArrayList<Home> homes = new ArrayList<Home>();
        
        //Add homes from hashmap to ArrayList
        for(Home home : Homes.values()){
            homes.add(home);
        }
        
        //return list
        return homes;
    }
}
