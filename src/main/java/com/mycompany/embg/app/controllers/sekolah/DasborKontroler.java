package com.mycompany.embg.app.controllers.sekolah;

import com.mycompany.embg.app.models.JadwalPengiriman;
import com.mycompany.embg.app.repository.JadwalRepo;
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
import java.util.List;

public class DasborKontroler {

    @FXML
    private VBox emptyStatePanel;
    @FXML
    private VBox mainPanel;
    @FXML
    private TextField inputJumlahSiswa;

    // Stat cards
    @FXML
    private Label labelJumlahSiswa;
    @FXML
    private Label labelJumlahAlergi;
    @FXML
    private Label labelPorsiPengiriman;
    @FXML
    private Label labelSambutanMain;

    // Kotak pengiriman aktif
    @FXML
    private Label labelVendorPengiriman;
    @FXML
    private Label labelMenuPengiriman;
    @FXML
    private Label labelStatusPengiriman;
    @FXML
    private Label labelWaktuPengiriman;

    // Status card (pojok kanan atas)
    @FXML
    private Label labelStatusCard;
    @FXML
    private Label labelWaktuCard;

    private SiswaRepo siswaRepo;
    private JadwalRepo jadwalRepo;
    private String idSekolah;

    @FXML
    public void initialize() {
        try {
            this.siswaRepo = new SiswaRepo();
            this.jadwalRepo = new JadwalRepo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.idSekolah = UserSession.getCurrentUserId();
        muatDasbor();
    }

    private void muatDasbor() {
        try {
            int jumlahSiswa = siswaRepo.getJumlahSiswa(idSekolah);

            if (jumlahSiswa <= 0) {
                emptyStatePanel.setVisible(true);
                emptyStatePanel.setManaged(true);
                mainPanel.setVisible(false);
                mainPanel.setManaged(false);
            } else {
                emptyStatePanel.setVisible(false);
                emptyStatePanel.setManaged(false);
                mainPanel.setVisible(true);
                mainPanel.setManaged(true);

                // Data siswa
                labelJumlahSiswa.setText(String.valueOf(jumlahSiswa));
                labelPorsiPengiriman.setText(String.valueOf(jumlahSiswa));
                int jumlahAlergi = siswaRepo.getJumlahSiswaKhusus(idSekolah);
                labelJumlahAlergi.setText(String.valueOf(jumlahAlergi));

                // Data pengiriman
                muatDataPengiriman();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Gagal memuat data dasbor: " + e.getMessage());
        }
    }

    private void muatDataPengiriman() throws SQLException {
        List<JadwalPengiriman> jadwalList = jadwalRepo.getJadwalBySekolah(idSekolah);

        if (jadwalList != null && !jadwalList.isEmpty()) {
            JadwalPengiriman jadwal = jadwalList.get(0);

            labelVendorPengiriman.setText(jadwal.getNamaVendor());
            labelMenuPengiriman.setText(jadwal.getNamaMenu());

            String status = "● " + jadwal.getStatus();
            String tanggal = jadwal.getTanggal();

            labelStatusCard.setText(status);
            labelWaktuCard.setText(tanggal);
            labelStatusPengiriman.setText(status);
            labelWaktuPengiriman.setText(tanggal);
        } else {
            labelVendorPengiriman.setText("-");
            labelMenuPengiriman.setText("-");
            labelStatusCard.setText("Tidak ada pengiriman");
            labelWaktuCard.setText("-");
            labelStatusPengiriman.setText("Tidak ada pengiriman");
            labelWaktuPengiriman.setText("-");
        }
    }

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
            muatDasbor();
        } catch (NumberFormatException e) {
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Format salah! Harap masukkan angka yang valid.");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Gagal menyimpan ke server: " + e.getMessage());
        }
    }

    @FXML
    void aksiLaporMasalah(ActionEvent event) {
        System.out.println("Memproses: Jendela pelaporan masalah pengiriman akan ditampilkan...");
    }

    @FXML
    void aksiMenuBaru(ActionEvent event) {
        System.out.println("Memproses: Jendela form pengajuan menu baru akan ditampilkan...");
    }

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
