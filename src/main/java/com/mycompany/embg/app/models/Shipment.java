package com.mycompany.embg.app.models;

public class Shipment {

    private String id;
    private String namaSekolah;
    private String menu;
    private int jumlahPorsi;
    private String status;

    public Shipment(
            String id,
            String namaSekolah,
            String menu,
            int jumlahPorsi,
            String status
    ) {
        this.id = id;
        this.namaSekolah = namaSekolah;
        this.menu = menu;
        this.jumlahPorsi = jumlahPorsi;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public String getMenu() {
        return menu;
    }

    public int getJumlahPorsi() {
        return jumlahPorsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}