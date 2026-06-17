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
    private String status;

    public JadwalPengiriman(
            String id,
            String vendorId,
            String sekolahId,
            String menu,
            int jumlahPorsi,
            String status
    ) {
        this.id = id;
        this.vendorId = vendorId;
        this.sekolahId = sekolahId;
        this.menu = menu;
        this.jumlahPorsi = jumlahPorsi;
        this.status = status;
    }

    public String getId() { return id; }
    public String getVendorId() { return vendorId; }
    public String getSekolahId() { return sekolahId; }
    public String getMenu() { return menu; }
    public int getJumlahPorsi() { return jumlahPorsi; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}