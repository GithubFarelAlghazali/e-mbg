/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class Produk {
    private String namaProduk;
    private int harga;
    
    public Produk(String namaProduk, int harga){
        this.namaProduk = namaProduk;
        this.harga = harga;
    }
    
    public String getNama(){
        return this.namaProduk;
    }
    
    public int getHarga(){
        return this.harga;
    }
    
    public void setNama(String namaProduk){
        this.namaProduk = namaProduk;
    }
    
    public void setHarga(int harga){
        this.harga = harga;
    }
}
