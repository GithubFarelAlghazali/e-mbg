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
public class Sekolah extends User {
    private String npsn;
    private String alamat;
    private int jumlahSiswa;
    private ArrayList<SiswaKebutuhanKhusus> siswaKhusus;
    
    public Sekolah(
    String id,
    String username,
    String email,
    String password,
    String npsn,
    String alamat){
        super(id, username, email, password, "sekolah");
        this.npsn = npsn;
        this.alamat = alamat;
//        this.jumlahSiswa = jumlahSiswa;
        this.siswaKhusus = new ArrayList<>();
    }
    
    public String getNpsn(){
        return this.npsn;
    }
    
    public String getAlamat(){
        return this.alamat;
    }
    
    public void setJumlahSiswa(int jumlah){
        this.jumlahSiswa = jumlah;
    }
    
    public void tambahSiswaKebutuhanKhusus(SiswaKebutuhanKhusus siswa){
       this.siswaKhusus.add(siswa);
    }
    
    public int getJumlahSiswa(){
        return this.jumlahSiswa;
    }
    
    public boolean konfirmasiPenerimaan(){
        return true;
    }
}