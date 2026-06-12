package com.mycompany.embg.app.controllers.sekolah;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DasborKontroler {

    @FXML
    public void initialize() {
        // Karena tata letak secara keseluruhan (tabel & grafik distribusi) dibangun murni 
        // dengan XML Native (GridPane, HBox, VBox) beserta in-line styling, Anda tidak perlu
        // melakukan set up UI secara terprogram di sini. Cukup siapkan logika backend-nya.
    }

    @FXML
    void aksiLaporMasalah(ActionEvent event) {
        System.out.println("Memproses: Jendela pelaporan masalah pengiriman akan ditampilkan...");
    }

    @FXML
    void aksiMenuBaru(ActionEvent event) {
        System.out.println("Memproses: Jendela form pengajuan menu baru akan ditampilkan...");
    }
}