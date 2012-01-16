/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.Over9000HomeImport.IO;

import com.manuwebdev.Over9000HomeImport.Home;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Manuel Gauto
 */
public class ImportingMYSQLActions {

    private ImportingMYSQLInterface mysqlInterface;
    private String TABLE_NAME;

    public ImportingMYSQLActions(ImportingMYSQLInterface mysqlInterface) {
        this.mysqlInterface = mysqlInterface;
        TABLE_NAME = "GHHOMES";
    }

    public void addHome(Home home) {
        /*
         * 1-Player Name(OWNER) 2-Home Name(NAME) 3-Home X Coordinate(X) 4-Home
         * Y Coordinate(Y) 5-Home Z Coordinate(Z) 6-Home World(WORLD)
         */
        final String QUERY = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            ps.setString(1, home.Owner);
            ps.setString(2, home.name);
            ps.setInt(3, home.X);
            ps.setInt(4, home.Y);
            ps.setInt(5, home.Z);
            ps.setString(6, home.world);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ImportingMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
