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
    private boolean approval;
    
    public Vendor(
    String id,
    String username,
    String email,
    String password,
    String alamat,
    boolean approve){
        super(id, username, email, password, "vendor");
        this.alamat = alamat;
        this.approval = approve;
    }
    
    public String getAlamat(){
        return this.alamat;
    }
    
    public boolean isApproved(){
        return this.approval;
    }
    
    public void setApproval(boolean approve){
        this.approval =  approve;
    }
    
    public void tambahMenu(){
        
    }
    
    public void updateStatusPengiriman(String status){
        
    }
}
