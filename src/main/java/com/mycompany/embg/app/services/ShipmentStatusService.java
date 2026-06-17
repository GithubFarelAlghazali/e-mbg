/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.services;

/**
 *
 * @author HP
 */
public class ShipmentStatusService {

    public static boolean canVendorUpdate(
            String status
    ) {

        return status.equals("DIMASAK")
                || status.equals("DIKIRIM");
    }

    public static boolean canSchoolUpdate(
            String status
    ) {

        return status.equals("DITERIMA");
    }
}