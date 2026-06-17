package com.mycompany.embg.app.models;

public class InventoryItem {

    private String id;
    private String namaBarang;
    private int stok;
    private String satuan;
    private int harga;

    public InventoryItem(
            String id,
            String namaBarang,
            int stok,
            String satuan,
            int harga
    ) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.stok = stok;
        this.satuan = satuan;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}