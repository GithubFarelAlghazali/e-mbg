package com.mycompany.embg.app.models;

public class JadwalPengiriman {

    private String id;
    private String namaVendor;   // ← ganti vendorId
    private String namaSekolah;  // ← ganti sekolahId
    private String namaMenu;     // ← ganti menu
    private int jumlahPorsi;
    private String tanggal;
    private String status;

    public JadwalPengiriman(
            String id,
            String namaVendor,
            String namaSekolah,
            String namaMenu,
            int jumlahPorsi,
            String tanggal,
            String status
    ) {
        this.id = id;
        this.namaVendor = namaVendor;
        this.namaSekolah = namaSekolah;
        this.namaMenu = namaMenu;
        this.jumlahPorsi = jumlahPorsi;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getNamaVendor() {
        return namaVendor;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public int getJumlahPorsi() {
        return jumlahPorsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMenu(String menu) {
        this.namaMenu = menu;
    }

    public void setSekolah(String sekolah) {
        this.namaSekolah = sekolah;
    }

    public void setPorsi(int porsi) {
        this.jumlahPorsi = porsi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
