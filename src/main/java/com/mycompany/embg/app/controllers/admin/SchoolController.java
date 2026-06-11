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

public class SchoolController {

    @FXML
    private TableView<String[]> schoolTable;

    @FXML
    public void initialize() {
        addColumn("Nama Sekolah", 0);
        addColumn("NPSN", 1);
        addColumn("Tanggal Daftar", 2);
        addColumn("Status", 3);
        addColumn("Actions", 4);

        schoolTable.getItems().addAll(
                new String[]{"SDN 01 Menteng Pagi", "20101234", "24 Oct 2023", "Menunggu Verifikasi", "Review"},
                new String[]{"SMPN 255 Jakarta", "20105678", "23 Oct 2023", "Menunggu Verifikasi", "Review"},
                new String[]{"SMAN 8 Jakarta", "20109012", "22 Oct 2023", "Menunggu Verifikasi", "Review"},
                new String[]{"SDN 15 Klender", "20103456", "21 Oct 2023", "Terverifikasi", "Processed"}
        );
    }

    private void addColumn(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        schoolTable.getColumns().add(column);
    }
}