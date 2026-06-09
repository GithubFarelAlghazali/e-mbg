/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.embg.app.controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.mycompany.embg.app.models.AdminDinas;
import com.mycompany.embg.app.repository.UserRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
/**
 * FXML Controller class
 *
 * @author User
 */
public class RegisterAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nipField;
    @FXML private TextField wilayahField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleRegsiter(ActionEvent event){
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String nip = nipField.getText().trim();
        String wilayah = wilayahField.getText().trim();
        
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || nip.isEmpty() || wilayah.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Semua input harus diisi lengkap!");
            return;
        }
        
        try{
            UserRepo repo = new UserRepo();
            
            if(repo.isEmailExist(email)){
                showAlert(Alert.AlertType.WARNING, "Email sudah terdaftar!");
                return;
            }
            
            if(repo.isUsernameExist(username)){
                showAlert(Alert.AlertType.WARNING, "Username sudah terdaftar!");
                return;
            }
            
            AdminDinas admin = new AdminDinas(null, username, email, password, nip, wilayah);
            repo.registerAdmin(admin);
            
            showAlert(Alert.AlertType.INFORMATION, "Registrasi berhasil! Silakan login dengan akun anda");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/embg/app/fxml/auth/LoginPage.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            
        } catch(SQLException e){
            showAlert(Alert.AlertType.ERROR, "Gagal terhubung ke database: " + e.getMessage());
        } catch (IOException e){
            showAlert(Alert.AlertType.ERROR, "Gagal membuka halaman login: " + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType type, String message){
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
