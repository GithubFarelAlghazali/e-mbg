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

public class VendorJadwalController {

    @FXML
    private TextField txtNamaMenu; // Idealnya nanti ini combo box ID Menu dari DB

    @FXML
    private TextField txtJumlahPorsi;

    @FXML
    private ComboBox<String> comboSekolahTujuan; // Sekarang menampung Nama Sekolah

    private JadwalRepo jadwalRepo;

    @FXML
    private DatePicker dateTanggalKirim;

    @FXML
    public void initialize() {
        jadwalRepo = new JadwalRepo();
        populateSekolahComboBox();
    }

    private void populateSekolahComboBox() {
        // CATATAN: Idealnya ini mengambil daftar Nama dari database.
        // Untuk sementara, pastikan teks ini atau ID yang dipilih sesuai dengan relasi UUID di DB Anda.
        comboSekolahTujuan.getItems().addAll("SDN 01 Menteng Pagi", "SMPN 255 Jakarta", "SMAN 8 Jakarta");
    }

    @FXML
    void handleTambahPengiriman(ActionEvent event) {
        String menuInput = txtNamaMenu.getText();
        String porsiStr = txtJumlahPorsi.getText();
        String sekolahTerpilih = comboSekolahTujuan.getValue();
        
        if (menuInput.isEmpty() || porsiStr.isEmpty() || sekolahTerpilih == null || dateTanggalKirim.getValue() == null) {
            AlertPopup.showAlert(AlertType.WARNING, "Semua data form pengiriman wajib diisi!");
            return;
        }

        try {
            int porsi = Integer.parseInt(porsiStr);
            String tanggal = dateTanggalKirim.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String idVendor = UserSession.getCurrentUserId(); // Mengambil ID Vendor aktif (UUID)

            // ──────────────────────────────────────────────────────────────
            // LOGIKA PENYESUAIAN UUID (PENTING)
            // ──────────────────────────────────────────────────────────────
            // Karena tabel 'jadwal' meminta foreign key berupa UUID ke tabel sekolah_id dan menu_id,
            // untuk sementara kita mapping data string dari UI ke ID UUID dummy/asli yang ada di DB Anda.
            
            String idSekolahUUID = "UBAH-DENGAN-UUID-SEKOLAH-YANG-ADA-DI-DB"; 
            if ("SDN 01 Menteng Pagi".equals(sekolahTerpilih)) {
                idSekolahUUID = "7b2ebd4c-fa81-4b13-bb1f-818f972b2601"; // Contoh UUID asli dari DB
            } else if ("SMPN 255 Jakarta".equals(sekolahTerpilih)) {
                idSekolahUUID = "c2b3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e";
            }

            // Begitu juga dengan menu makanan (harus merujuk ke ID di tabel 'products')
            String idMenuUUID = "4a1b2c3d-4e5f-6a7b-8c9d-0e1f2a3b4c5d"; // Contoh UUID Produk Nasi Ayam/Menu dari DB

            // Membuat objek baru sesuai dengan struktur model JadwalItem
            JadwalItem pengirimanBaru = new JadwalItem();
            
            // Masukkan UUID String ke setter nama, karena Repo akan mengambil nilai ini untuk ?::uuid
            pengirimanBaru.setNamaMenu(idMenuUUID);      
            pengirimanBaru.setNamaSekolah(idSekolahUUID); 
            
            pengirimanBaru.setJumlahPorsi(porsi);
            pengirimanBaru.setNamaVendor(UserSession.getCurrentUsername()); 
            pengirimanBaru.setTanggal(tanggal);
            pengirimanBaru.setStatus("dimasak"); // Sesuai aturan enum/lowercase flow: 'dimasak'

            // Simpan ke database melalui repository
            jadwalRepo.tambahJadwal(pengirimanBaru, idVendor);
            
            AlertPopup.showAlert(AlertType.INFORMATION, "Pengiriman makanan sukses ditambahkan!");
            clearForm();

        } catch (NumberFormatException e) {
            AlertPopup.showAlert(AlertType.ERROR, "Input porsi makanan harus berupa angka!");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal menyimpan pengiriman: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtNamaMenu.clear();
        txtJumlahPorsi.clear();
        comboSekolahTujuan.setValue(null);
        dateTanggalKirim.setValue(null);
    }
}