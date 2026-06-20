package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.models.BahanMakanan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepo {

    private static final List<BahanMakanan> inventoryList =
            new ArrayList<>();

    private Connection conn;

    public InventoryRepo() throws SQLException {
        this.conn = DbConfig.getConnection();
    }


    
    public List<BahanMakanan> getAllItems(String idVendor) throws SQLException {
        String sql = "SELECT * FROM inventaris_dapur WHERE vendor_id = ?::uuid ";
        inventoryList.clear();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idVendor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inventoryList.add(new BahanMakanan(
                        rs.getString("nama"),
                        rs.getInt("harga"),
                        rs.getInt("qty"),
                        rs.getString("unit"),
                        rs.getString("id"),
                        rs.getString("vendor_id")
                ));
            }
        }
        
        return inventoryList;
    }

    public void addItem(BahanMakanan item) throws SQLException {
        String sql = "INSERT INTO inventaris_dapur (id, nama, harga, qty, unit, vendor_id) "
                + "VALUES (?::uuid, ?, ?, ?, ?, ?::uuid)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getNama());    
            stmt.setInt(3, item.getHarga());
            stmt.setInt(4, item.getJumlah());
            stmt.setString(5, item.getSatuan());
            stmt.setString(6, item.getVendorId());

            stmt.executeUpdate();

            inventoryList.add(item);
        }
    }

    public void deleteItem(String id) {
        
        inventoryList.removeIf(
                item -> item.getId().equals(id)
        );
    }

    public void updateItem(
            String id,
            String namaBarang,
            int stok,
            String satuan,
            int harga
    ) {

        for (BahanMakanan item : inventoryList) {

            if (item.getId().equals(id)) {

                item.setNama(namaBarang);
                item.setJumlah(stok);
                item.setSatuan(satuan);
                item.setHarga(harga);

                return;
            }
        }
    }
}