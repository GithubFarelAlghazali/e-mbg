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

public class DashboardController {

    @FXML
    private TableView<String[]> dashboardTable;

    @FXML
    public void initialize() {
        addColumn("Vendor", 0);
        addColumn("Sekolah", 1);
        addColumn("Menu", 2);
        addColumn("Status", 3);
        addColumn("Estimasi", 4);

        dashboardTable.getItems().addAll(
                new String[]{"CV. Dapur Nusantara", "SDN 01 Pagi Jakarta", "Nasi Ayam Bakar", "Dimasak", "10:30 AM"},
                new String[]{"PT. Rasa Indonesia", "SMPN 12 Bandung", "Nasi Ikan Goreng", "Diantar", "11:15 AM"},
                new String[]{"Katering Mapan", "SMAN 3 Surabaya", "Nasi Telur Balado", "Sampai", "09:45 AM"},
                new String[]{"CV. Berkah Jaya", "SDN 05 Petang", "Nasi Sayur Lodeh", "Diantar", "11:30 AM"}
        );
    }

    private void addColumn(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        dashboardTable.getColumns().add(column);
    }
}