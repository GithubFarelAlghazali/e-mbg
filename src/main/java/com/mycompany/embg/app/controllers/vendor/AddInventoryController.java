package com.mycompany.embg.app.controllers.vendor;

import com.mycompany.embg.app.models.BahanMakanan;
import com.mycompany.embg.app.repository.InventoryRepo;
import com.mycompany.embg.app.services.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.UUID;

public class AddInventoryController {

    @FXML
    private TextField txtNamaBarang;

    @FXML
    private TextField txtStok;

    @FXML
    private TextField txtSatuan;

    @FXML
    private TextField txtHarga;

    @FXML
    private void handleSave() {

        try {

            InventoryRepo repo = new InventoryRepo();

            BahanMakanan item =
                    new BahanMakanan(
                            txtNamaBarang.getText(),
                            Integer.parseInt(txtHarga.getText()),
                            Integer.parseInt(txtStok.getText()),
                            txtSatuan.getText(),
                            UUID.randomUUID().toString(),
                            UserSession.getCurrentUserId()
                    );

            repo.addItem(item);

            Stage stage =
                    (Stage) txtNamaBarang.getScene().getWindow();

            stage.close();

        } catch (NumberFormatException e) {

            System.out.println("Stok dan Harga harus berupa angka!");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}