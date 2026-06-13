/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.services;

import java.io.IOException;
import java.net.URL;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.mycompany.embg.app.services.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 *
 * @author User
 */
public class Redirect {
    public static void redirectPage(Event event, String fxmlPath) {
        try {
            URL fxmlLocation = Redirect.class.getResource(fxmlPath);
            if (fxmlLocation == null) {
                Alert.showAlert(AlertType.ERROR, "File fxml tidak ditemukan pada path : "+fxmlPath);              
                return;
            }
            
            Parent root = FXMLLoader.load(fxmlLocation);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            Alert.showAlert(AlertType.ERROR, "Gagal memuat halaman : " + fxmlPath + " error: " + e);
            e.printStackTrace();
        }
    }
}
