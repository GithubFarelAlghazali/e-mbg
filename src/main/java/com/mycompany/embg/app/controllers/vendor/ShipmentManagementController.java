package com.mycompany.embg.app.controllers.vendor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.embg.app.models.Shipment;
import com.mycompany.embg.app.repository.ShipmentRepo;
import com.mycompany.embg.app.services.AlertPopup;
import com.mycompany.embg.app.services.Redirect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ShipmentManagementController implements Initializable {

    @FXML private TextField txtSearchGlobal;
    @FXML private Button btnNewReport;

    @FXML private ComboBox<String> comboStatus1;
    @FXML private ComboBox<String> comboStatus2;
    @FXML private ComboBox<String> comboStatus3;

    private ShipmentRepo shipmentRepo;

    // Opsi yang boleh dipilih vendor (Diterima tidak termasuk)
    private static final ObservableList<String> VENDOR_STATUS_OPTIONS =
            FXCollections.observableArrayList("Dimasak", "Dikirim");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shipmentRepo = new ShipmentRepo();

        List<Shipment> shipments = shipmentRepo.getAllShipments();

        if (shipments.size() >= 1) setupCombo(comboStatus1, shipments.get(0), "1");
        if (shipments.size() >= 2) setupCombo(comboStatus2, shipments.get(1), "2");
        if (shipments.size() >= 3) setupCombo(comboStatus3, shipments.get(2), "3");
    }

    /**
     * Inisialisasi satu ComboBox:
     * - Kalau status sudah "Diterima" → tampilkan label "Diterima", disable, vendor tidak bisa ubah.
     * - Kalau belum → tampilkan opsi Dimasak / Dikirim, pasang listener update.
     */
    private void setupCombo(ComboBox<String> combo, Shipment shipment, String shipmentId) {
        String currentStatus = shipment.getStatus();

        if ("Diterima".equalsIgnoreCase(currentStatus)) {
            // Status sudah dikonfirmasi sekolah — kunci ComboBox untuk vendor
            combo.setItems(FXCollections.observableArrayList("Diterima"));
            combo.setValue("Diterima");
            combo.setDisable(true);
            combo.setStyle(
                "-fx-opacity: 0.6; " +
                "-fx-background-color: #d1fae5; " +  // hijau muda (diterima)
                "-fx-background-radius: 6;"
            );
        } else {
            // Status masih bisa diubah vendor (Dimasak / Dikirim)
            combo.setItems(VENDOR_STATUS_OPTIONS);
            combo.setValue(currentStatus);
            combo.setDisable(false);

            combo.setOnAction(event -> {
                String statusBaru = combo.getValue();
                String statusLama = shipment.getStatus();

                // Validasi: dari Dimasak hanya boleh ke Dikirim (tidak boleh mundur)
                if ("Dikirim".equalsIgnoreCase(statusLama) && "Dimasak".equalsIgnoreCase(statusBaru)) {
                    AlertPopup.showAlert(AlertType.WARNING,
                            "Status tidak dapat mundur dari 'Dikirim' ke 'Dimasak'.");
                    combo.setValue(statusLama); // kembalikan ke nilai lama
                    return;
                }

                shipmentRepo.updateStatus(shipmentId, statusBaru);
                System.out.println("Shipment " + shipmentId + " → " + statusBaru);
            });
        }
    }

    @FXML
    private void handleAssignNewRoute(MouseEvent event) {
        System.out.println("Membuka form penugasan rute pengiriman...");
    }

    @FXML
    private void handleBukaDashboard(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/VendorDashboard.fxml");
    }

    @FXML
    private void handleBukaVendors(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/MenuManagement.fxml");
    }

    @FXML
    private void handleBukaInventory(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml");
    }
}
