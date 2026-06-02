/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class MenuMakanan extends Produk {
    private NilaiGizi infoGizi;
    private BahanMakanan[] daftarBahan;
    
    public MenuMakanan(
        String namaMenu,
        int harga,
        BahanMakanan[] daftarBahan){
        super(namaMenu, harga);
    }
    
    public String getNamaMenu(){
        return super.getNama();
    }
}
