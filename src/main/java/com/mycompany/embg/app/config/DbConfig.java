/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.config;
import java.sql.*;

public class DbConfig {
    private static final String URL = "jdbc:postgresql://db.gpychukfwcmuwhryvsxl.supabase.co:5432/postgres?user=postgres&password=e-mbgSUPA26";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your_password"; // dari Supabase

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
