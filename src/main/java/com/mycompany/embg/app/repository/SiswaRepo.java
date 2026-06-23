package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.models.Sekolah;
import com.mycompany.embg.app.models.SiswaKebutuhanKhusus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaRepo {

    private Connection conn;
    
    public SiswaRepo() throws SQLException {
        this.conn = DbConfig.getConnection();
    }

    // mengambil semua sekolah terdaftar
    public List<Sekolah> getSekolah() throws SQLException {
        List<Sekolah> listSekolah = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'sekolah'";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listSekolah.add(new Sekolah(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("npsn"),
                        rs.getString("alamat")
                ));
            }
        }
        return listSekolah;

    }
    
    // Mengambil data siswa khusus berdasarkan ID Sekolah
    public List<SiswaKebutuhanKhusus> getSiswaKhususBySekolah(String idSekolah) throws SQLException {
        List<SiswaKebutuhanKhusus> listSiswa = new ArrayList<>();
        String sql = "SELECT * FROM siswa_khusus WHERE id_sekolah = ?::uuid";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idSekolah);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listSiswa.add(new SiswaKebutuhanKhusus(
                        rs.getString("id"),
                        rs.getString("alergi"),
                        rs.getString("nama"),
                        rs.getString("id_sekolah"),
                        rs.getString("kelas")
                ));
            }
        }
        return listSiswa;
    }

    // Menyimpan data siswa alergi baru
    public void tambahSiswaKhusus(SiswaKebutuhanKhusus siswa) throws SQLException {
        String sql = "INSERT INTO siswa_khusus (alergi, nama, id_sekolah, kelas) VALUES (?, ?, ?::uuid, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, siswa.getAlergi());
            stmt.setString(2, siswa.getNama());
            stmt.setString(3, siswa.getIdSekolah());
            stmt.setString(4, siswa.getKelas());
            stmt.executeUpdate();
        }
    }

    // Memperbarui Total Siswa (Porsi Harian) untuk Sekolah
    public void updateTotalPorsi(String idSekolah, int totalPorsi) throws SQLException {
        String sql = "UPDATE users SET jumlah_siswa = ? WHERE id = ?::uuid AND role = 'sekolah'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, totalPorsi);
            stmt.setString(2, idSekolah);
            stmt.executeUpdate();
        }
    }

//    /**
//     * [BARU] Mengambil jumlah_siswa dari tabel users untuk sekolah tertentu.
//     * Mengembalikan 0 jika kolom null (belum diisi).
//     */
    public int getJumlahSiswa(String idSekolah) throws SQLException {
        String sql = "SELECT jumlah_siswa FROM users WHERE id = ?::uuid AND role = 'sekolah'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idSekolah);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // getInt mengembalikan 0 jika nilai kolom NULL
                return rs.getInt("jumlah_siswa");
            }
        }
        return 0;
    }

//    /**
//     * [BARU] Menghitung total baris siswa_khusus milik sekolah ini. Dipakai
//     * untuk kartu "Siswa Kebutuhan Khusus" di dasbor.
//     */
    public int getJumlahSiswaKhusus(String idSekolah) throws SQLException {
        String sql = "SELECT COUNT(*) FROM siswa_khusus WHERE id_sekolah = ?::uuid";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idSekolah);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
