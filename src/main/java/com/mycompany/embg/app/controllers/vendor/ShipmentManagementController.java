package com.mycompany.embg.app.controllers.vendor;

import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.embg.app.repository.ShipmentRepo;
import com.mycompany.embg.app.services.Redirect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

        ObservableList<String> statusOptions =
                FXCollections.observableArrayList(
                        "Dimasak",
                        "Dikirim"
                );

        comboStatus1.setItems(statusOptions);
        comboStatus2.setItems(statusOptions);
        comboStatus3.setItems(statusOptions);

        if (shipmentRepo.getAllShipments().size() >= 3) {

            comboStatus1.setValue(
                    shipmentRepo.getAllShipments()
                            .get(0)
                            .getStatus()
            );

            comboStatus2.setValue(
                    shipmentRepo.getAllShipments()
                            .get(1)
                            .getStatus()
            );

            comboStatus3.setValue(
                    shipmentRepo.getAllShipments()
                            .get(2)
                            .getStatus()
            );
        }

        comboStatus1.setOnAction(event -> {
            shipmentRepo.updateStatus(
                    "1",
                    comboStatus1.getValue()
            );

            System.out.println(
                    "Shipment 1 => "
                            + comboStatus1.getValue()
            );
        });

        comboStatus2.setOnAction(event -> {
            shipmentRepo.updateStatus(
                    "2",
                    comboStatus2.getValue()
            );

            System.out.println(
                    "Shipment 2 => "
                            + comboStatus2.getValue()
            );
        });

        comboStatus3.setOnAction(event -> {
            shipmentRepo.updateStatus(
                    "3",
                    comboStatus3.getValue()
            );

            System.out.println(
                    "Shipment 3 => "
                            + comboStatus3.getValue()
            );
        });
    }

    @FXML
    private void handleAssignNewRoute(MouseEvent event) {

        System.out.println(
                "Membuka form penugasan rute pengiriman..."
        );
    }

    @FXML
    private void handleBukaDashboard(ActionEvent event) {

        Redirect.redirectPage(
                event,
                "/com/mycompany/embg/app/fxml/vendor/VendorDashboard.fxml"
        );
    }

    @FXML
    private void handleBukaVendors(ActionEvent event) {

        Redirect.redirectPage(
                event,
                "/com/mycompany/embg/app/fxml/vendor/MenuManagement.fxml"
        );
    }

    @FXML
    private void handleBukaInventory(ActionEvent event) {

        Redirect.redirectPage(
                event,
                "/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml"
        );
    }
}