package com.mycompany.embg.app.controllers.admin;

import com.mycompany.embg.app.models.JadwalPengiriman;
import com.mycompany.embg.app.models.Vendor;
import com.mycompany.embg.app.repository.JadwalRepo;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.mycompany.embg.app.repository.UserRepo;
import java.sql.SQLException;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private TableView<String[]> dashboardTable;

    // Tambahkan komponen TableView baru menggunakan kelas Model Vendor
    @FXML
    private TableView<Vendor> vendorStatusTable;

    @FXML
    public void initialize() {
        // 1. Inisialisasi Tabel Recent Deliveries (Bawaan Lama)
        addColumnToDashboard("Vendor", 0);
        addColumnToDashboard("Sekolah", 1);
        addColumnToDashboard("Menu", 2);
        addColumnToDashboard("Porsi", 3);
        addColumnToDashboard("Tanggal", 4);
        addColumnToDashboard("Status", 5);

        loadJadwalData();

        // 2. Inisialisasi Tabel Status Approval Vendor (Tambahan Baru)
        setupVendorStatusTable();
    }

    // Helper bawaan lama untuk dashboardTable
    private void addColumnToDashboard(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        dashboardTable.getColumns().add(column);
    }

    
    private void loadJadwalData() {
        try {
            JadwalRepo repoJadwal = new JadwalRepo();
            List<JadwalPengiriman> listJadwal = repoJadwal.getAllJadwal();

            if (listJadwal.isEmpty()) {
                Label emptyLabel = new Label("Belum ada riwayat pengiriman.");
                emptyLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px;");
                dashboardTable.setPlaceholder(emptyLabel);
            } else {
                for (JadwalPengiriman j : listJadwal) {
                    // Petakan properti objek ke dalam susunan array String[]
                    String[] rowData = new String[]{
                        j.getNamaVendor(),
                        j.getNamaSekolah(),
                        j.getNamaMenu(),
                        String.valueOf(j.getJumlahPorsi()),
                        j.getTanggal(),
                        j.getStatus()
                    };
                    dashboardTable.getItems().add(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dashboardTable.setPlaceholder(new Label("Gagal mengambil data jadwal dari database."));
        }
    }
    
    
    private void setupVendorStatusTable() {
      
        TableColumn<Vendor, String> colNama = new TableColumn<>("Nama Vendor");
        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<Vendor, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlamat()));

        TableColumn<Vendor, String> colApproval = new TableColumn<>("Status Approval");
        colApproval.setCellValueFactory(data -> {
            boolean isApproved = data.getValue().isApproved(); // Pastikan pakai isApproved() yang sudah diperbaiki
            return new SimpleStringProperty(isApproved ? "Approved (Aktif)" : "Pending (Belum Diverifikasi)");
        });

        vendorStatusTable.getColumns().addAll(colNama, colAlamat, colApproval);
       

        // Ambil data dari database dan handle empty state
        try {
            UserRepo repo = new UserRepo();
            List<Vendor> vendors = repo.getAllVendors();

            if (vendors.isEmpty()) {
                Label emptyLabel = new Label("Belum ada data vendor.");
                emptyLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px;");
                vendorStatusTable.setPlaceholder(emptyLabel);
            } else {
                vendorStatusTable.getItems().addAll(vendors);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            vendorStatusTable.setPlaceholder(new Label("Gagal koneksi ke database."));
        }
    }
}
