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
