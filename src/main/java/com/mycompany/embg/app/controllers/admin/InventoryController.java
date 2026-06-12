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

public class InventoryController {

    @FXML
    private TableView<String[]> inventoryTable;

    @FXML
    public void initialize() {
        addColumn("Nama Bahan", 0);
        addColumn("Kategori", 1);
        addColumn("Stok", 2);
        addColumn("Satuan", 3);
        addColumn("Status", 4);

        inventoryTable.getItems().addAll(
                new String[]{"Beras", "Karbohidrat", "250", "Kg", "Aman"},
                new String[]{"Ayam", "Protein", "80", "Kg", "Aman"},
                new String[]{"Telur", "Protein", "35", "Rak", "Menipis"},
                new String[]{"Wortel", "Sayur", "20", "Kg", "Menipis"}
        );
    }

    private void addColumn(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        inventoryTable.getColumns().add(column);
    }
}