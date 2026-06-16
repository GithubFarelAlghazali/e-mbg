/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class SiswaKebutuhanKhusus {
    private String id;
    private String alergi;
    private String nama;
    private String idSekolah;
    private String kelas;
    
    public SiswaKebutuhanKhusus(String id,String alergi, String nama, String idSekolah, String kelas){
        this.id = id;
        this.alergi = alergi;
        this.nama = nama;
        this.kelas = kelas;
        this.idSekolah = idSekolah;
    }
    
    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public String getIdSekolah() {
        return this.idSekolah;
    }

    public String getKelas() {
        return this.kelas;
    }

    public String getAlergi() {
        return this.alergi;
    }
}
