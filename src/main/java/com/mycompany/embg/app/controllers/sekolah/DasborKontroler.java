package com.mycompany.embg.app.controllers.sekolah;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.mycompany.embg.app.services.Redirect;

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
    
    @FXML
    private void clickDashboard(ActionEvent event){
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/Dashboard.fxml");
    }
    
    @FXML
    private void clickSiswa(ActionEvent event){
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/DirektoriSiswa.fxml");
    }
    
    @FXML
    private void clickDistribusi(ActionEvent event){
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/PelacakMakanan.fxml");
    }
    
    @FXML
    private void clickProfil(ActionEvent event){
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/ProfilSekolah.fxml");
    }
}