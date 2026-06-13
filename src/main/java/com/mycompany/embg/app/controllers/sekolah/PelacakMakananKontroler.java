package com.mycompany.embg.app.controllers.sekolah;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.mycompany.embg.app.services.Redirect;

public class PelacakMakananKontroler {

    @FXML
    public void initialize() {
        // Karena tata letak secara keseluruhan dirakit langsung menggunakan FXML kustom
        // dengan inline styling, metode ini bisa difokuskan nanti untuk penarikan 
        // data statistik harian MBG secara dinamis dari database.
    }

    @FXML
    void aksiSinkron(ActionEvent event) {
        System.out.println("Tindakan: Sinkronisasi progres secara live sedang berjalan...");
    }

    @FXML
    void aksiFilter(ActionEvent event) {
        System.out.println("Tindakan: Menampilkan *dropdown* filter daftar kelas...");
    }

    @FXML
    void aksiUrutkan(ActionEvent event) {
        System.out.println("Tindakan: Menampilkan pengaturan urutan tabel distribusi...");
    }

    @FXML
    void aksiNavigasiTanggal(ActionEvent event) {
        System.out.println("Tindakan: Mengubah rentang waktu atau tanggal distribusi makanan...");
    }
    
    @FXML
    void aksiCatatData(ActionEvent event) {
        System.out.println("Tindakan: Membuka formulir pencatatan data aktual untuk kelas yang tertunda...");
    }

    @FXML
    void aksiEkspor(ActionEvent event) {
        System.out.println("Tindakan: Mengekspor log distribusi harian ke dokumen/spreadsheet...");
    }

    @FXML
    void aksiKeluar(ActionEvent event) {
        System.out.println("Tindakan: Proses keluar dari portal admin MBG...");
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