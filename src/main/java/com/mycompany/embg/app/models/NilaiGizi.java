/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.models;

/**
 *
 * @author User
 */
public class NilaiGizi {
    private double kalori;
    private double protein;
    private double karbohidrat;
    private double lemak;
    
    public NilaiGizi(
    double kalori,
    double protein,
    double karbohidrat,
    double lemak){
        this.kalori = kalori;
        this.protein = protein;
        this.karbohidrat = karbohidrat;
        this.lemak = lemak;
    }
    
    public String getTotalGizi(){
        return "Kalori : " + this.kalori + "kkal, Protein : " + this.protein + "g, Karbohidrat : " + this.karbohidrat + "g, Lemak : " + this.lemak + "g";
    }
}
