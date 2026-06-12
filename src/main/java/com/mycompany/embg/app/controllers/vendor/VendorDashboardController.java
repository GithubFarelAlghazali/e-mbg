package com.mycompany.mbgsystem; // SESUAIKAN dengan nama package Anda sendiri

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VendorDashboardController implements Initializable {

    @FXML private TextField txtSearch;
    @FXML private Button btnNewReport;
    @FXML private Button btnUnduhLaporan;
    @FXML private Button btnDetailResep;
    @FXML private ImageView imgMenu;
    
    // Properti Tabel
    @FXML private TableView<Distribusi> tblDistribusi;
    @FXML private TableColumn<Distribusi, String> colSekolah;
    @FXML private TableColumn<Distribusi, String> colWaktu;
    @FXML private TableColumn<Distribusi, Integer> colPorsi;
    @FXML private TableColumn<Distribusi, String> colStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Hubungkan Kolom Tabel ke Variabel Kelas Model Distribusi
        colSekolah.setCellValueFactory(new PropertyValueFactory<>("sekolah"));
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuTarget"));
        colPorsi.setCellValueFactory(new PropertyValueFactory<>("jumlahPorsi"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // 2. Kirim Data Dummy ke Tabel agar terisi otomatis
        tblDistribusi.setItems(getDummyData());

        // 3. Ambil Gambar Secara Aman Lewat Stream Konten Input (Resource Safe)
        try {
            Image gambarAyam = new Image(getClass().getResourceAsStream("images/ayam_bakar.png"));
            imgMenu.setImage(gambarAyam);
        } catch (Exception e) {
            System.out.println("Peringatan: Gambar 'images/ayam_bakar.png' belum dimasukkan atau tidak terbaca.");
        }
    }    

    private ObservableList<Distribusi> getDummyData() {
        ObservableList<Distribusi> data = FXCollections.observableArrayList();
        data.add(new Distribusi("SDN 01 Menteng", "10:00 WIB", 150, "Sampai"));
        data.add(new Distribusi("SMPN 04 Cikini", "10:30 WIB", 200, "Diantar"));
        data.add(new Distribusi("SDN 05 Kenari", "11:15 WIB", 100, "Dimasak"));
        return data;
    }

    @FXML
    private void handleNewReport(ActionEvent event) {
        System.out.println("Tombol New Report diklik.");
    }

    @FXML
    private void handleUnduhLaporan(ActionEvent event) {
        System.out.println("Unduh Laporan sukses dijalankan.");
    }

    @FXML
    private void handleDetailResep(ActionEvent event) {
        System.out.println("Membuka resep Nasi Ayam Bakar...");
    }

    // --- KELAS MODEL DATA (POJO) UNTUK KOLOM TABEL ---
    public static class Distribusi {
        private final String sekolah;
        private final String waktuTarget;
        private final int jumlahPorsi;
        private final String status;

        public Distribusi(String sekolah, String waktuTarget, int jumlahPorsi, String status) {
            this.sekolah = sekolah;
            this.waktuTarget = waktuTarget;
            this.jumlahPorsi = jumlahPorsi;
            this.status = status;
        }

        public String getSekolah() { return sekolah; }
        public String getWaktuTarget() { return waktuTarget; }
        public int getJumlahPorsi() { return jumlahPorsi; }
        public String getStatus() { return status; }
    }
    @FXML
private void handleBukaVendors(ActionEvent event) { mengubahHalaman("MenuManagement.fxml"); }

@FXML
private void handleBukaShipments(ActionEvent event) { mengubahHalaman("ShipmentManagement.fxml"); }

@FXML
private void handleBukaInventory(ActionEvent event) { mengubahHalaman("InventoryManagement.fxml"); }

private void mengubahHalaman(String fxmlFile) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) btnNewReport.getScene().getWindow();
        stage.getScene().setRoot(root);
    } catch (IOException e) {
        System.out.println("Gagal memuat halaman: " + fxmlFile);
        e.printStackTrace();
    }
}
}