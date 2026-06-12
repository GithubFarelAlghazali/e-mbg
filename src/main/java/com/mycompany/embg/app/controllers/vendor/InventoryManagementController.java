package com.mycompany.mbgsystem; // Sesuaikan dengan nama package utama Anda

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
import javafx.stage.Stage;

public class InventoryManagementController implements Initializable {

    @FXML private TextField txtSearchGlobal;
    @FXML private Button btnAddItem;
    @FXML private Button btnNewReport;
    
    // Properti TableView Inventory
    @FXML private TableView<InventoryItem> tblInventory;
    @FXML private TableColumn<InventoryItem, String> colNamaBarang;
    @FXML private TableColumn<InventoryItem, String> colStok;
    @FXML private TableColumn<InventoryItem, String> colSatuan;
    @FXML private TableColumn<InventoryItem, String> colActions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Hubungkan Kolom Tabel ke Variabel Kelas Model
        colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colSatuan.setCellValueFactory(new PropertyValueFactory<>("satuan"));
        colActions.setCellValueFactory(new PropertyValueFactory<>("actions"));

        // 2. Set Baris Data Dummy Sesuai Gambar Mockup
        tblInventory.setItems(getDummyInventoryData());
    }    

    private ObservableList<InventoryItem> getDummyInventoryData() {
        ObservableList<InventoryItem> data = FXCollections.observableArrayList();
        data.add(new InventoryItem("Beras Putih Premium", "1,250", "Kg"));
        data.add(new InventoryItem("Telur Ayam Ras", "45", "Papan (30)"));
        data.add(new InventoryItem("Minyak Goreng Sawit", "320", "Liter"));
        data.add(new InventoryItem("Daging Ayam Potong", "150", "Kg"));
        return data;
    }

    @FXML
    private void handleAddItem(ActionEvent event) {
        System.out.println("Membuka formulir pop-up Add Item Baru...");
    }
    // --- NAVIGASI MULTI-PAGE KE VENDORS (MANAJEMEN MENU) ---
    // --- NAVIGASI MULTI-PAGE KE DASHBOARD ---

    // --- KELAS MODEL POJO STRUKTUR DATA INVENTORY ---
    public static class InventoryItem {
        private final String namaBarang;
        private final String stok;
        private final String satuan;
        private final String actions;

        public InventoryItem(String namaBarang, String stok, String satuan) {
            this.namaBarang = namaBarang;
            this.stok = stok;
            this.satuan = satuan;
            this.actions = ""; // Tempat penempatan tombol aksi edit/delete nanti
        }

        public String getNamaBarang() { return namaBarang; }
        public String getStok() { return stok; }
        public String getSatuan() { return satuan; }
        public String getActions() { return actions; }
    }
    @FXML
private void handleBukaDashboard(ActionEvent event) { mengubahHalaman("VendorDashboard.fxml"); }

@FXML
private void handleBukaVendors(ActionEvent event) { mengubahHalaman("MenuManagement.fxml"); }

@FXML
private void handleBukaShipments(ActionEvent event) { mengubahHalaman("ShipmentManagement.fxml"); }

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