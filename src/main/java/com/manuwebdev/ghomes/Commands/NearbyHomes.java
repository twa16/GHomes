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
