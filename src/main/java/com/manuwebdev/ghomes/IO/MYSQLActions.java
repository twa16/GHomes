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
package com.manuwebdev.ghomes.IO;

import com.manuwebdev.ghomes.GHomes;
import com.manuwebdev.ghomes.Home;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class MYSQLActions {

    private MYSQLInterface mysqlInterface;
    private String TABLE_NAME;
    private GHomes plugin;

    public MYSQLActions(MYSQLInterface mysqlInterface, GHomes plugin) {
        this.mysqlInterface = mysqlInterface;
        TABLE_NAME = mysqlInterface.getPrefix() + "HOMES";
        this.plugin=plugin;
    }

    public void addHome(Home home) {
        /*
         * 1-Player Name(OWNER) 2-Home Name(NAME) 3-Home X Coordinate(X) 4-Home
         * Y Coordinate(Y) 5-Home Z Coordinate(Z) 6-Home World(WORLD)
         */
        final String QUERY = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            ps.setString(1, home.getOwner().getName());
            ps.setString(2, home.getHomeName());
            ps.setInt(3, home.getHomeLocation().getBlockX());
            ps.setInt(4, home.getHomeLocation().getBlockY());
            ps.setInt(5, home.getHomeLocation().getBlockZ());
            ps.setString(6, home.getHomeLocation().getWorld().getName());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
            home.getOwner().sendMessage(ChatColor.DARK_RED + "Internal MYSQL Error");
        }

    }

    public Home getHome(String name, Player owner) {
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE OWNER=? AND NAME=?";
        try {
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            ps.setString(1, owner.getName());
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            Location loc = new Location(plugin.getServer().getWorld(rs.getString("WORLD")), (double) rs.getInt("X"), (double) rs.getInt("Y"), (double) rs.getInt("Z"));
            Home home = new Home(owner, name, loc);
            return home;
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
            owner.sendMessage(ChatColor.DARK_RED + "Internal MYSQL Error");
        }

        return null;
    }
    
    public ArrayList<Home> getPlayersHomes(Player owner) {
        //Initiate homes ArrayList
        ArrayList<Home> homes = new ArrayList<Home>();

        //Query to execute
        final String QUERY = "SELECT * FROM " + TABLE_NAME+" WHERE OWNER=?";
        try {
            //prepare
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            ps.setString(1, owner.getName());
            //execute
            ResultSet rs = ps.executeQuery();
            //loop through result
            while (rs.next()) {
                //Initiate location object with data in database
                Location loc = new Location(plugin.getServer().getWorld(rs.getString("WORLD")), (double) rs.getInt("X"), (double) rs.getInt("Y"), (double) rs.getInt("Z"));
                //Make home object
                Player p=plugin.getServer().getPlayer(rs.getString("OWNER"));
                Home home = new Home(p, rs.getString("NAME"), loc);
                
                //add home to list
                homes.add(home);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return homes;
    }

    public void deleteHome(String name, Player owner) {
        final String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE OWNER=? AND NAME=?";
        try {
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            ps.setString(1, owner.getName());
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
            owner.sendMessage(ChatColor.DARK_RED + "Internal MYSQL Error");
        }
    }

    /**
     * Get all hoems from database
     *
     * @return ArrayList with all homes in database
     */
    public ArrayList<Home> getAllHomes() {
        //Initiate homes ArrayList
        ArrayList<Home> homes = new ArrayList<Home>();

        //Query to execute
        final String QUERY = "SELECT * FROM " + TABLE_NAME;
        try {
            //prepare
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //execute
            ResultSet rs = ps.executeQuery();
            //loop through result
            while (rs.next()) {
                //Initiate location object with data in database
                Location loc = new Location(plugin.getServer().getWorld(rs.getString("WORLD")), (double) rs.getInt("X"), (double) rs.getInt("Y"), (double) rs.getInt("Z"));
                //Make home object
                Player p=plugin.getServer().getPlayer(rs.getString("OWNER"));
                Home home = new Home(p, rs.getString("NAME"), loc);
                
                //add home to list
                homes.add(home);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return homes;
    }

    public boolean doesTableExist(String Table) {
        try {
            DatabaseMetaData dbm = mysqlInterface.getMYSQLConnection().getMetaData();
            // check if table is there
            ResultSet tables = dbm.getTables(null, null, Table, null);
            if (tables.next()) {
                // Table exists
                return true;
            } else {
                // Table does not exist
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void createTableIfNeeded() {
        if (doesTableExist(TABLE_NAME) == false) {
            try {
                Statement stmt = mysqlInterface.getMYSQLConnection().createStatement();

                String sql = "CREATE TABLE " + TABLE_NAME + "("
                        + "OWNER             VARCHAR(254), " 
                        + "NAME              VARCHAR(254), " 
                        + "X                 INTEGER, "
                        + "Y                 INTEGER, "
                        + "Z                 INTEGER, "
                        + "WORLD             VARCHAR(254))";

                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                //NOM NOM NOM
            }
        }
    }
}
