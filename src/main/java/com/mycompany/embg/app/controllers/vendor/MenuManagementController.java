package com.mycompany.embg.app.controllers.vendor;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.services.Redirect;
import com.mycompany.embg.app.services.UserSession;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuManagementController implements Initializable {

    @FXML
    private Button btnTambahMenu;
    @FXML
    private Button btnFilter;
    @FXML
    private TextField txtSearchMenu;
    @FXML
    private Button btnNewReport;

    @FXML
    private TableView<MenuHarian> tblMenu;
    @FXML
    private TableColumn<MenuHarian, String> colTanggal;   // akan tampilkan harga
    @FXML
    private TableColumn<MenuHarian, String> colNamaMenu;
    @FXML
    private TableColumn<MenuHarian, String> colKalori;
    @FXML
    private TableColumn<MenuHarian, String> colActions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // colTanggal dipakai untuk Harga (sesuaikan label di FXML jika bisa)
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colKalori.setCellValueFactory(new PropertyValueFactory<>("kalori"));
        colActions.setCellValueFactory(new PropertyValueFactory<>("actions"));

        // Kolom nama menu dengan subtitle (nama + detail/gizi)
        colNamaMenu.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    MenuHarian data = getTableView().getItems().get(getIndex());

                    Label lblJudul = new Label(data.getNamaMenu());
                    lblJudul.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #1E152A;");

                    Label lblSubtitle = new Label(data.getDetailMenu());
                    lblSubtitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #4E6766;");

                    VBox box = new VBox(2, lblJudul, lblSubtitle);
                    setGraphic(box);
                }
            }
        });

        // Load data real dari Supabase
        loadMenuDariDB();
    }

    // -------------------------------------------------------
    // Query menu JOIN nilai_gizi WHERE vendor_id = user_login
    // -------------------------------------------------------
    private void loadMenuDariDB() {
        ObservableList<MenuHarian> data = FXCollections.observableArrayList();

        // 1. Tambahkan "p." sebelum vendor_id agar jelas
        String sql = """
                SELECT p.nama_menu, p.budget,
                       n.kalori, n.protein, n.lemak, n.karbohidrat
                FROM menu p
                LEFT JOIN nilai_gizi n ON p.nilai_gizi_id = n.id
                WHERE p.vendor_id = ?::uuid
                ORDER BY p.nama_menu
                """;

        // 2. Deklarasi rs dipisah, agar bisa set parameter
        try (Connection conn = DbConfig.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // 3. Set parameter id vendor yang sedang login
            ps.setString(1, UserSession.getCurrentUserId());

            // 4. Baru eksekusi query-nya
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nama = rs.getString("nama_menu");
                    int harga = rs.getInt("budget");
                    float kalori = rs.getFloat("kalori");
                    float protein = rs.getFloat("protein");
                    float lemak = rs.getFloat("lemak");
                    float karbo = rs.getFloat("karbohidrat");

                    // Susun detail gizi sebagai subtitle
                    String detailGizi = String.format(
                            "Protein %.0fg  •  Lemak %.0fg  •  Karbo %.0fg",
                            protein, lemak, karbo
                    );

                    // colTanggal → tampilkan harga
                    String hargaStr = "Rp " + String.format("%,d", harga).replace(',', '.');

                    // colKalori → tampilkan kalori
                    String kaloriStr = String.format("%.0f kcal", kalori);

                    data.add(new MenuHarian(hargaStr, nama, detailGizi, kaloriStr));
                }

                tblMenu.setItems(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Gagal memuat menu: " + e.getMessage());
        }
    }
    // -------------------------------------------------------
    // Model POJO (struktur tetap sama agar FXML tidak perlu diubah)
    // -------------------------------------------------------
    public static class MenuHarian {

        private final String tanggal;    // dipakai untuk harga
        private final String namaMenu;
        private final String detailMenu; // subtitle gizi
        private final String kalori;
        private final String actions;

        public MenuHarian(String tanggal, String namaMenu, String detailMenu, String kalori) {
            this.tanggal = tanggal;
            this.namaMenu = namaMenu;
            this.detailMenu = detailMenu;
            this.kalori = kalori;
            this.actions = "";
        }

        public String getTanggal() {
            return tanggal;
        }

        public String getNamaMenu() {
            return namaMenu;
        }

        public String getDetailMenu() {
            return detailMenu;
        }

        public String getKalori() {
            return kalori;
        }

        public String getActions() {
            return actions;
        }
    }

    // -------------------------------------------------------
    // Tambah Menu → buka dialog → refresh otomatis setelah simpan
    // -------------------------------------------------------
    @FXML
    private void handleTambahMenu(ActionEvent event) {
        bukaDialogTambahMenu("menu");
    }

    private void bukaDialogTambahMenu(String tipeProduk) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/mycompany/embg/app/fxml/vendor/TambahMenuDialog.fxml"
            ));
            Parent root = loader.load();

            TambahMenuController controller = loader.getController();
            controller.setTipeProduk(tipeProduk);
            controller.setOnSimpanCallback(() -> {
                // Refresh tabel setelah dialog ditutup (di JavaFX thread)
                Platform.runLater(this::loadMenuDariDB);
            });

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Tambah Menu");

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
    private void handleBukaShipments(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/ShipmentManagement.fxml");
    }


    @FXML
    private void handleLogout(ActionEvent event) {
        com.mycompany.embg.app.services.UserSession.clearSession();
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/auth/LoginPage.fxml");
    }
    
    @FXML
    private void handleBukaInventory(ActionEvent event) {
        Redirect.redirectPage(event,
                "/com/mycompany/embg/app/fxml/vendor/InventoryManagement.fxml");
    }
}
    

