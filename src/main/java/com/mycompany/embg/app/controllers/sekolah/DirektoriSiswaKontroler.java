package com.mycompany.embg.app.controllers.sekolah;

import com.mycompany.embg.app.models.SiswaKebutuhanKhusus;
import com.mycompany.embg.app.repository.SiswaRepo;
import com.mycompany.embg.app.services.AlertPopup;
import com.mycompany.embg.app.services.Redirect;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;

public class DirektoriSiswaKontroler {

    @FXML
    private ComboBox<String> filterKelas;

    // Panel Interaktif
    @FXML
    private VBox tabelPanel;
    @FXML
    private TableView<SiswaKebutuhanKhusus> tabelSiswa;

    @FXML
    private VBox emptyStatePanel;
    @FXML
    private TextField inputTotalSiswa;

    @FXML
    private VBox formPanel;
    @FXML
    private TextField inputNama;
    @FXML
    private TextField inputKelas; // Sekarang menjadi TextField
    @FXML
    private TextField inputAlergi; // Sekarang menjadi TextField

    private SiswaRepo siswaRepo;
    private String idSekolah; // Nanti diganti dengan session aktif

    public DirektoriSiswaKontroler() {
        try {
            this.siswaRepo = new SiswaRepo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        this.idSekolah = com.mycompany.embg.app.services.UserSession.getCurrentUserId();
        setupComboBoxes();
        setupTable();
        loadDataSiswa();
    }

    private void setupComboBoxes() {
        filterKelas.getItems().addAll("Semua Kelas", "Kelas 1", "Kelas 2", "Kelas 3", "Kelas 4", "Kelas 5", "Kelas 6");
        filterKelas.getSelectionModel().selectFirst();
    }

    private void setupTable() {
        TableColumn<SiswaKebutuhanKhusus, String> colNama = new TableColumn<>("Nama Siswa");
        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));

        TableColumn<SiswaKebutuhanKhusus, String> colId = new TableColumn<>("Nomor ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getId() != null ? data.getValue().getId() : "-"
        ));

        TableColumn<SiswaKebutuhanKhusus, String> colKelas = new TableColumn<>("Kelas");
        colKelas.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKelas()));

        TableColumn<SiswaKebutuhanKhusus, String> colDiet = new TableColumn<>("Pantangan Makanan");
        colDiet.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlergi()));

        tabelSiswa.getColumns().addAll(colNama, colId, colKelas, colDiet);
        tabelSiswa.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadDataSiswa() {
        tabelSiswa.getItems().clear();
        try {
            List<SiswaKebutuhanKhusus> dataSiswa = siswaRepo.getSiswaKhususBySekolah(idSekolah);

            if (dataSiswa.isEmpty()) {
                // Sembunyikan tabel, munculkan form pengisian awal Total Siswa
                tabelPanel.setVisible(false);
                tabelPanel.setManaged(false);

                emptyStatePanel.setVisible(true);
                emptyStatePanel.setManaged(true);
            } else {
                // Sembunyikan form awal, munculkan tabel
                emptyStatePanel.setVisible(false);
                emptyStatePanel.setManaged(false);

                tabelPanel.setVisible(true);
                tabelPanel.setManaged(true);

                tabelSiswa.getItems().addAll(dataSiswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void simpanTotalSiswa(ActionEvent event) {
        if (inputTotalSiswa.getText().isEmpty()) {
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Harap masukkan jumlah siswa terlebih dahulu.");
            return;
        }

        try {
            int totalSiswa = Integer.parseInt(inputTotalSiswa.getText());
            siswaRepo.updateTotalPorsi(idSekolah, totalSiswa); // Memanggil query di SiswaRepo

            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Jumlah total porsi siswa berhasil disimpan!");
            inputTotalSiswa.clear();

        } catch (NumberFormatException e) {
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Format Salah ! Harap masukkan angka yang valid.");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Gagal menyimpan jumlah total siswa ke server : " + e);
        }
    }

    @FXML
    void aksiTambahSiswa(ActionEvent event) {
        // Sembunyikan tabel dan empty state
        tabelPanel.setVisible(false);
        tabelPanel.setManaged(false);
        emptyStatePanel.setVisible(false);
        emptyStatePanel.setManaged(false);

        // Munculkan Form
        formPanel.setVisible(true);
        formPanel.setManaged(true);
    }

    @FXML
    void batalTambahSiswa(ActionEvent event) {
        formPanel.setVisible(false);
        formPanel.setManaged(false);

        kosongkanForm();
        loadDataSiswa(); // Reload untuk mengecek kembali apakah data kosong atau tidak
    }

    @FXML
    void simpanDataSiswa(ActionEvent event) {
        if (inputNama.getText().isEmpty() || inputKelas.getText().isEmpty()) {
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Harap isi Nama dan Kelas !");
            return;
        }

        String alergiVal = inputAlergi.getText().isEmpty() ? "Tidak Ada" : inputAlergi.getText();

        SiswaKebutuhanKhusus siswaBaru = new SiswaKebutuhanKhusus(
                null,
                alergiVal,
                inputNama.getText(),
                idSekolah,
                inputKelas.getText()
        );

        try {
            siswaRepo.tambahSiswaKhusus(siswaBaru);
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Data siswa alergi berhasil disimpan.");

            batalTambahSiswa(null);

        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Gagal menyimpan data siswa : " + e);
        }
    }

    private void kosongkanForm() {
        inputNama.clear();
        inputKelas.clear();
        inputAlergi.clear();
    }

    @FXML
    void aksiEkspor(ActionEvent event) {
        System.out.println("Tindakan: Mengekspor daftar direktori siswa ke dokumen...");
    }

    @FXML
    void aksiKeluar(ActionEvent event) {
        com.mycompany.embg.app.services.UserSession.clearSession();
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/auth/LoginPage.fxml");
    }

    // --- Navigasi Sidebar ---
    @FXML
    private void clickDashboard(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/Dashboard.fxml");
    }

    @FXML
    private void clickSiswa(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/DirektoriSiswa.fxml");
    }

    @FXML
    private void clickDistribusi(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/PelacakMakanan.fxml");
    }

    @FXML
    private void clickProfil(ActionEvent event) {
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/sekolah/ProfilSekolah.fxml");
    }
}
