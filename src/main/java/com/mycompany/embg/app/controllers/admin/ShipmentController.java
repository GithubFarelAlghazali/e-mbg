/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.controllers.admin;

/**
 *
 * @author HP
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ShipmentController {

    @FXML
    private ComboBox<String> vendorCombo;

    @FXML
    private ComboBox<String> schoolCombo;

    @FXML
    private TableView<String[]> shipmentTable;

    @FXML
    public void initialize() {
        vendorCombo.getItems().addAll("All Vendors", "Catering Sehat Ibu", "Dapur Nusantara", "CV Maju Bersama");
        vendorCombo.setValue("All Vendors");

        schoolCombo.getItems().addAll("All Schools", "SDN 01 Pagi", "SMPN 15 Jakarta", "SMAN 8");
        schoolCombo.setValue("All Schools");

        addColumn("Vendor", 0);
        addColumn("School", 1);
        addColumn("Menu", 2);
        addColumn("Jumlah Porsi", 3);
        addColumn("Status", 4);
        addColumn("ETA", 5);

        shipmentTable.getItems().addAll(
                new String[]{"Catering Sehat Ibu", "SDN 01 Pagi", "Nasi Ayam Bakar, Sayur Asem, Pisang", "450", "Dimasak", "10:30 AM"},
                new String[]{"Dapur Nusantara", "SMPN 15 Jakarta", "Nasi Ikan Goreng, Capcay, Jeruk", "620", "Diantar", "11:15 AM"},
                new String[]{"CV Maju Bersama", "SMAN 8", "Nasi Rendang Sapi, Daun Singkong", "850", "Sampai", "09:45 AM"},
                new String[]{"Catering Sehat Ibu", "SDN 05 Petang", "Nasi Ayam Bakar, Sayur Asem", "320", "Diantar", "11:30 AM"}
        );
    }

    private void addColumn(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        shipmentTable.getColumns().add(column);
    }
}