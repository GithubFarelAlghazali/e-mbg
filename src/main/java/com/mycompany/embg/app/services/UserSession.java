package com.mycompany.embg.app.services;

public class UserSession {
    // Variabel statis untuk menyimpan informasi user yang sedang login
    private static String currentUserId;
    private static String currentUsername;

    // Getter dan Setter untuk User ID
    public static void setCurrentUserId(String id) {
        currentUserId = id;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    // --- TAMBAHKAN INI UNTUK MEMPERBAIKI ERROR ---
    // Getter dan Setter untuk Username
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }
    // ---------------------------------------------
    
    // Dipanggil saat Logout
    public static void clearSession() {
        currentUserId = null;
        currentUsername = null;
    }
}