package com.mycompany.embg.app.controllers.sekolah;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class DirektoriSiswaKontroler {

    @FXML
    private ComboBox<String> filterKelas;

    @FXML
    private ComboBox<String> filterDiet;

    @FXML
    public void initialize() {
        // Mengisi opsi data untuk ComboBox filter
        filterKelas.getItems().addAll("Semua Kelas", "Kelas 1A", "Kelas 1B", "Kelas 2A", "Kelas 3A");
        filterKelas.getSelectionModel().selectFirst();

        filterDiet.getItems().addAll("Semua Jenis Diet", "Tidak Ada", "Alergi Kacang", "Vegan", "Bebas Gluten", "Bebas Susu");
        filterDiet.getSelectionModel().selectFirst();
    }

    @FXML
    void aksiTambahSiswa(ActionEvent event) {
        System.out.println("Tindakan: Membuka formulir untuk menambahkan data siswa baru...");
    }

    @FXML
    void aksiEkspor(ActionEvent event) {
        System.out.println("Tindakan: Mengekspor daftar direktori siswa ke dokumen...");
    }

    @FXML
    void aksiKeluar(ActionEvent event) {
        System.out.println("Tindakan: Proses keluar (Logout) dari sistem...");
    }
}