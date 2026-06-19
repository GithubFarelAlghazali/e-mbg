package com.mycompany.embg.app.controllers.vendor; // Sesuaikan dengan nama package Anda

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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.mycompany.embg.app.services.Redirect;

public class MenuManagementController implements Initializable {

    @FXML private Button btnTambahMenu;
    @FXML private Button btnFilter;
    @FXML private TextField txtSearchMenu;
    @FXML private Button btnNewReport;
    
    // Properti TableView
    @FXML private TableView<MenuHarian> tblMenu;
    @FXML private TableColumn<MenuHarian, String> colTanggal;
    @FXML private TableColumn<MenuHarian, String> colNamaMenu;
    @FXML private TableColumn<MenuHarian, String> colKalori;
    @FXML private TableColumn<MenuHarian, String> colActions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Set Kolom Dasar
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colKalori.setCellValueFactory(new PropertyValueFactory<>("kalori"));
        colActions.setCellValueFactory(new PropertyValueFactory<>("actions"));

        // 2. Kustomisasi Kolom Nama Menu agar Memiliki Subtitle (Judul + Sub-menu)
        colNamaMenu.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    MenuHarian data = getTableView().getItems().get(getIndex());
                    
                    Label lblJudul = new Label(data.getNamaMenu());
                    lblJudul.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #1E152A;");
                    
                    Label lblSubtitle = new Label(data.getDetailMenu());
                    lblSubtitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #4E6766;");
                    
                    VBox containerTeks = new VBox(2, lblJudul, lblSubtitle);
                    setGraphic(containerTeks);
                }
            }
        });

        // 3. Masukkan Data Dummy Pasukan
        tblMenu.setItems(getDummyMenuData());
    }    

    private ObservableList<MenuHarian> getDummyMenuData() {
        ObservableList<MenuHarian> data = FXCollections.observableArrayList();
        data.add(new MenuHarian("Senin, 16 Okt", "Nasi Ayam Bakar Madu", "Sayur Asem, Tempe, Buah Pisang", "650 kcal"));
        data.add(new MenuHarian("Selasa, 17 Okt", "Nasi Ikan Nila Goreng", "Tumis Kangkung, Tahu, Jeruk", "580 kcal"));
        data.add(new MenuHarian("Rabu, 18 Okt", "Nasi Telur Dadar Spesial", "Sayur Bayam Jagung, Semangka", "520 kcal"));
        return data;
    }

    @FXML
    private void handleTambahMenu(ActionEvent event) {
        System.out.println("Membuka formulir pop-up Tambah Menu Baru...");
    }

    // --- KELAS MODEL POJO UNTUK STRUKTUR DATA MENU ---
    public static class MenuHarian {
        private final String tanggal;
        private final String namaMenu;
        private final String detailMenu;
        private final String kalori;
        private final String actions;

        public MenuHarian(String tanggal, String namaMenu, String detailMenu, String kalori) {
            this.tanggal = tanggal;
            this.namaMenu = namaMenu;
            this.detailMenu = detailMenu;
            this.kalori = kalori;
            this.actions = ""; // Kosong untuk tombol aksi opsional nanti
        }

        public String getTanggal() { return tanggal; }
        public String getNamaMenu() { return namaMenu; }
        public String getDetailMenu() { return detailMenu; }
        public String getKalori() { return kalori; }
        public String getActions() { return actions; }
    }
    @FXML
private void handleBukaDashboard(ActionEvent event) {
    Redirect.redirectPage(event,"/com/mycompany/embg/app/fxml/vendor/VendorDashboard.fxml");}

@FXML
private void handleBukaShipments(ActionEvent event) { 
Redirect.redirectPage(event,"/com/mycompany/embg/app/fxml/vendor/ShipmentManagement.fxml");}

@FXML
private void handleBukaInventory(ActionEvent event) { 
    Redirect.redirectPage(event,"/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml");}



    @FXML
    private void handleLogout(ActionEvent event) {
        com.mycompany.embg.app.services.UserSession.clearSession();
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/auth/LoginPage.fxml");
    }
}