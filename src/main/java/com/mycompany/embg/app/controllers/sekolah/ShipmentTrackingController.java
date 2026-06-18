/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.controllers.sekolah;

/**
 *
 * @author HP
 */
import com.mycompany.embg.app.repository.ShipmentRepo;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class ShipmentTrackingController {

    private ShipmentRepo repo;
    
    @FXML
    public void initialize(){
        
    try{
    
            repo = new ShipmentRepo();
    } catch (SQLException e){
        e.printStackTrace();
    }
    }

    public void terimaMakanan(
            String shipmentId
    ) {

        repo.updateStatus(
                shipmentId,
                "DITERIMA"
        );
    }
}