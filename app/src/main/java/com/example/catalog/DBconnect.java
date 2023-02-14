package com.example.catalog;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
    private Connection connection;

    public Connection connect() {
        String ip = "";
        String user = "";
        String pass = "";
        String port = "";
        String database = "BD_Catalog";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionString = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + database
                    + ";user=" + user + ";password=" + pass + ";";
            connection = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            Log.e("Error",e.getMessage());
        } catch (SQLException e) {
            Log.e("Error", e.getMessage());
        }
        return connection;
    }
}
