package com.mycompany.embg.app.controllers.admin;

import com.mycompany.embg.app.models.Shipment;
import com.mycompany.embg.app.repository.ShipmentRepo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminShipmentController {

    @FXML
    private TableView<Shipment> tblShipment;

    @FXML
    private TableColumn<Shipment,String> colSekolah;

    @FXML
    private TableColumn<Shipment,String> colStatus;

    @FXML
    public void initialize() {

        colSekolah.setCellValueFactory(
                new PropertyValueFactory<>(
                        "namaSekolah"
                )
        );

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>(
                        "status"
                )
        );

        ShipmentRepo repo =
                new ShipmentRepo();

        tblShipment.setItems(
                FXCollections.observableArrayList(
                        repo.getAllShipments()
                )
        );
    }
}