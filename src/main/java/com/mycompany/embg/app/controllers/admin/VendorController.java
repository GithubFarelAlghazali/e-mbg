package com.mycompany.embg.app.controllers.admin;

import com.mycompany.embg.app.models.Vendor;
import com.mycompany.embg.app.repository.UserRepo;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import com.mycompany.embg.app.services.AlertPopup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class VendorController {

    @FXML
    private TableView<Vendor> vendorTable;
    @FXML
    private VBox detailPanel;
    @FXML
    private Label detailNama;
    @FXML
    private Label detailAlamat;
    @FXML
    private Label detailStatus;

    private UserRepo userRepo;
    private Vendor selectedVendor;

    public VendorController() {
        try {
            this.userRepo = new UserRepo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        setupTable();
        loadDataFromDatabase(); // Panggil data dari DB, bukan dummy lagi

        vendorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedVendor = newSelection;
                tampilkanDetailVendor(newSelection);
            }
        });
    }
    
    private void setupTable() {
        TableColumn<Vendor, String> colNama = new TableColumn<>("Nama Vendor");
        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<Vendor, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlamat()));

        TableColumn<Vendor, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isApproved() ? "Approved" : "Pending Review"));

        vendorTable.getColumns().addAll(colNama, colAlamat, colStatus);
    }

    private void tampilkanDetailVendor(Vendor vendor) {
        detailNama.setText(vendor.getUsername());
        detailAlamat.setText(vendor.getAlamat());
        detailStatus.setText(vendor.isApproved() ? "Terverifikasi (Approved)" : "Menunggu Verifikasi (Pending)");
        detailPanel.setVisible(true);
    }

    @FXML
    private void handleVerifikasi() {
        if (selectedVendor == null) {
            return;
        }

        // Decision Node: "Apakah dokumen lengkap?" (Kita gunakan AlertPopup Dialog)
        javafx.scene.control.Alert checkDialog = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        checkDialog.setTitle("Pengecekan Dokumen");
        checkDialog.setHeaderText("Verifikasi Vendor: " + selectedVendor.getUsername());
        checkDialog.setContentText("Apakah dokumen vendor ini sudah lengkap dan valid?");

        ButtonType btnYa = new ButtonType("Ya, Lengkap");
        ButtonType btnTidak = new ButtonType("Tidak Lengkap", ButtonBar.ButtonData.CANCEL_CLOSE);
        checkDialog.getButtonTypes().setAll(btnYa, btnTidak);

        Optional<ButtonType> result = checkDialog.showAndWait();

        try {
            if (result.isPresent() && result.get() == btnYa) {
                // Alur YA -> Mengaktifkan akun -> Pesan "Verifikasi Berhasil"
                userRepo.updateVendorApproval(selectedVendor.getId(), true);
                selectedVendor.setApproval(true);

                AlertPopup.showAlert(Alert.AlertType.INFORMATION, "Verifikasi Berhasil");
            } else {
                // Alur TIDAK -> Menolak verifikasi -> Pesan "Dokumen Belum Lengkap"
                userRepo.updateVendorApproval(selectedVendor.getId(), false);
                selectedVendor.setApproval(false);

                AlertPopup.showAlert(Alert.AlertType.WARNING, "Dokumen Belum Lengkap");
            }

            // Refresh detail & tabel
            tampilkanDetailVendor(selectedVendor);
            vendorTable.refresh();

        } catch (SQLException e) {
            AlertPopup.showAlert(AlertType.ERROR, "Gagal memperbarui status vendor : " + e);
        }
    }


    private void loadDataFromDatabase() {
        vendorTable.getItems().clear(); // Bersihkan data di tabel

        try {
            List<Vendor> vendors = userRepo.getAllVendors();

            // Logika Empty State
            if (vendors.isEmpty()) {
                Label emptyLabel = new Label("Belum ada vendor yang mendaftar.");
                emptyLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px; -fx-font-style: italic;");
                vendorTable.setPlaceholder(emptyLabel);
            } else {
                vendorTable.getItems().addAll(vendors);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Gagal memuat data dari database.");
            errorLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 14px; -fx-font-weight: bold;");
            vendorTable.setPlaceholder(errorLabel);
        }
    }
    
    
}
