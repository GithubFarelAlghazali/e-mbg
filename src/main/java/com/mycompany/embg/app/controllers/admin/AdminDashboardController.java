package com.mycompany.embg.app.controllers.admin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        loadPage("/com/mycompany/embg/app/fxml/admin/Dashboard.fxml");
    }

    @FXML
    public void showDashboard() {
        loadPage("/com/mycompany/embg/app/fxml/admin/Dashboard.fxml");
    }

    @FXML
    public void showVendors() {
        loadPage("/com/mycompany/embg/app/fxml/admin/Vendor.fxml");
    }

    @FXML
    public void showSchools() {
        loadPage("/com/mycompany/embg/app/fxml/admin/School.fxml");
    }

    @FXML
    public void showShipments() {
        loadPage("/com/mycompany/embg/app/fxml/admin/Shipment.fxml");
    }

    @FXML
    public void showInventory() {
        loadPage("/com/mycompany/embg/app/fxml/admin/Inventory.fxml");
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            com.mycompany.embg.app.services.UserSession.clearSession();
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/embg/app/fxml/auth/LoginPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPage(String path) {
        try {
            Node page = FXMLLoader.load(getClass().getResource(path));
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}