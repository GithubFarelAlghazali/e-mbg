/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.repository;
import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.models.*;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        stmt.setString(3, BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()) );
        stmt.setString(4, admin.getRole());
        stmt.setString(5, admin.getNip());
        stmt.setString(6, admin.getWilayah());
        stmt.executeUpdate();
    }
    
    public void registerSekolah(Sekolah sekolah) throws SQLException {
        String SQL = "INSERT INTO users (username, email, password, role, npsn, alamat) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, sekolah.getUsername());
        stmt.setString(2, sekolah.getEmail());
        stmt.setString(3, BCrypt.hashpw(sekolah.getPassword(), BCrypt.gensalt()));
        stmt.setString(4, sekolah.getRole());
        stmt.setString(5, sekolah.getNpsn());
        stmt.setString(6, sekolah.getAlamat());
        stmt.executeUpdate();
    }
    
        public void registerVendor(Vendor vendor) throws SQLException {
        String SQL = "INSERT INTO users (username, email, password, role, alamat) "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, vendor.getUsername());
        stmt.setString(2, vendor.getEmail());
        stmt.setString(3, BCrypt.hashpw(vendor.getPassword(), BCrypt.gensalt()));
        stmt.setString(4, vendor.getRole());
        stmt.setString(5, vendor.getAlamat());
        stmt.executeUpdate();
    }
    
    // Validasi
    
    public boolean isEmailExist(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return rs.getInt(1) > 0;
        }
        return false;
    }
    
    public boolean isUsernameExist(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return rs.getInt(1) > 0;
        }
        return false;
    }
    
    // Login
    
    public User findByEmail(String email) throws SQLException{
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            return new User(
                  rs.getString("id"),
                  rs.getString("username"),
                  rs.getString("email"),
                  rs.getString("password"),
                  rs.getString("role")
            );
        }
        return null;
    }
    
    public boolean checkPassword(String inputPassword, String hashedPassword){
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }
    
    // verifikasi vendoor
    
    public List<Vendor> getAllVendors() throws SQLException {
        List<Vendor> vendors = new ArrayList<>();
        // Mengambil data spesifik user dengan role 'vendor'
        String sql = "SELECT * FROM users WHERE role = 'vendor'";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vendor v = new Vendor(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("alamat"),
                        rs.getBoolean("approval") // Pastikan di database kamu ada kolom 'approval' (tipe boolean/tinyint)
                );
                vendors.add(v);
            }
        }
        return vendors;
    }
    
    public void updateVendorApproval(String idVendor, boolean getApproved) throws SQLException {
        // Asumsi tabel users memiliki kolom 'approval' ber-tipe boolean/tinyint
        String sql = "UPDATE users SET approval = ? WHERE id = ?::uuid AND role = 'vendor'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, getApproved);
            stmt.setString(2, idVendor);
            stmt.executeUpdate();
        }
    }
}
