package com.mycompany.embg.app.controllers.vendor;

import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.embg.app.models.BahanMakanan;
import com.mycompany.embg.app.repository.InventoryRepo;
import com.mycompany.embg.app.services.AlertPopup;
import com.mycompany.embg.app.services.Redirect;
import com.mycompany.embg.app.services.UserSession;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InventoryManagementController implements Initializable {

    @FXML
    private TextField txtSearchGlobal;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnNewReport;

    @FXML
    private TableView<BahanMakanan> tblInventory;

    @FXML
    private TableColumn<BahanMakanan, String> colNamaBarang;
    

    @FXML
    private TableColumn<BahanMakanan, Integer> colStok;

    @FXML
    private TableColumn<BahanMakanan, String> colSatuan;

    @FXML
    private TableColumn<BahanMakanan, Integer> colHarga;

    @FXML
    private TableColumn<BahanMakanan, Void> colActions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colNamaBarang.setCellValueFactory(
                new PropertyValueFactory<>("nama")
        );

        colStok.setCellValueFactory(
                new PropertyValueFactory<>("jumlah")
        );

        colSatuan.setCellValueFactory(
                new PropertyValueFactory<>("satuan")
        );
        
        colHarga.setCellValueFactory(
        new PropertyValueFactory<>("harga")
        );

        loadInventory();
        addActionButtons();
    }

    private void loadInventory() {

        InventoryRepo repo;
        ObservableList<BahanMakanan> data;
        
        try{
            repo = new InventoryRepo();
                    data = FXCollections.observableArrayList(
                            repo.getAllItems(UserSession.getCurrentUserId())
            );
        tblInventory.setItems(data);
        } catch(SQLException err){
            AlertPopup.showAlert(Alert.AlertType.ERROR, "Gagal memuat inventaris : " + err);
        }

        

    }

    private void addActionButtons() {

        colActions.setCellFactory(param -> new TableCell<>() {

            private final Button btnEdit = new Button("Edit");
            private final Button btnDelete = new Button("Delete");

            private final HBox pane =
                    new HBox(5, btnEdit, btnDelete);

            {

                btnDelete.setOnAction(event -> {

                    BahanMakanan item =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    InventoryRepo repo;
                    try{
                        
                        repo = new InventoryRepo();
                        repo.deleteItem(item.getId());
                    } catch(SQLException err){
                        AlertPopup.showAlert(Alert.AlertType.ERROR, "Gagal mengedit data inventaris : " + err);
                    }


                    loadInventory();
                });

                btnEdit.setOnAction(event -> {

                    BahanMakanan item =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    System.out.println(
                            "Edit Item: "
                                    + item.getNama()
                    );

                    // nanti buka EditInventory.fxml
                });
            }

            @Override
            protected void updateItem(Void item,
                                      boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    @FXML
    private void handleAddItem(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/com/mycompany/embg/app/fxml/vendor/AddInventory.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.setTitle(
                    "Tambah Inventaris"
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.showAndWait();

            loadInventory();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @FXML
    private void handleBukaDashboard(ActionEvent event) {

        Redirect.redirectPage(
                event,
                "/com/mycompany/embg/app/fxml/vendor/VendorDashboard.fxml"
        );
    }

    @FXML
    private void handleBukaVendors(ActionEvent event) {

        Redirect.redirectPage(
                event,
                "/com/mycompany/embg/app/fxml/vendor/MenuManagement.fxml"
        );
    }

    @FXML
    private void handleBukaShipments(ActionEvent event) {

        Redirect.redirectPage(
                event,
                "/com/mycompany/embg/app/fxml/vendor/ShipmentManagement.fxml"
        );
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        com.mycompany.embg.app.services.UserSession.clearSession();
        Redirect.redirectPage(event, "/com/mycompany/embg/app/fxml/auth/LoginPage.fxml");
    }
}