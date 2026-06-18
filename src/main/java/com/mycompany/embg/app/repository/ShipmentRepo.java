package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.models.JadwalPengiriman;
import com.mycompany.embg.app.models.Shipment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mycompany.embg.app.config.DbConfig;
import java.sql.ResultSet;
import javafx.scene.control.Alert.AlertType;
import com.mycompany.embg.app.services.AlertPopup;

import java.util.ArrayList;
import java.util.List;

public class ShipmentRepo {

    private Connection conn;

    
    public ShipmentRepo() throws SQLException {
        this.conn = DbConfig.getConnection();
    }    
    
    public List<JadwalPengiriman> getJadwalbyVendor(String idVendor) throws SQLException {
        List<JadwalPengiriman> listJadwal = new ArrayList<>();

        // Menggunakan JOIN untuk mengambil nama sekolah dan nama menu
        // Asumsi: 
        // 1. Nama tabel sekolah adalah 'sekolah' dan kolom namanya adalah 'nama'
        // 2. Nama tabel menu adalah 'menu' dan kolom namanya adalah 'nama'
        String sql = "SELECT "
                + "  j.id, "
                + "  j.vendor_id, "
                + "  s.username AS nama_sekolah, "
                + "  m.nama_produk AS nama_menu, "
                + "  j.jumlah_porsi, "
                + "  j.status, "
                + "  j.tanggal "
                + "FROM jadwal j "
                + "JOIN users s ON j.sekolah_id = s.id::uuid "
                + "JOIN products m ON j.menu_id = m.id::uuid "
                + "WHERE j.vendor_id = ?::uuid";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idVendor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listJadwal.add(new JadwalPengiriman(
                        rs.getString("id"),
                        rs.getString("vendor_id"),
                        rs.getString("nama_sekolah"), // Masuk ke field sekolahId di model
                        rs.getString("nama_menu"), // Masuk ke field menu di model
                        rs.getInt("jumlah_porsi"),
                        rs.getString("status"),
                        rs.getString("tanggal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listJadwal;
    }


    public List<JadwalPengiriman> getJadwal() throws SQLException {
        List<JadwalPengiriman> listJadwal = new ArrayList<>();

        // Menggunakan JOIN untuk mengambil nama sekolah, nama menu, dan nama vendor
        String sql = "SELECT "
                + "  j.id, "
                + "  v.username AS nama_vendor, " // JOIN tambahan untuk nama vendor
                + "  s.username AS nama_sekolah, "
                + "  m.nama_produk AS nama_menu, "
                + "  j.jumlah_porsi, "
                + "  j.status, "
                + "  j.tanggal "
                + "FROM jadwal j "
                + "JOIN users s ON j.sekolah_id = s.id::uuid "
                + "JOIN products m ON j.menu_id = m.id::uuid "
                + "JOIN users v ON j.vendor_id = v.id::uuid"; // Menghubungkan vendor_id ke tabel users

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Tanpa stmt.setString karena kita mengambil semua data (tanpa WHERE)
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listJadwal.add(new JadwalPengiriman(
                        rs.getString("id"),
                        rs.getString("nama_vendor"), // Menggantikan j.vendor_id menjadi nama_vendor
                        rs.getString("nama_sekolah"),
                        rs.getString("nama_menu"),
                        rs.getInt("jumlah_porsi"),
                        rs.getString("status"),
                        rs.getString("tanggal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listJadwal;
    }

    public void updateStatus(String jadwalId, String status){
        String sql = "UPDATE jadwal SET status = ? WHERE id = ?::uuid";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, status);
            stmt.setString(2, jadwalId);
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal mengubah status pengiriman : " + e);
        }
    }
    
}