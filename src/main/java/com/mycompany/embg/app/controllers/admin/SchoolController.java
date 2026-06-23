/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.controllers.admin;

/**
 *
 * @author HP
 */

import com.mycompany.embg.app.models.Sekolah;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.mycompany.embg.app.repository.SiswaRepo;import java.sql.SQLException;
import java.util.List;
;

public class SchoolController {

    @FXML
    private TableView<String[]> schoolTable;

    private SiswaRepo sekolahRepo;
    public SchoolController(){
        try {
            this.sekolahRepo = new SiswaRepo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @FXML
    public void initialize() {
        addColumn("No", 0);
        addColumn("Nama Sekolah", 1);
        addColumn("Email", 2);
        addColumn("NPSN", 3);

        loadSchoolData();
    }

    private void loadSchoolData() {
        if (sekolahRepo == null) {
            return;
        }

        try {
            List<Sekolah> listSekolah = sekolahRepo.getSekolah();

            int no = 1;
            for (Sekolah sekolah : listSekolah) {
                // Buat array String sesuai urutan kolom: {No, Nama, Email, NPSN}
                String[] rowData = new String[]{
                    String.valueOf(no++),
                    sekolah.getUsername(), // Menggunakan getUsername() karena SQL mengambil dari kolom 'username'
                    sekolah.getEmail(),
                    sekolah.getNpsn()
                };

                schoolTable.getItems().add(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addColumn(String title, int index) {
        TableColumn<String[], String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[index]));
        schoolTable.getColumns().add(column);
    }
}