package com.mycompany.embg.app.controllers.vendor;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import com.mycompany.embg.app.services.Redirect;
import com.mycompany.embg.app.models.JadwalPengiriman;
import com.mycompany.embg.app.models.BahanMakanan;
import com.mycompany.embg.app.repository.JadwalRepo;
import com.mycompany.embg.app.repository.InventoryRepo;
import com.mycompany.embg.app.services.UserSession;

public class VendorDashboardController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnNewReport;
    @FXML
    private Button btnUnduhLaporan;
    @FXML
    private Button btnDetailResep;

    // Properti Tabel Distribusi Sekolah
    @FXML
    private TableView<Distribusi> tblDistribusi;
    @FXML
    private TableColumn<Distribusi, String> colSekolah;
    @FXML
    private TableColumn<Distribusi, Integer> colPorsi;
    @FXML
    private TableColumn<Distribusi, String> colStatus;

    // --- FXID Tambahan untuk Kartu Informasi Dashboard ---
    // Pastikan fx:id ini dipasang pada Label terkait di VendorDashboard.fxml agar nilainya berubah secara live
    @FXML
    private Label lblMenuHariIni;
    @FXML
    private Label lblTargetPorsi;
    @FXML
    private Label lblPorsiSelesai;
    @FXML
    private Label lblTopBahan;

    // Perlu disesuaikan dengan ID Vendor yang sedang login dari session aplikasi kamu
    private final String currentVendorId = UserSession.getCurrentUserId();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Setup Mapping Kolom Tabel (Tanpa Waktu Target)
        colSekolah.setCellValueFactory(new PropertyValueFactory<>("sekolah"));
        colPorsi.setCellValueFactory(new PropertyValueFactory<>("jumlahPorsi"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // 2. Load Data Pengiriman (Jadwal) dan Ringkasan Informasi Dashboard secara Real-time
        loadDashboardData();
    }

    private void loadDashboardData() {
        try {
            JadwalRepo jRepo = new JadwalRepo();
            List<JadwalPengiriman> jadwalList = jRepo.getAllJadwal();
            ObservableList<Distribusi> dataDistribusi = FXCollections.observableArrayList();

            int totalTargetPorsi = 0;
            int totalPorsiSelesai = 0;
            String menuTerbaru = "Belum Ada Menu";

            // Karena getAllJadwal sudah terurut DESC (terbaru di atas) di SQL-nya:
            if (!jadwalList.isEmpty()) {
                menuTerbaru = jadwalList.get(0).getNamaMenu();
            }

            for (JadwalPengiriman jp : jadwalList) {
                // Catatan opsional: Jika ingin memfilter hanya jadwal milik vendor ini, 
                // pastikan model JadwalPengiriman kamu punya method getVendorId() atau sejenisnya.

                // 1. Masukkan ke tabel distribusi live
                dataDistribusi.add(new Distribusi(
                        jp.getNamaSekolah(),
                        jp.getJumlahPorsi(),
                        jp.getStatus()
                ));

                // 2. Hitung total porsi untuk Target Pengiriman
                totalTargetPorsi += jp.getJumlahPorsi();

                // 3. Jika status pengiriman diterima/selesai, akumulasikan ke porsi selesai
                if ("diterima".equalsIgnoreCase(jp.getStatus()) || "selesai".equalsIgnoreCase(jp.getStatus())) {
                    totalPorsiSelesai += jp.getJumlahPorsi();
                }
            }
            tblDistribusi.setItems(dataDistribusi);

            // Set text ke komponen kartu UI (Gunakan pengecekan null safety)
            if (lblMenuHariIni != null) {
                lblMenuHariIni.setText(menuTerbaru);
            }
            if (lblTargetPorsi != null) {
                lblTargetPorsi.setText(totalTargetPorsi + " Porsi");
            }
            if (lblPorsiSelesai != null) {
                lblPorsiSelesai.setText(totalPorsiSelesai + " Porsi");
            }

            // --- Bagian 4: Fetch Data Inventaris untuk Top 3 Paling Sedikit ---
            InventoryRepo iRepo = new InventoryRepo();
            List<BahanMakanan> semuaBahan = iRepo.getAllItems(currentVendorId);

            // Urutkan berdasarkan stok (qty) dari yang paling sedikit
            semuaBahan.sort((b1, b2) -> Integer.compare(b1.getJumlah(), b2.getJumlah()));

            // Ambil top 3 teratas
            List<BahanMakanan> top3Sedikit = semuaBahan.subList(0, Math.min(3, semuaBahan.size()));

            // Gabungkan nama bahan makanan top 3 menjadi satu string untuk ditampilkan di card inventaris
            if (lblTopBahan != null) {
                if (top3Sedikit.isEmpty()) {
                    lblTopBahan.setText("Stok Aman / Kosong");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < top3Sedikit.size(); i++) {
                        BahanMakanan b = top3Sedikit.get(i);
                        sb.append(b.getNama()).append(" (").append(b.getJumlah()).append(" ").append(b.getSatuan()).append(")");
                        if (i < top3Sedikit.size() - 1) {
                            sb.append(", ");
                        }
                    }
                    lblTopBahan.setText(sb.toString());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- KELAS MODEL DATA (POJO) UNTUK KOLOM TABEL (Tanpa target waktu) ---
    public static class Distribusi {

        private final String sekolah;
        private final int jumlahPorsi;
        private final String status;

        public Distribusi(String sekolah, int jumlahPorsi, String status) {
            this.sekolah = sekolah;
            this.jumlahPorsi = jumlahPorsi;
            this.status = status;
        }

        public String getSekolah() {
            return sekolah;
        }

        public int getJumlahPorsi() {
            return jumlahPorsi;
        }

        public String getStatus() {
            return status;
        }
    }

    @FXML
    private void handleBukaVendors(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/vendor/MenuManagement.fxml");
    }

    @FXML
    private void handleBukaShipments(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/vendor/ShipmentManagement.fxml");
    }

    @FXML
    private void handleBukaInventory(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml");
    }
}
