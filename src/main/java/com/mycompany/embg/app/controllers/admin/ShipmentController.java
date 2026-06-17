package com.mycompany.embg.app.controllers.admin;

import com.mycompany.embg.app.models.Shipment;
import com.mycompany.embg.app.repository.ShipmentRepo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ShipmentController {

    @FXML
    private TableView<Shipment> shipmentTable;

    @FXML
    private ComboBox<String> vendorCombo;

    @FXML
    private ComboBox<String> schoolCombo;

    @FXML
    public void initialize() {

        TableColumn<Shipment, String> colSekolah =
                new TableColumn<>("Sekolah");

        colSekolah.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getNamaSekolah()
                )
        );

        TableColumn<Shipment, String> colMenu =
                new TableColumn<>("Menu");

        colMenu.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getMenu()
                )
        );

        TableColumn<Shipment, Number> colPorsi =
                new TableColumn<>("Porsi");

        colPorsi.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getJumlahPorsi()
                )
        );

        TableColumn<Shipment, String> colStatus =
                new TableColumn<>("Status");

        colStatus.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStatus()
                )
        );

        shipmentTable.getColumns().addAll(
                colSekolah,
                colMenu,
                colPorsi,
                colStatus
        );

        ShipmentRepo repo = new ShipmentRepo();

        shipmentTable.setItems(
                FXCollections.observableArrayList(
                        repo.getAllShipments()
                )
        );

        vendorCombo.getItems().addAll(
                "All Vendors",
                "Vendor A",
                "Vendor B"
        );

        vendorCombo.setValue("All Vendors");

        schoolCombo.getItems().addAll(
                "All Schools",
                "SD Negeri 01 Menteng",
                "SMP Bina Bangsa",
                "SMA 4 Jakarta"
        );

        schoolCombo.setValue("All Schools");
    }
}