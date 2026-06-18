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

    private String id;
    private String vendorId;
    private String sekolahId;
    private String menu;
    private int jumlahPorsi;
    private String tanggal;
    private String status;

    public JadwalPengiriman(
            String id,
            String vendorId,
            String sekolahId,
            String menu,
            int jumlahPorsi,
            String status,
            String tanggal
    ) {
        this.id = id;
        this.vendorId = vendorId;
        this.sekolahId = sekolahId;
        this.menu = menu;
        this.jumlahPorsi = jumlahPorsi;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getId() { return id; }
    public String getVendorId() { return this.vendorId; }
    public String getSekolahId() { return this.sekolahId; }
    public String getMenu() { return this.menu; }
    public int getJumlahPorsi() { return this.jumlahPorsi; }
    public String getStatus() { return this.status; }
    public String getTanggal() { return this.tanggal; }

    public void setStatus(String status) {
        this.status = status;
    }
}