/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class Home {
    
    private Player player;
    private Location location;
    private String name;
    
    /**
     * Constructor for home
     * @param p Owner of home
     * @param n Name for home
     * @param loc Location of 
     */
    public Home(Player p, String n,Location loc){
        player=p;
        name=n;
        location=loc;
    }
    
    /**
     * Returns the Player who owns the home
     * @return Owner of home
     */
    public Player getOwner(){
        return player;
    }
    
    /**
     * Returns the Location of the home
     * @return Home location
     */
    public Location getHomeLocation(){
        return location;
    }
    
    /**
     * Returns the name of the home
     * @return 
     */
    public String getHomeName(){
        return name;
    }
    
    
}
