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
    private String id;
    private String vendorId;
    
    public Produk(String namaProduk, int harga, String id, String vendorId){
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.id = id;
        this.vendorId = vendorId;
    }
    
    public String getNama(){
        return this.namaProduk;
    }
    
    public int getHarga(){
        return this.harga;
    }
  
    public String getId(){
        return this.id;
    }
    
    public String getVendorId(){
        return this.vendorId;
    }
    
    public void setNama(String namaProduk){
        this.namaProduk = namaProduk;
    }
    
    public void setHarga(int harga){
        this.harga = harga;
    }
}
