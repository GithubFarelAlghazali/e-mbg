package com.mycompany.embg.app.controllers.vendor;

import com.mycompany.embg.app.models.JadwalItem;
import com.mycompany.embg.app.repository.JadwalRepo;
import com.mycompany.embg.app.services.AlertPopup;
import com.mycompany.embg.app.services.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class VendorShipmentController {

    @FXML private TextField inputNamaMenu;      // Berisi input teks menu/produk
    @FXML private TextField inputJumlahPorsi;   // Berisi input jumlah porsi makanan
    
    @FXML private ComboBox<String> comboSekolahTujuan; // Input tambahan untuk memilih Sekolah
    @FXML private DatePicker dateTanggalKirim;        // Input tambahan untuk memilih Tanggal Pengiriman

    private JadwalRepo jadwalRepo;

    @FXML
    public void initialize() {
        jadwalRepo = new JadwalRepo();
        populateSekolahComboBox();
    }

    private void populateSekolahComboBox() {
        // Opsi sekolah sementara. Sesuaikan atau sinkronkan dengan database Anda nanti.
        comboSekolahTujuan.getItems().addAll("SDN 01 Menteng Pagi", "SMPN 255 Jakarta", "SMAN 8 Jakarta");
    }

    @FXML
    void handleTambahPengiriman(ActionEvent event) {
        String menuInput = inputNamaMenu.getText();
        String porsiStr = inputJumlahPorsi.getText();
        String sekolahTerpilih = comboSekolahTujuan.getValue();

        // Validasi kelengkapan data form
        if (menuInput.isEmpty() || porsiStr.isEmpty() || sekolahTerpilih == null || dateTanggalKirim.getValue() == null) {
            AlertPopup.showAlert(AlertType.WARNING, "Harap isi semua data pengiriman!");
            return;
        }

        try {
            int porsi = Integer.parseInt(porsiStr);
            String tanggal = dateTanggalKirim.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String idVendor = UserSession.getCurrentUserId(); // Mengambil ID Vendor yang sedang login aktif

            // ──────────────────────────────────────────────────────────────
            // LOGIKA PEMETAAN DATA UUID POSTGRESQL
            // ──────────────────────────────────────────────────────────────
            // Pastikan string ID UUID di bawah ini sesuai dengan data baris asli di PostgreSQL Anda
            String idSekolahUUID = "7b2ebd4c-fa81-4b13-bb1f-818f972b2601"; 
            if ("SMPN 255 Jakarta".equals(sekolahTerpilih)) {
                idSekolahUUID = "c2b3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e";
            } else if ("SMAN 8 Jakarta".equals(sekolahTerpilih)) {
                idSekolahUUID = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d";
            }

            // Dummy UUID untuk menu produk masakan (sesuaikan dengan isi tabel 'products')
            String idMenuUUID = "4a1b2c3d-4e5f-6a7b-8c9d-0e1f2a3b4c5d";

            // Menggunakan Constructor Kosong + Method Setter yang baru kita buat di JadwalItem.java
            JadwalItem pengirimanBaru = new JadwalItem();
            pengirimanBaru.setNamaMenu(idMenuUUID);      // Melempar ID Menu bertipe UUID String ke query Repo
            pengirimanBaru.setNamaSekolah(idSekolahUUID); // Melempar ID Sekolah bertipe UUID String ke query Repo
            pengirimanBaru.setJumlahPorsi(porsi);
            pengirimanBaru.setNamaVendor(UserSession.getCurrentUsername());
            pengirimanBaru.setTanggal(tanggal);
            pengirimanBaru.setStatus("dimasak"); // Menggunakan format huruf kecil 'dimasak' agar aman di database

            // Memanggil method repo tambahJadwal yang membutuhkan objek JadwalItem dan idVendor
            jadwalRepo.tambahJadwal(pengirimanBaru, idVendor); 
            
            AlertPopup.showAlert(AlertType.INFORMATION, "Pengiriman berhasil ditambahkan!");
            kosongkanForm();
            
        } catch (NumberFormatException e) {
            AlertPopup.showAlert(AlertType.ERROR, "Porsi harus berupa angka!");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal menyimpan ke database: " + e.getMessage());
        }
    }

    private void kosongkanForm() {
        inputNamaMenu.clear();
        inputJumlahPorsi.clear();
        comboSekolahTujuan.setValue(null);
        dateTanggalKirim.setValue(null);
    }
}