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
package com.manuwebdev.ghomes.Caching;

import com.manuwebdev.ghomes.GHomes;
import com.manuwebdev.ghomes.Home;
import com.manuwebdev.ghomes.IO.MYSQLActions;
import com.manuwebdev.ghomes.IO.MYSQLInterface;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class HomeCache {

    /**
     * ArrayList Containing homes
     */
    private ArrayList<Home> HomeCache;
    /**
     * MYSQL Interface
     */
    private MYSQLInterface mysqlInterface;

    /**
     * Queries for MYSQL
     */
    MYSQLActions mysqlActions;
    
    /**
     * Plugin
     */
    GHomes plugin;
    /**
     * Initialize Home cache and do initial caching
     *
     * @param mysql Connection to MYSQL
     */
    public HomeCache(MYSQLInterface mysql, GHomes plugin) {
        this.mysqlInterface = mysql;
        mysqlActions=new MYSQLActions(mysqlInterface,plugin);
        HomeCache = new ArrayList<Home>();
        this.plugin=plugin;
    }

    /**
     * Add specified home to cache
     *
     * @param home Home to cache
     */
    public void addHome(Home home) {
        HomeCache.add(home);
    }

    /**
     * Get all homes that belong to a player
     * @param player Player to look for
     * @return Homes owned by specified player
     */
    public ArrayList<Home> getAllPlayerHomes(Player player) {
        ArrayList<Home> homes=mysqlActions.getPlayersHomes(player);
        return homes;
    }
    
    /**
     * Get home by name
     * @param name Name to search for
     * @return Home with name or null if it doesn't exist
     */
    public Home getHomeByName(String name, Player player){
        
        for (int i = 0; i < mysqlActions.getPlayersHomes(player).size(); i++) {
            if(mysqlActions.getPlayersHomes(player).get(i).getHomeName().equals(name)){
                return mysqlActions.getPlayersHomes(player).get(i);
            }
        }
        return null;
    }
    
    /**
     * Add a home to the cache
     * @param home Home to add
     */
    public void addHometoCache(Home home){
        HomeCache.add(home);
    }
    
    /**
     * Load homes from MYSQL
     */
    public void cacheHomes(){
        HomeCache=mysqlActions.getAllHomes();
    }
}
