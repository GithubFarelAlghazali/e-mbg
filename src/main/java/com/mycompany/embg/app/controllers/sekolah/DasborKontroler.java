package com.mycompany.embg.app.controllers.sekolah;

import com.mycompany.embg.app.repository.SiswaRepo;
import com.mycompany.embg.app.services.AlertPopup;
import com.mycompany.embg.app.services.Redirect;
import com.mycompany.embg.app.services.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class DasborKontroler {

    // --- Panel Dua Mode ---
    @FXML
    private VBox emptyStatePanel;
    @FXML
    private VBox mainPanel;

    // --- Komponen Empty State ---
    @FXML
    private TextField inputJumlahSiswa;

    // --- Label Dinamis Dashboard Utama ---
    @FXML
    private Label labelJumlahSiswa;
    @FXML
    private Label labelJumlahAlergi;
    @FXML
    private Label labelPorsiPengiriman;
    @FXML
    private Label labelSambutanMain;

    private SiswaRepo siswaRepo;
    private String idSekolah;

    @FXML
    public void initialize() {
        try {
            this.siswaRepo = new SiswaRepo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.idSekolah = UserSession.getCurrentUserId();
        muatDasbor();
    }

    /**
     * Cek jumlah_siswa dari DB. Jika 0 atau null → tampilkan emptyStatePanel.
     * Jika sudah ada → tampilkan mainPanel dengan data real.
     */
    private void muatDasbor() {
        try {
            int jumlahSiswa = siswaRepo.getJumlahSiswa(idSekolah);

            if (jumlahSiswa <= 0) {
                // Mode setup awal
                emptyStatePanel.setVisible(true);
                emptyStatePanel.setManaged(true);
                mainPanel.setVisible(false);
                mainPanel.setManaged(false);
            } else {
                // Mode dashboard penuh
                emptyStatePanel.setVisible(false);
                emptyStatePanel.setManaged(false);
                mainPanel.setVisible(true);
                mainPanel.setManaged(true);

                // Isi label dengan data real
                labelJumlahSiswa.setText(String.valueOf(jumlahSiswa));
                labelPorsiPengiriman.setText(String.valueOf(jumlahSiswa));

                int jumlahAlergi = siswaRepo.getJumlahSiswaKhusus(idSekolah);
                labelJumlahAlergi.setText(String.valueOf(jumlahAlergi));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Gagal memuat data dasbor: " + e.getMessage());
        }
    }

    /**
     * Dipanggil saat tombol "Simpan" di emptyStatePanel ditekan. Menyimpan
     * jumlah siswa lalu reload dasbor ke mode penuh.
     */
    @FXML
    void aksiSimpanJumlahSiswa(ActionEvent event) {
        String input = inputJumlahSiswa.getText().trim();
        if (input.isEmpty()) {
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Harap masukkan jumlah siswa terlebih dahulu.");
            return;
        }

        try {
            int totalSiswa = Integer.parseInt(input);
            if (totalSiswa <= 0) {
                AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                        "Jumlah siswa harus lebih dari 0.");
                return;
            }

            siswaRepo.updateTotalPorsi(idSekolah, totalSiswa);
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Jumlah siswa berhasil disimpan! Dasbor akan diperbarui.");

            inputJumlahSiswa.clear();
            muatDasbor(); // Reload → akan pindah ke mainPanel

        } catch (NumberFormatException e) {
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Format salah! Harap masukkan angka yang valid.");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Gagal menyimpan ke server: " + e.getMessage());
        }
    }

    // --- Aksi Tombol ---
    @FXML
    void aksiLaporMasalah(ActionEvent event) {
        System.out.println("Memproses: Jendela pelaporan masalah pengiriman akan ditampilkan...");
    }

    @FXML
    void aksiMenuBaru(ActionEvent event) {
        System.out.println("Memproses: Jendela form pengajuan menu baru akan ditampilkan...");
    }

    // --- Navigasi Sidebar ---
    @FXML
    private void clickDashboard(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/Dashboard.fxml");
    }

    @FXML
    private void clickSiswa(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/DirektoriSiswa.fxml");
    }

    @FXML
    private void clickDistribusi(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/PelacakMakanan.fxml");
    }

    @FXML
    private void clickProfil(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/ProfilSekolah.fxml");
    }
}
