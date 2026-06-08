/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.repository;
import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.models.*;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

/**
 *
 * @author User
 */
public class UserRepo {
    private Connection conn;
    
    public UserRepo() throws SQLException{
        this.conn = DbConfig.getConnection();
    }
    
    public void registerAdmin(AdminDinas admin) throws SQLException {
        String SQL = "INSERT INTO users (username, email, password, role, nip, wilayah) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, admin.getUsername());
        stmt.setString(2, admin.getEmail());
        stmt.setString(3, admin.getPassword());
        stmt.setString(4, admin.getRole());
        stmt.setString(5, admin.getNip());
        stmt.setString(6, admin.getWilayah());
        stmt.executeUpdate();
    }
}
