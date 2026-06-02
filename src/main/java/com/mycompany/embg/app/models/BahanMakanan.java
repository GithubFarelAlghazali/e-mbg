/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class BahanMakanan extends Produk {
    private int jumlah;
    private String satuan;
    private NilaiGizi infoGizi;
    
    public BahanMakanan(
            String namaProduk,
            int harga,
            int jumlah,
            String satuan,
            NilaiGizi gizi){
        super(namaProduk, harga);
        this.jumlah = jumlah;
        this.satuan = satuan;
        this.infoGizi = gizi;
    }
    
    public void setJumlah(int jumlah){
        this.jumlah = jumlah;
    }
    
    public int getJumlah(){
        return this.jumlah;
    }
    
    public String getSatuan(){
        return this.satuan;
    }
}
