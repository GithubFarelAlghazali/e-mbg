package com.mycompany.embg.app.services;

public class UserSession {
    // Variabel statis untuk menyimpan ID user yang sedang login
    private static String currentUserId;

    public static void setCurrentUserId(String id) {
        currentUserId = id;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }
    
    // Opsional: Bisa dipanggil saat tombol Logout diklik
    public static void clearSession() {
        currentUserId = null;
    }
}