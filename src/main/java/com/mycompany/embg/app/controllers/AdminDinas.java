/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.controllers;

/**
 *
 * @author User
 */
public class AdminDinas extends User {
    private String nip;
    private String wilayah;
    
    public AdminDinas(
    String id,
    String username,
    String email,
    String password,
    String nip,
    String wilayah){
        super(id, username, email,password);
        this.nip = nip;
        this.wilayah = wilayah;
    }
    
    public boolean verifikasiVendor(){
        return true;
    }
    
    public void pantauDistribusi(){
        System.out.println("PANTAU");
    }
}
