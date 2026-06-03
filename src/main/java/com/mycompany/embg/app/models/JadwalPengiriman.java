/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class JadwalPengiriman {
    private MenuMakanan menu;
    private Sekolah tujuan;
    private String statusDistribusi;
    
    public JadwalPengiriman(MenuMakanan menu, Sekolah tujuan){
        this.menu = menu;
        this.tujuan = tujuan;
    }
    
    public void updateStatus(String status){
        
    }
}
