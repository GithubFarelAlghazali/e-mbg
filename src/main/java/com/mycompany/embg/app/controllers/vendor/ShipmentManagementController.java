package com.mycompany.embg.app.controllers.vendor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.embg.app.repository.ShipmentRepo;
import com.mycompany.embg.app.services.Redirect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShipmentManagementController implements Initializable {

    @FXML
    private TextField txtSearchGlobal;
    @FXML
    private Button btnNewReport;
    @FXML
    private ComboBox<String> comboStatus1;
    @FXML
    private ComboBox<String> comboStatus2;
    @FXML
    private ComboBox<String> comboStatus3;

    private ShipmentRepo shipmentRepo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        shipmentRepo = new ShipmentRepo();

        ObservableList<String> statusOptions
                = FXCollections.observableArrayList("Dimasak", "Dikirim");

        comboStatus1.setItems(statusOptions);
        comboStatus2.setItems(statusOptions);
        comboStatus3.setItems(statusOptions);

        if (shipmentRepo.getAllShipments().size() >= 3) {
            comboStatus1.setValue(shipmentRepo.getAllShipments().get(0).getStatus());
            comboStatus2.setValue(shipmentRepo.getAllShipments().get(1).getStatus());
            comboStatus3.setValue(shipmentRepo.getAllShipments().get(2).getStatus());
        }

        comboStatus1.setOnAction(event
                -> shipmentRepo.updateStatus("1", comboStatus1.getValue()));

        comboStatus2.setOnAction(event
                -> shipmentRepo.updateStatus("2", comboStatus2.getValue()));

        comboStatus3.setOnAction(event
                -> shipmentRepo.updateStatus("3", comboStatus3.getValue()));
    }

    // -------------------------------------------------------
    // Tombol "Assign New Route" — buka dialog tambah jadwal
    // -------------------------------------------------------
    @FXML
    private void handleAssignNewRoute(MouseEvent event) {
        bukaTambahJadwalDialog();
    }

    private void bukaTambahJadwalDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/mycompany/embg/app/fxml/vendor/TambahJadwalDialog.fxml"
            ));
            Parent root = loader.load();

            // Ambil controller dan set callback refresh
            TambahJadwalController controller = loader.getController();
            controller.setOnSimpanCallback(() -> {
                // TODO: refresh kartu jadwal di halaman ini jika sudah dinamis
                System.out.println("Jadwal baru tersimpan, siap refresh.");
            });

            // Buat Stage dialog (modal)
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Tambah Jadwal Pengiriman");

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            dialog.setScene(scene);
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal membuka dialog: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Navigasi sidebar
    // -------------------------------------------------------
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
