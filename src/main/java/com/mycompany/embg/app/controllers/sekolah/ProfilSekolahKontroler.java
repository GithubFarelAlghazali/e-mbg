package com.mycompany.embg.app.controllers.sekolah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfilSekolahKontroler {

    @FXML
    public void initialize() {
        // Teks seperti Nama Sekolah, NPSN, dan Alamat sudah diatur bawaannya
        // langsung di dalam FXML untuk mencocokkan desain yang Anda butuhkan.
    }

    @FXML
    void aksiBatalkan(ActionEvent event) {
        System.out.println("Tindakan: Membatalkan perubahan dan memuat ulang formulir...");
    }

    @FXML
    void aksiSimpan(ActionEvent event) {
        System.out.println("Tindakan: Menyimpan pembaruan profil institusi sekolah...");
    }

    @FXML
    void aksiUnggahLogo(ActionEvent event) {
        System.out.println("Tindakan: Membuka FileChooser untuk mengunggah logo...");
    }

    @FXML
    void aksiSesuaikanJadwal(ActionEvent event) {
        System.out.println("Tindakan: Menampilkan form pop-up pengaturan jam operasional...");
    }

    @FXML
    void aksiEkspor(ActionEvent event) {
        System.out.println("Tindakan: Mengunduh ekspor laporan sekolah...");
    }

    @FXML
    void aksiKeluar(ActionEvent event) {
        System.out.println("Tindakan: Keluar (Logout) dari sistem portal admin...");
    }
}