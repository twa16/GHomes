/*
 * The MIT License
 *
 * Copyright 2012 Manuel Gauto.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.manuwebdev.ghomes;

import org.bukkit.Location;
import org.bukkit.entity.Player;


/**
 *
 * @author Manuel Gauto
 */
public class Home {
    
    private Player player;
    private Location location;
    private String name;
    private String ownername;
    
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
    
    public void setOwnerName(String name){
        ownername=name;
    }
    
    /**
     * 
     * @return 
     */
    public String getOwnerName(){
        if(ownername !=null){
            return ownername;
        }
        else{
            return player.getName();
        }
    }
    
    
}
