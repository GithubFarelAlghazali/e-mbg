package com.mycompany.embg.app.controllers.vendor;

import com.mycompany.embg.app.config.DbConfig;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.mycompany.embg.app.services.UserSession;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TambahMenuController implements Initializable {

    @FXML
    private TextField txtNamaProduk;
    @FXML
    private Spinner<Integer> spinnerHarga;
    @FXML
    private Spinner<Integer> spinnerKalori;
    @FXML
    private Spinner<Integer> spinnerProtein;
    @FXML
    private Spinner<Integer> spinnerLemak;
    @FXML
    private Spinner<Integer> spinnerKarbo;
    @FXML
    private Label lblError;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnTutup;

    // "menu" untuk halaman ini, "bahan" untuk inventaris
    // Di-set dari luar jika perlu, default "menu"
    private String tipeProduk = "menu";

    // Callback agar halaman pemanggil bisa refresh setelah simpan
    private Runnable onSimpanCallback;

    public void setTipeProduk(String tipe) {
        this.tipeProduk = tipe;
    }

    public void setOnSimpanCallback(Runnable callback) {
        this.onSimpanCallback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spinnerHarga.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999999, 15000, 500));
        spinnerKalori.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0));
        spinnerProtein.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0));
        spinnerLemak.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0));
        spinnerKarbo.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0));

        // Buat spinner editable
        spinnerHarga.setEditable(true);
        spinnerKalori.setEditable(true);
        spinnerProtein.setEditable(true);
        spinnerLemak.setEditable(true);
        spinnerKarbo.setEditable(true);
    }

    @FXML
    private void handleSimpan(ActionEvent event) {
        lblError.setText("");

        // --- Validasi ---
        String nama = txtNamaProduk.getText().trim();
        if (nama.isEmpty()) {
            lblError.setText("Nama menu tidak boleh kosong.");
            return;
        }

        int harga = spinnerHarga.getValue();
        int kalori = spinnerKalori.getValue();
        int protein = spinnerProtein.getValue();
        int lemak = spinnerLemak.getValue();
        int karbo = spinnerKarbo.getValue();

        try (Connection conn = DbConfig.getConnection()) {

            // STEP 1: Insert ke tabel nilai_gizi, ambil UUID yang di-generate
            String sqlGizi
                    = "INSERT INTO nilai_gizi (kalori, protein, lemak, karbohidrat) "
                    + "VALUES (?, ?, ?, ?) RETURNING id";

            String nilaiGiziId = null;

            try (PreparedStatement ps = conn.prepareStatement(sqlGizi)) {
                ps.setFloat(1, kalori);
                ps.setFloat(2, protein);
                ps.setFloat(3, lemak);
                ps.setFloat(4, karbo);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    nilaiGiziId = rs.getString("id");
                }
            }

            if (nilaiGiziId == null) {
                lblError.setText("Gagal menyimpan data gizi.");
                return;
            }

            // STEP 2: Insert ke tabel products dengan nilai_gizi_id dari step 1
            String sqlProduk
                    = "INSERT INTO menu (nama_menu, budget, nilai_gizi_id, vendor_id) "
                    + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sqlProduk)) {
                ps.setString(1, nama);
                ps.setInt(2, harga);
                ps.setObject(3, java.util.UUID.fromString(nilaiGiziId));
                ps.setObject(4, java.util.UUID.fromString(UserSession.getCurrentUserId()));

                ps.executeUpdate();
            }

            System.out.println("Menu berhasil disimpan: ");

            if (onSimpanCallback != null) {
                onSimpanCallback.run();
            }

            tutupDialog();

        } catch (SQLException e) {
            lblError.setText("Gagal menyimpan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTutup(ActionEvent event) {
        tutupDialog();
    }

    private void tutupDialog() {
        Stage stage = (Stage) btnSimpan.getScene().getWindow();
        stage.close();
    }
}
