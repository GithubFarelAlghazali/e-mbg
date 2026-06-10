/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.embg.app.controllers.auth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import com.mycompany.embg.app.services.Alert;
import javafx.scene.control.Alert.AlertType;
import com.mycompany.embg.app.repository.UserRepo;
import com.mycompany.embg.app.models.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleClickRegister(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mycompany/embg/app/fxml/auth/SelectRole.fxml")
        );
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        
    }
    
    @FXML
    private void handleLogin(ActionEvent event){
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        
        if(email.isEmpty() || password.isEmpty()){
            Alert.showAlert(AlertType.WARNING, "Email & Password harus diisi!" );
            return;
        }
        
        try{
            UserRepo repo = new UserRepo();
            User user = repo.findByEmail(email);
            
            if(user == null){
                Alert.showAlert(AlertType.ERROR, "Email tidak terdaftar!");
                return;
            }
            
            if(!repo.checkPassword(password, user.getPassword())){
                Alert.showAlert(AlertType.ERROR, "Password salah!");
                return;
            }
            
            String fxmlPath;
            
            switch(user.getRole()){
                case "admin":
                    fxmlPath = "/com/mycompany/embg/app/fxml/admin/AdminDashboard.fxml";
                    break;
                case "sekolah":
                    fxmlPath = "/com/mycompany/embg/app/fxml/admin/AdminDashboard.fxml";
                    break;
                case "vendor":
                    fxmlPath = "/com/mycompany/embg/app/fxml/admin/AdminDashboard.fxml";
                    break;
                default:
                    Alert.showAlert(AlertType.ERROR, "Role tidak dikenali!");
                    return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            
        } catch(SQLException e){
            Alert.showAlert(AlertType.ERROR, "Gagal terhubung ke database: " + e);
        } catch (IOException e){
            Alert.showAlert(AlertType.ERROR, "Gagal membuka halaman dashbaord: " + e);
        }
        
    }
    
    
}
