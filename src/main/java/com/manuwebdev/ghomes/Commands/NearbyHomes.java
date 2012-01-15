/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Commands;

import com.manuwebdev.ghomes.Caching.HomeCache;
import com.manuwebdev.ghomes.Home;
import com.manuwebdev.ghomes.IO.MYSQLActions;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class NearbyHomes {
    public static ArrayList<Home> getMyNearbyHomes(Player p, int Radius, HomeCache hcm){
        Location playerLocation=p.getLocation();
        ArrayList<Home> homes=hcm.getAllPlayerHomes(p);
        ArrayList<Home> myhomes=new ArrayList<Home>();
        for(int i=0;i<homes.size();i++){
            Home nearby=homes.get(i);
            boolean isNearby=isPointInCircle(Radius,playerLocation.getBlockX(), playerLocation.getBlockY(),nearby.getHomeLocation().getBlockX(),nearby.getHomeLocation().getBlockY());
            boolean owned=false;
            if(nearby.getOwner().equals(p))owned=true;
            if(isNearby&&owned)myhomes.add(nearby);
        }
        return myhomes;
    }
    
    public static ArrayList<Home> getALLNearbyHomes(Player p, int Radius, MYSQLActions mysqlActions){
        Location playerLocation=p.getLocation();
        ArrayList<Home> homes=new ArrayList<Home>();
        ArrayList<Home> allHomes=mysqlActions.getAllHomes();
        for(int i=0;i<allHomes.size();i++){
            Home nearby=allHomes.get(i);
            boolean isNearby=isPointInCircle(Radius,playerLocation.getBlockX(), playerLocation.getBlockY(),nearby.getHomeLocation().getBlockX(),nearby.getHomeLocation().getBlockY());
            if(isNearby)homes.add(nearby);
        }
        return homes;
    }
    
    public static ArrayList<Home> getPlayersNearbyHomes(Player p, int Radius, HomeCache hcm, Player tosearch){
        Location playerLocation=p.getLocation();
        ArrayList<Home> myhomes=new ArrayList<Home>();
        ArrayList<Home> homes=hcm.getAllPlayerHomes(tosearch);
        for(int i=0;i<homes.size();i++){
            Home nearby=homes.get(i);
            boolean isNearby=isPointInCircle(Radius,playerLocation.getBlockX(), playerLocation.getBlockY(),nearby.getHomeLocation().getBlockX(),nearby.getHomeLocation().getBlockY());
            boolean owned=false;
            if(nearby.getOwner().equals(tosearch))owned=true;
            if(isNearby&&owned)homes.add(nearby);
        }
        return homes;
    }
    
    private static boolean isPointInCircle(int circleRadius, int circleCenterX, int circleCenterY, int pointX, int pointY){
        int xDifference=pointX-circleCenterX;
        int yDifference=pointY-circleCenterY;
        int yDifferenceSquared=yDifference*yDifference;
        int xDifferenceSquared=xDifference*xDifference;
        int radiusSquared=xDifferenceSquared*yDifferenceSquared;
        if(radiusSquared<=circleRadius)return true;
        else{
            return false;
        }
    }
}
