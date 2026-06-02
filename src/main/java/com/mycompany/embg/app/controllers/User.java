/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.embg.app.controllers;

/**
 *
 * @author User
 */
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    
    public User(
    String id,
    String username,
    String email,
    String password){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
