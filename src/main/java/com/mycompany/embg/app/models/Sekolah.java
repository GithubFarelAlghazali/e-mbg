/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class Sekolah extends User {
    private String npsn;
    private String alamat;
    
    public Sekolah(
    String id,
    String username,
    String email,
    String password,
    String npsn,
    String alamat){
        super(id, username, email, password);
        this.npsn = npsn;
        this.alamat = alamat;
    }
    
}
