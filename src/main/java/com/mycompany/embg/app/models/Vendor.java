/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class Vendor extends User {
    private String alamat;
    private InventarisDapur inventaris;
    
    public Vendor(
    String id,
    String username,
    String email,
    String password,
    String alamat){
        super(id, username, email, password);
        this.alamat = alamat;
    }
    
    public void tambahMenu(){
        
    }
    
    public void updateStatusPengiriman(String status){
        
    }
}
