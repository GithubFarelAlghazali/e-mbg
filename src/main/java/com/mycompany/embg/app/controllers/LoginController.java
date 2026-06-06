package com.mycompany.embg.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class untuk halaman Login MBG System
 */
public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckbox;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink registerLink;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Logika inisialisasi (jika ada, seperti mengambil remembered email)
    }    

    @FXML
    private void handleSignIn(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        boolean isRememberMe = rememberMeCheckbox.isSelected();
        
        System.out.println("Mencoba login dengan Email: " + email);
        // Tambahkan logika autentikasi atau panggil service di sini
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        System.out.println("Navigasi ke halaman lupa password...");
        // Logika perpindahan scene / pop-up lupa password
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        System.out.println("Navigasi ke halaman pendaftaran (Register)...");
        // Logika perpindahan scene ke RegisterPage.fxml
    }
}