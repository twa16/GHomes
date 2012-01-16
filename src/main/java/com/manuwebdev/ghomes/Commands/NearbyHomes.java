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

    public static ArrayList<Home> getMyNearbyHomes(Player p, int Radius, HomeCache hcm) {
        Location playerLocation = p.getLocation();
        ArrayList<Home> homes = hcm.getAllPlayerHomes(p);
        ArrayList<Home> myhomes = new ArrayList<Home>();
        for (int i = 0; i < homes.size(); i++) {
            Home nearby = homes.get(i);
            boolean isNearby = isPointInCircle(Radius, playerLocation.getBlockX(), playerLocation.getBlockZ(), nearby.getHomeLocation().getBlockX(), nearby.getHomeLocation().getBlockZ());
            boolean owned = false;
            if (nearby.getOwner().equals(p)) {
                owned = true;
            }
            if (isNearby && owned) {
                myhomes.add(nearby);
            }
        }
        return myhomes;
    }

    public static ArrayList<Home> getALLNearbyHomes(Player p, int Radius, MYSQLActions mysqlActions) {
        Location playerLocation = p.getLocation();
        ArrayList<Home> homes = new ArrayList<Home>();
        ArrayList<Home> allHomes = mysqlActions.getAllHomes();
        for (int i = 0; i < allHomes.size(); i++) {
            Home nearby = allHomes.get(i);
            boolean isNearby = isPointInCircle(Radius, playerLocation.getBlockX(), playerLocation.getBlockZ(), nearby.getHomeLocation().getBlockX(), nearby.getHomeLocation().getBlockZ());
            if (isNearby) {
                homes.add(nearby);
            }
        }
        return homes;
    }

    public static ArrayList<Home> getPlayersNearbyHomes(Player p, int Radius, HomeCache hcm, Player tosearch) {
        Location playerLocation = p.getLocation();
        ArrayList<Home> myhomes = new ArrayList<Home>();
        ArrayList<Home> homes = hcm.getAllPlayerHomes(tosearch);
        for (int i = 0; i < homes.size(); i++) {
            Home nearby = homes.get(i);
            boolean isNearby = isPointInCircle(Radius, playerLocation.getBlockX(), playerLocation.getBlockZ(), nearby.getHomeLocation().getBlockX(), nearby.getHomeLocation().getBlockZ());
            boolean owned = false;
            if (nearby.getOwner().equals(tosearch)) {
                owned = true;
            }
            if (isNearby && owned) {
                myhomes.add(nearby);
            }
        }
        return myhomes;
    }

    private static boolean isPointInCircle(int circleRadius, int circleCenterX, int circleCenterZ, int pointX, int pointZ) {
        if (circleCenterX < pointX && circleCenterZ < pointZ) {
            int xDifference = pointX - circleCenterX;
            int zDifference = pointZ - circleCenterZ;
            int zDifferenceSquared = zDifference * zDifference;
            int xDifferenceSquared = xDifference * xDifference;
            int radiusSquared = xDifferenceSquared + zDifferenceSquared;
            if (radiusSquared < (circleRadius*circleRadius)) {
                return true;
            } else {
                return false;
            }
        } else {
            int xDifference = circleCenterX-pointX;
            int zDifference = circleCenterZ-pointZ;
            int zDifferenceSquared = zDifference * zDifference;
            int xDifferenceSquared = xDifference * xDifference;
            int radiusSquared = xDifferenceSquared + zDifferenceSquared;
            System.out.println("\n\nzDif: "+zDifference+"\nxDif: "+xDifference+"\n zDifSqared: "+zDifferenceSquared+"\n xDifSqared: "+xDifferenceSquared);
            System.out.println("\nradiussquard: "+radiusSquared+"\ncircleRadius: "+circleRadius+"\ncircleradiussaqre: "+(circleRadius*circleRadius));
            if (radiusSquared < (circleRadius*circleRadius)) {
                return true;
            } else {
                return false;
            }
        }




    }
}
