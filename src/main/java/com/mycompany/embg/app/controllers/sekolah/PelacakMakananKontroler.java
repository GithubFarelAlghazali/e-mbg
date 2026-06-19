package com.mycompany.embg.app.controllers.sekolah;

import com.mycompany.embg.app.models.JadwalItem;
import com.mycompany.embg.app.repository.JadwalRepo;
import com.mycompany.embg.app.services.AlertPopup;
import com.mycompany.embg.app.services.Redirect;
import com.mycompany.embg.app.services.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;

import java.sql.SQLException;
import java.util.List;

public class PelacakMakananKontroler {

    @FXML private TableView<JadwalItem> tblPelacakMakan;
    @FXML private TableColumn<JadwalItem, String> colVendor;
    @FXML private TableColumn<JadwalItem, String> colMenu;
    @FXML private TableColumn<JadwalItem, String> colPorsi;
    @FXML private TableColumn<JadwalItem, String> colTanggal;
    @FXML private TableColumn<JadwalItem, String> colStatus;

    private JadwalRepo jadwalRepo;
    private ObservableList<JadwalItem> listJadwal;

    @FXML
    public void initialize() {
        // Kita tidak perlu try-catch di sini karena operasi di bawah ini
        // tidak lagi melempar checked exception SQLException secara langsung.
        // Jika nantinya kamu menambahkan operasi database yang butuh try-catch, 
        // kamu bisa menambahkannya kembali.
        
        jadwalRepo = new JadwalRepo();
        listJadwal = FXCollections.observableArrayList();
        setupTableColumns();
        loadDataPengirimanSekolah();
    }

    private void setupTableColumns() {
        colVendor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaVendor()));
        colMenu.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaMenu()));
        colPorsi.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getJumlahPorsi())));
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggal()));
        
        // Setup cell factory agar status tampil dengan pewarnaan
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        colStatus.setCellFactory(column -> new TableCell<JadwalItem, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equalsIgnoreCase("diterima")) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else if (item.equalsIgnoreCase("dikirim")) {
                        setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void loadDataPengirimanSekolah() {
        try {
            tblPelacakMakan.getItems().clear();
            String idSekolahAktif = UserSession.getCurrentUserId(); 
            
            List<JadwalItem> data = jadwalRepo.getJadwalBySekolah(idSekolahAktif);
            if (data != null) {
                listJadwal.setAll(data);
                tblPelacakMakan.setItems(listJadwal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal memuat data pelacakan: " + e.getMessage());
        }
    }

    @FXML
    void handleKonfirmasiPenerimaan(ActionEvent event) {
        JadwalItem selected = tblPelacakMakan.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            AlertPopup.showAlert(AlertType.WARNING, "Pilih pengiriman yang ingin dikonfirmasi!");
            return;
        }

        if (!"dikirim".equalsIgnoreCase(selected.getStatus())) {
            AlertPopup.showAlert(AlertType.WARNING, "Hanya status 'dikirim' yang bisa dikonfirmasi diterima!");
            return;
        }

        try {
            boolean success = jadwalRepo.konfirmasiPenerimaanSekolah(
                selected.getId(), 
                UserSession.getCurrentUserId()
            );

            if (success) {
                AlertPopup.showAlert(AlertType.INFORMATION, "Penerimaan berhasil dikonfirmasi!");
                loadDataPengirimanSekolah();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal mengonfirmasi: " + e.getMessage());
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadDataPengirimanSekolah();
    }

    // --- Navigasi Sidebar ---
    @FXML private void clickDashboard(ActionEvent event) { Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/Dashboard.fxml"); }
    @FXML private void clickSiswa(ActionEvent event) { Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/DirektoriSiswa.fxml"); }
    @FXML private void clickDistribusi(ActionEvent event) { Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/PelacakMakanan.fxml"); }
    @FXML private void clickProfil(ActionEvent event) { Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/ProfilSekolah.fxml"); }

    @FXML
    private void aksiKeluar(ActionEvent event) {
        UserSession.clearSession();
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/auth/LoginPage.fxml");
    }
}