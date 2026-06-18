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
    
    public List<JadwalPengiriman> getJadwal(String idVendor) throws SQLException {
        List<JadwalPengiriman> listJadwal = new ArrayList<>();
        String sql = "SELECT * FROM jadwal WHERE vendor_id = ?::uuid";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idVendor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listJadwal.add(new JadwalPengiriman(
                        rs.getString("id"),
                        rs.getString("vendor_id"),
                        rs.getString("sekolah_id"),
                        rs.getString("menu_id"),
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
    
//    private static final List<Shipment> shipmentList =
//            new ArrayList<>();
//
//    static {
//
//        shipmentList.add(
//                new Shipment(
//                        "1",
//                        "SD Negeri 01 Menteng",
//                        "Nasi Ayam",
//                        150,
//                        "Dimasak"
//                )
//        );
//
//        shipmentList.add(
//                new Shipment(
//                        "2",
//                        "SMP Bina Bangsa",
//                        "Nasi Ikan",
//                        220,
//                        "Dikirim"
//                )
//        );
//
//        shipmentList.add(
//                new Shipment(
//                        "3",
//                        "SMA 4 Jakarta",
//                        "Nasi Daging",
//                        350,
//                        "Diterima"
//                )
//        );
//    }
//
//    public List<Shipment> getAllShipments() {
//        return shipmentList;
//    }
//
//    public void updateStatus(
//            String shipmentId,
//            String status
//    ) {
//
//        for (Shipment s : shipmentList) {
//
//            if (s.getId().equals(shipmentId)) {
//
//                s.setStatus(status);
//                break;
//            }
//        }
//    }
}