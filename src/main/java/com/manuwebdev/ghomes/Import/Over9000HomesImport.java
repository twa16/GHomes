/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.ghomes.Import;

import com.manuwebdev.Over9000HomeImport.Home;
import com.manuwebdev.Over9000HomeImport.IO.ImportingMYSQLActions;
import com.manuwebdev.Over9000HomeImport.IO.ImportingMYSQLInterface;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author Manuel Gauto
 */
public class Over9000HomesImport {

    public static ArrayList<Home> getHomes(String player, File dir) {

        File file = new File(dir, player);

        //Configuration config = YamlConfiguration.loadConfiguration(file);
        Configuration config = new Configuration(file);
        config.load();
        ArrayList<Home> homesgotten = new ArrayList<Home>();
        //for (int i = 0; i < worlds.size(); i++) {

        List<String> homes = config.getKeys("world");
        Object[] objects = homes.toArray();
        String[] homesarray = new String[homes.size()];
        for (int i = 0; i < homes.size(); i++) {
            homesarray[i] = objects[i].toString();
        }


        for (int ii = 0; ii < homes.size(); ii++) {

            System.out.println(homesarray[ii]);
            String name = homesarray[ii];
            String world = "world";
            int x = (int) config.getDouble("world" + "." + homesarray[ii] + ".x", 0.0D);
            int y = (int) config.getDouble("world" + "." + homesarray[ii] + ".y", 0.0D);
            int z = (int) config.getDouble("world" + "." + homesarray[ii] + ".z", 0.0D);

            Home tmp = new Home();
            tmp.X = x;
            tmp.Y = y;
            tmp.Z = z;
            tmp.world = world;
            tmp.name = name;
            tmp.Owner = player.replace(".yml", "");

            homesgotten.add(tmp);
        }
        //}
        return homesgotten;
    }

    public static void importHomes(ImportingMYSQLInterface mysql, File dir) {
        ImportingMYSQLActions ma = new ImportingMYSQLActions(mysql);
        File folder = dir;
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            System.out.println(file.getName());
            ArrayList<Home> playerhomes = getHomes(file.getName(), dir);
            for (int i = 0; i < playerhomes.size(); i++) {
                ma.addHome(playerhomes.get(i));
            }
        }
    }
}
