/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.IO;

import com.manuwebdev.ghomes.Home;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final String TABLE_NAME = mysqlInterface.getPrefix() + "HOMES";

    public MYSQLActions(MYSQLInterface mysqlInterface) {
        this.mysqlInterface = mysqlInterface;
    }

    public void addHome(Home home) {
        /* 1-Player Name(OWNER)
         * 2-Home Name(NAME)
         * 3-Home X Coordinate(X)
         * 4-Home Y Coordinate(Y)
         * 5-Home Z Coordinate(Z)
         * 6-Home World(WORLD)
         */
        final String QUERY = "INSERT INTO " + TABLE_NAME + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            ps.setString(1, home.getOwner().getName());
            ps.setString(2, home.getHomeName());
            ps.setInt(3, home.getHomeLocation().getBlockX());
            ps.setInt(4, home.getHomeLocation().getBlockY());
            ps.setInt(5, home.getHomeLocation().getBlockZ());
            ps.setString(6, home.getHomeLocation().getWorld().getName());
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
            Location loc = new Location(Bukkit.getWorld(rs.getString("WORLD")), (double) rs.getInt("X"), (double) rs.getInt("Y"), (double) rs.getInt("Z"));
            Home home = new Home(owner, name, loc);
            return home;
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
            owner.sendMessage(ChatColor.DARK_RED + "Internal MYSQL Error");
        }

        return null;
    }

    public void deleteHome(String name, Player owner) {
        final String QUERY = "DELETE * FROM " + TABLE_NAME + " WHERE OWNER=? AND NAME=?";
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
                Location loc = new Location(Bukkit.getWorld(rs.getString("WORLD")), (double) rs.getInt("X"), (double) rs.getInt("Y"), (double) rs.getInt("Z"));
                //Make home object
                Home home = new Home(Bukkit.getPlayer(rs.getString(rs.getString("OWNER"))),rs.getString("NAME"), loc);
                //add home to list
                homes.add(home);
                //cleanup
                home=null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return homes;
    }
}