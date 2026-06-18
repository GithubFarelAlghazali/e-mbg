package com.mycompany.embg.app.models;

/**
 * Model untuk menampilkan data jadwal pengiriman di UI (gabungan tabel jadwal + users).
 */
public class JadwalItem {
    private String id;
    private String namaSekolah;   // dari JOIN users WHERE role='sekolah'
    private String namaVendor;    // dari JOIN users WHERE role='vendor'
    private String namaMenu;      // dari JOIN menu/products
    private int jumlahPorsi;
    private String tanggal;
    private String status;

    // ─────────────────────────────────────────────
    // CONSTRUCTOR UTAMA (Sudah Ada)
    // ─────────────────────────────────────────────
    public JadwalItem(String id, String namaSekolah, String namaVendor,
                      String namaMenu, int jumlahPorsi, String tanggal, String status) {
        this.id = id;
        this.namaSekolah = namaSekolah;
        this.namaVendor = namaVendor;
        this.namaMenu = namaMenu;
        this.jumlahPorsi = jumlahPorsi;
        this.tanggal = tanggal;
        this.status = status;
    }

    // ─────────────────────────────────────────────
    // CONSTRUCTOR KOSONG (Tambahan untuk instansiasi fleksibel)
    // ─────────────────────────────────────────────
    public JadwalItem() {
    }

    // ─────────────────────────────────────────────
    // GETTER (Sudah Ada)
    // ─────────────────────────────────────────────
    public String getId()          { return id; }
    public String getNamaSekolah() { return namaSekolah; }
    public String getNamaVendor()  { return namaVendor; }
    public String getNamaMenu()    { return namaMenu; }
    public int    getJumlahPorsi() { return jumlahPorsi; }
    public String getTanggal()     { return tanggal; }
    public String getStatus()      { return status; }

    // ─────────────────────────────────────────────
    // SETTER (Tambahan agar data bisa di-set dinamis)
    // ─────────────────────────────────────────────
    public void setId(String id) { 
        this.id = id; 
    }

    public void setNamaSekolah(String namaSekolah) { 
        this.namaSekolah = namaSekolah; 
    }

    public void setNamaVendor(String namaVendor) { 
        this.namaVendor = namaVendor; 
    }

    public void setNamaMenu(String namaMenu) { 
        this.namaMenu = namaMenu; 
    }

    public void setJumlahPorsi(int jumlahPorsi) { 
        this.jumlahPorsi = jumlahPorsi; 
    }

    public void setTanggal(String tanggal) { 
        this.tanggal = tanggal; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }
}