package com.mycompany.embg.app.controllers.vendor; // Sesuaikan dengan package utama Anda

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.mycompany.embg.app.services.Redirect;

public class ShipmentManagementController implements Initializable {

    @FXML private TextField txtSearchGlobal;
    @FXML private Button btnNewReport;
    
    // Properti Dropdown Status tiap Kartu
    @FXML private ComboBox<String> comboStatus1;
    @FXML private ComboBox<String> comboStatus2;
    @FXML private ComboBox<String> comboStatus3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Isian Opsi Dropdown Status Sesuai Mockup
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Dimasak", "Diantar", "Sampai");
        
        comboStatus1.setItems(statusOptions);
        comboStatus2.setItems(statusOptions);
        comboStatus3.setItems(statusOptions);

        // 2. Default Status Pilihan Awal Masing-masing Sekolah
        comboStatus1.setValue("Dimasak");
        comboStatus2.setValue("Diantar");
        comboStatus3.setValue("Sampai");
    }    

    // --- TRIGGER ASSIGN ROUTE BARU ---
    @FXML
    private void handleAssignNewRoute(MouseEvent event) {
        System.out.println("Membuka form penugasan rute pengiriman sekolah baru...");
    }
    // --- NAVIGASI MULTI-PAGE KE DASHBOARD ---
    // --- NAVIGASI MULTI-PAGE KE VENDORS (MANAJEMEN MENU) ---
    // --- NAVIGASI MULTI-PAGE KE INVENTORY ---
    // Fungsi pembantu ganti root window




    @FXML
private void handleBukaDashboard(ActionEvent event) { 
Redirect.redirectPage(event,"/com/mycompany/embg/app/fxml/vendor/VendorDashboard.fxml");
}

@FXML
private void handleBukaVendors(ActionEvent event) { 
    Redirect.redirectPage(event,"/com/mycompany/embg/app/fxml/vendor/MenuManagement.fxml"); 
}

@FXML
private void handleBukaInventory(ActionEvent event) { 
    Redirect.redirectPage(event,"/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml"); }


}