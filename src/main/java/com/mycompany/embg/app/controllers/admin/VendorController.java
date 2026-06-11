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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VendorController {

    @FXML
    private TableView<String[]> vendorTable;

    @FXML
    public void initialize() {
        addColumn("Nama Vendor", 0);
        addColumn("Alamat", 1);
        addColumn("Tanggal Daftar", 2);
        addColumn("Status", 3);
        addColumn("Actions", 4);

        vendorTable.getItems().addAll(
                new String[]{"PT Sumber Pangan", "Jl. Merdeka No 12, Jakarta", "12 Oct 2023", "Pending Review", "View Detail"},
                new String[]{"Katering Bu Dasiyem", "Jl. Kaliurang KM 5, Sleman", "12 Oct 2023", "Pending Review", "View Detail"},
                new String[]{"CV Makmur Jaya", "Kawasan Industri Pulo Gadung", "10 Oct 2023", "Approved", "View Detail"},
                new String[]{"Toko Berkah", "Pasar Induk Kramat Jati", "09 Oct 2023", "Rejected", "View Reason"}
        );
    }

    private void addColumn(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        vendorTable.getColumns().add(column);
    }
}