/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;
import java.util.*;

/**
 *
 * @author User
 */
public class Produksi {
    private MenuMakanan menu;
    private ArrayList<BahanMakanan> bahan;
    private InventarisDapur inventaris;
    private JadwalPengiriman jadwal;
    
    public void belanjaBahan(BahanMakanan bahan){
        this.bahan.add(bahan);
    }
    
    public void masakMakanan(){
        
    }
    
    public void updateStatusMasak(){
        
    }
    
    public void kirimDistribusi(){
        
    }
    
}
