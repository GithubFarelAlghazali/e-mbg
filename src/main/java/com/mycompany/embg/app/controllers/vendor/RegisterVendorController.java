/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.embg.app.controllers.vendor;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.mycompany.embg.app.models.Vendor;
import com.mycompany.embg.app.repository.UserRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert.AlertType;
import com.mycompany.embg.app.services.AlertPopup;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RegisterVendorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField alamatField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void handleRegister(ActionEvent event){
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String alamat = alamatField.getText().trim();
        
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || alamat.isEmpty()){
            AlertPopup.showAlert(AlertType.WARNING, "Semua input harus diisi lengkap!");
            return;
        }
        
        try{
            UserRepo repo = new UserRepo();
            
            if(repo.isEmailExist(email)){
                AlertPopup.showAlert(AlertType.WARNING, "Email sudah terdaftar!");
                return;
            }
            
            if(repo.isUsernameExist(username)){
                AlertPopup.showAlert(AlertType.WARNING, "Username sudah terdaftar!");
                return;
            }
            
            Vendor vendor = new Vendor(null, username, email, password, alamat, false);
            repo.registerVendor(vendor);
            
            AlertPopup.showAlert(AlertType.INFORMATION, "Registrasi berhasil! Silakan login dengan akun anda");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/embg/app/fxml/auth/LoginPage.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            
        } catch(SQLException e){
            AlertPopup.showAlert(AlertType.ERROR, "Gagal terhubung ke database: " + e.getMessage());
        } catch (IOException e){
            AlertPopup.showAlert(AlertType.ERROR, "Gagal membuka halaman login: " + e.getMessage());
        }

    }
    
}
