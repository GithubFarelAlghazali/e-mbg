package com.mycompany.embg.app.controllers.vendor;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.embg.app.models.JadwalPengiriman;
import com.mycompany.embg.app.repository.ShipmentRepo;
import com.mycompany.embg.app.services.Redirect;
import com.mycompany.embg.app.services.UserSession;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import com.mycompany.embg.app.services.AlertPopup;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShipmentManagementController implements Initializable {

    @FXML
    private TextField txtSearchGlobal;
    @FXML
    private Button btnNewReport;

    // HAPUS comboStatus1, 2, 3 karena kita akan generate dinamis
    // TAMBAHKAN id FlowPane dan VBox tombol Assign dari FXML
    @FXML
    private FlowPane cardContainer;
    @FXML
    private VBox btnAssignRouteBox;

    private ShipmentRepo shipmentRepo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Inisialisasi repo (akan melempar SQLException jika koneksi DB gagal)
            shipmentRepo = new ShipmentRepo();

            // Panggil method untuk me-render card dari database
            muatDataShipment();

        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Koneksi Database Gagal: " + e.getMessage());
        }
    }

    private void muatDataShipment() {
        if (cardContainer == null) {
            return;
        }

        // 1. Bersihkan card lama (tapi tombol assign jangan sampai hilang selamanya)
        cardContainer.getChildren().clear();

        // TODO: Ganti ini dengan ID Vendor yang sedang login di sesimu
        String currentVendorId = UserSession.getCurrentUserId();

        try {
            // 2. Ambil data asli dari Database via Repo
            List<JadwalPengiriman> listJadwal = shipmentRepo.getJadwal(currentVendorId);
            ObservableList<String> statusOptions = FXCollections.observableArrayList("Dimasak", "Dikirim", "Diterima");

            // 3. Looping data dan buat UI Card-nya satu per satu
            for (JadwalPengiriman jadwal : listJadwal) {
                VBox card = buatCardShipment(jadwal, statusOptions);
                cardContainer.getChildren().add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertPopup.showAlert(AlertType.ERROR, "Gagal memuat jadwal pengiriman: " + e.getMessage());
        }

        // 4. Pastikan tombol "Assign New Route" selalu ada di posisi paling akhir (paling bawah/kanan)
        if (btnAssignRouteBox != null) {
            cardContainer.getChildren().add(btnAssignRouteBox);
        }
    }

    // Method pembantu untuk menggambar desain card menggunakan Java (mirip seperti di FXML)
    private VBox buatCardShipment(JadwalPengiriman jadwal, ObservableList<String> statusOptions) {
        VBox card = new VBox(10);
        card.setPrefSize(233.0, 231.0);
        card.setStyle("-fx-background-color: #2DACF0; -fx-background-radius: 12; -fx-padding: 15;");

        // --- Header (Icon & Nama Sekolah) ---
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label icon = new Label("🎓");
        icon.setStyle("-fx-font-size: 16px;");

        VBox titleBox = new VBox();
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        // Menampilkan ID Sekolah (Jika di DB kamu ini merujuk ke ID, pertimbangkan untuk di-JOIN dengan tabel sekolah agar tampil nama)
        Label title = new Label(jadwal.getSekolahId());
        title.setFont(Font.font("System", FontWeight.BOLD, 13));
        title.setStyle("-fx-text-fill: #1E152A;");
        title.setWrapText(true);
        titleBox.getChildren().add(title);

        Label dots = new Label("⋮");
        dots.setFont(Font.font("System", FontWeight.BOLD, 14));
        dots.setStyle("-fx-text-fill: #4E6766;");
        header.getChildren().addAll(icon, titleBox, dots);

        Separator separator = new Separator();
        separator.setOpacity(0.3);

        // --- Grid (Jumlah Porsi) ---
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(4);
        Label lblPorsiTitle = new Label("Jumlah Porsi");
        lblPorsiTitle.setFont(Font.font("System", 10));
        lblPorsiTitle.setStyle("-fx-text-fill: #4E6766;");

        Label lblPorsiValue = new Label(String.valueOf(jadwal.getJumlahPorsi()));
        lblPorsiValue.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblPorsiValue.setStyle("-fx-text-fill: #1E152A;");

        grid.add(lblPorsiTitle, 0, 0);
        grid.add(lblPorsiValue, 0, 1);

        // --- Menu Hari Ini ---
        VBox menuBox = new VBox(2);
        Label lblMenuTitle = new Label("Menu Hari Ini");
        lblMenuTitle.setFont(Font.font("System", 10));
        lblMenuTitle.setStyle("-fx-text-fill: #4E6766;");

        Label lblMenuValue = new Label(jadwal.getMenu());
        lblMenuValue.setFont(Font.font("System", 11));
        lblMenuValue.setStyle("-fx-text-fill: #1E152A;");
        lblMenuValue.setWrapText(true);
        menuBox.getChildren().addAll(lblMenuTitle, lblMenuValue);

        // --- ComboBox Status ---
        VBox statusBox = new VBox(4);
        Label lblStatusTitle = new Label("Update Status");
        lblStatusTitle.setFont(Font.font("System", 10));
        lblStatusTitle.setStyle("-fx-text-fill: #4E6766;");

        ComboBox<String> comboStatus = new ComboBox<>(statusOptions);
        comboStatus.setMaxWidth(Double.MAX_VALUE);
        comboStatus.setStyle("-fx-background-color: white; -fx-background-radius: 6;");

        // Set nilai awal dari DB
        if (jadwal.getStatus() != null) {
            comboStatus.setValue(jadwal.getStatus());
        }

        // Listener saat status diganti oleh user
        comboStatus.setOnAction(e -> {
            shipmentRepo.updateStatus(jadwal.getId(), comboStatus.getValue());
            System.out.println("Status diperbarui untuk ID: " + jadwal.getId() + " menjadi " + comboStatus.getValue());
        });

        statusBox.getChildren().addAll(lblStatusTitle, comboStatus);

        // Gabungkan semua komponen ke dalam Card
        card.getChildren().addAll(header, separator, grid, menuBox, statusBox);
        return card;
    }

    // -------------------------------------------------------
    // Tombol "Assign New Route" — buka dialog tambah jadwal
    // -------------------------------------------------------
    @FXML
    private void handleAssignNewRoute(MouseEvent event) {
        bukaTambahJadwalDialog();
    }

    private void bukaTambahJadwalDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/mycompany/embg/app/fxml/vendor/TambahJadwalDialog.fxml"
            ));
            Parent root = loader.load();

            // Ambil controller dan set callback refresh
            TambahJadwalController controller = loader.getController();
            controller.setOnSimpanCallback(() -> {
                System.out.println("Jadwal baru tersimpan, refresh data dari DB...");
                muatDataShipment(); // <-- Panggil ulang query untuk me-refresh UI
            });

            // Buat Stage dialog (modal)
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Tambah Jadwal Pengiriman");

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            dialog.setScene(scene);
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal membuka dialog: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Navigasi sidebar
    // -------------------------------------------------------
    @FXML
    private void handleBukaDashboard(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/VendorDashboard.fxml");
    }

    @FXML
    private void handleBukaVendors(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/MenuManagement.fxml");
    }

    @FXML
    private void handleBukaInventory(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml");
    }
}
