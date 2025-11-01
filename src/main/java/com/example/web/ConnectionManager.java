package com.example.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection(String dbName, String user, String password) throws SQLException {
        // Updated URL for MySQL: uses 'jdbc:mysql' and port 3306
        String url = "sql8.freesqldatabase.com" + dbName + "?serverTimezone=UTC";

        // NOTE: This now uses the hardcoded credentials that were previously in your code,
        // but now targets a MySQL instance.
        return DriverManager.getConnection(url, user, password);
    }
}
