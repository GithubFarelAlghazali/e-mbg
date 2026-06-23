package com.mycompany.embg.app.controllers.vendor;

import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.services.UserSession;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TambahJadwalController implements Initializable {

    @FXML
    private Label lblVendorId;
    @FXML
    private ComboBox<String> comboSekolah;
    @FXML
    private ComboBox<String> comboMenu;
    @FXML
    private Spinner<Integer> spinnerPorsi;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label lblError;
    @FXML
    private Button btnSimpan;

    // Map untuk menyimpan nama -> UUID (agar bisa dikirim ke DB)
    private final Map<String, String> sekolahMap = new HashMap<>();
    private final Map<String, String> menuMap = new HashMap<>();

    // Callback agar ShipmentManagement bisa refresh setelah simpan
    private Runnable onSimpanCallback;

    public void setOnSimpanCallback(Runnable callback) {
        this.onSimpanCallback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Tampilkan vendor dari session
        String vendorId = UserSession.getCurrentUserId();
        lblVendorId.setText(vendorId != null ? vendorId : "Tidak ditemukan");


        // Tanggal default hari ini
        datePicker.setValue(LocalDate.now());

        // Spinner porsi — pastikan editable
        SpinnerValueFactory<Integer> factory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999, 100);
        spinnerPorsi.setValueFactory(factory);
        spinnerPorsi.setEditable(true);

        // Load data dari Supabase
        loadSekolah();
        loadMenu();
    }

    // -------------------------------------------------------
    // Query sekolah dari tabel users WHERE role = 'sekolah'
    // -------------------------------------------------------
    private void loadSekolah() {
        try (Connection conn = DbConfig.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "SELECT id, username FROM users WHERE role = 'sekolah' ORDER BY username")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String nama = rs.getString("username");
                sekolahMap.put(nama, id);
            }
            comboSekolah.setItems(
                    FXCollections.observableArrayList(sekolahMap.keySet())
            );

        } catch (SQLException e) {
            lblError.setText("Gagal memuat data sekolah: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------
    // Query menu dari tabel products
    // -------------------------------------------------------
    private void loadMenu() {
        try (Connection conn = DbConfig.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "SELECT id, nama_menu FROM menu ORDER BY nama_menu")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String nama = rs.getString("nama_menu");
                menuMap.put(nama, id);
            }
            comboMenu.setItems(
                    FXCollections.observableArrayList(menuMap.keySet())
            );

        } catch (SQLException e) {
            lblError.setText("Gagal memuat data menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------
    // Simpan jadwal ke tabel jadwal
    // -------------------------------------------------------
    @FXML
    private void handleSimpan(ActionEvent event) {

        lblError.setText("");

        // --- Validasi input ---
        if (comboSekolah.getValue() == null) {
            lblError.setText("Sekolah tujuan wajib dipilih.");
            return;
        }
        if (comboMenu.getValue() == null) {
            lblError.setText("Menu makanan wajib dipilih.");
            return;
        }
        if (datePicker.getValue() == null) {
            lblError.setText("Tanggal pengiriman wajib diisi.");
            return;
        }

        // --- Ambil nilai ---
        String vendorId = UserSession.getCurrentUserId();
        String sekolahId = sekolahMap.get(comboSekolah.getValue());
        String menuId = menuMap.get(comboMenu.getValue());
        int porsi = spinnerPorsi.getValue();
        String tanggal = datePicker.getValue().toString(); // format: yyyy-MM-dd
        String status = "Dimasak";

        if (vendorId == null) {
            lblError.setText("Session vendor tidak ditemukan. Silakan login ulang.");
            return;
        }

        // --- Insert ke DB ---
        String sql = "INSERT INTO jadwal (vendor_id, sekolah_id, menu_id, jumlah_porsi, tanggal, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, java.util.UUID.fromString(vendorId));
            ps.setObject(2, java.util.UUID.fromString(sekolahId));
            ps.setObject(3, java.util.UUID.fromString(menuId));
            ps.setInt(4, porsi);
            ps.setDate(5, Date.valueOf(tanggal));
            ps.setString(6, status);

            ps.executeUpdate();
            System.out.println("Jadwal berhasil disimpan!");

            // Panggil callback (refresh list di ShipmentManagement)
            if (onSimpanCallback != null) {
                onSimpanCallback.run();
            }

            // Tutup dialog
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
