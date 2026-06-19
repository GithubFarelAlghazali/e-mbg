package com.mycompany.embg.app.controllers.admin;

import com.mycompany.embg.app.models.JadwalPengiriman;
import com.mycompany.embg.app.repository.JadwalRepo;
import com.mycompany.embg.app.services.AlertPopup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller halaman Shipment untuk ADMIN.
 * Admin hanya bisa MELIHAT (read-only) semua status pengiriman.
 * Admin TIDAK bisa mengubah status apapun.
 */
public class AdminShipmentController {

    @FXML private TableView<JadwalPengiriman> tblShipment;
    @FXML private TableColumn<JadwalPengiriman, String> colVendor;
    @FXML private TableColumn<JadwalPengiriman, String> colSekolah;
    @FXML private TableColumn<JadwalPengiriman, String> colMenu;
    @FXML private TableColumn<JadwalPengiriman, String> colPorsi;
    @FXML private TableColumn<JadwalPengiriman, String> colTanggal;
    @FXML private TableColumn<JadwalPengiriman, String> colStatus;

    @FXML private Label lblTotalPengiriman;
    @FXML private Label lblDimasak;
    @FXML private Label lblDikirim;
    @FXML private Label lblDiterima;

    private JadwalRepo jadwalRepo;

    @FXML
    public void initialize() {
        jadwalRepo = new JadwalRepo();
        setupTableColumns();
        loadAllShipments();
    }

    private void setupTableColumns() {
        colVendor.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNamaVendor()));
        colSekolah.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNamaSekolah()));
        colMenu.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNamaMenu()));
        colPorsi.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getJumlahPorsi())));
        colTanggal.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTanggal()));

        // Kolom status dengan pewarnaan — admin hanya melihat, tidak ada aksi
        colStatus.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatus()));
        colStatus.setCellFactory(column -> new TableCell<JadwalPengiriman, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status.substring(0, 1).toUpperCase() + status.substring(1));
                    switch (status.toLowerCase()) {
                        case "dimasak" ->
                            setStyle("-fx-text-fill: #b45309; -fx-font-weight: bold;"); // oranye
                        case "dikirim" ->
                            setStyle("-fx-text-fill: #1d4ed8; -fx-font-weight: bold;"); // biru
                        case "diterima" ->
                            setStyle("-fx-text-fill: #15803d; -fx-font-weight: bold;"); // hijau
                        default ->
                            setStyle("-fx-text-fill: #64748b;");
                    }
                }
            }
        });

        // Tabel read-only — nonaktifkan seleksi agar tidak membingungkan
        tblShipment.setEditable(false);
    }

    private void loadAllShipments() {
        try {
            List<JadwalPengiriman> data = jadwalRepo.getAllJadwal();
            ObservableList<JadwalPengiriman> list = FXCollections.observableArrayList(data);
            tblShipment.setItems(list);
            updateSummaryLabels(data);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal memuat data pengiriman: " + e.getMessage());
        }
    }

    private void updateSummaryLabels(List<JadwalPengiriman> data) {
        long dimasak  = data.stream().filter(j -> "dimasak".equalsIgnoreCase(j.getStatus())).count();
        long dikirim  = data.stream().filter(j -> "dikirim".equalsIgnoreCase(j.getStatus())).count();
        long diterima = data.stream().filter(j -> "diterima".equalsIgnoreCase(j.getStatus())).count();

        if (lblTotalPengiriman != null) lblTotalPengiriman.setText(String.valueOf(data.size()));
        if (lblDimasak  != null) lblDimasak.setText(String.valueOf(dimasak));
        if (lblDikirim  != null) lblDikirim.setText(String.valueOf(dikirim));
        if (lblDiterima != null) lblDiterima.setText(String.valueOf(diterima));
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        loadAllShipments();
    }
}
