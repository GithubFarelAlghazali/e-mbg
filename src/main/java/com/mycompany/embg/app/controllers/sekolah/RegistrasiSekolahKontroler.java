package com.mycompany.embg.app.controllers.sekolah;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegistrasiSekolahKontroler {

    @FXML
    private TextField kolomNpsn;

    @FXML
    private TextField kolomUsername;

    @FXML
    private TextField kolomEmail;

    @FXML
    private PasswordField kolomSandi;

    @FXML
    private TextField kolomTeksSandi; // Digunakan untuk menampilkan kata sandi dalam bentuk teks biasa

    @FXML
    private TextArea kolomAlamat;

    @FXML
    private Button tombolToggleSandi;

    private boolean sandiTerlihat = false;

    @FXML
    public void initialize() {
        // Menyinkronkan teks yang diketik agar selalu sama, baik saat disembunyikan maupun ditampilkan
        kolomTeksSandi.textProperty().bindBidirectional(kolomSandi.textProperty());
    }

    @FXML
    void aksiToggleSandi(ActionEvent event) {
        sandiTerlihat = !sandiTerlihat;
        
        if (sandiTerlihat) {
            kolomSandi.setVisible(false);
            kolomTeksSandi.setVisible(true);
            tombolToggleSandi.setText("🙈"); // Ikon mata tercoret/tertutup
        } else {
            kolomSandi.setVisible(true);
            kolomTeksSandi.setVisible(false);
            tombolToggleSandi.setText("👁"); // Ikon mata terbuka
        }
    }

    @FXML
    void aksiDaftar(ActionEvent event) {
        String npsn = kolomNpsn.getText();
        String username = kolomUsername.getText();
        
        System.out.println("Memproses pendaftaran untuk NPSN: " + npsn);
        System.out.println("Username Administrator: " + username);
        // Tambahkan logika penyimpanan data atau pemanggilan API di sini
    }

    @FXML
    void aksiMasuk(ActionEvent event) {
        System.out.println("Mengarahkan pengguna ke halaman Login Dasbor...");
        // Tambahkan logika pergantian scene (navigasi layar) di sini
    }
}