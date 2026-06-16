package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.models.SiswaKebutuhanKhusus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaRepo {

    private Connection conn;

    public SiswaRepo() throws SQLException {
        this.conn = DbConfig.getConnection();
    }

    // Mengambil data siswa khusus berdasarkan ID Sekolah
    public List<SiswaKebutuhanKhusus> getSiswaKhususBySekolah(String idSekolah) throws SQLException {
        List<SiswaKebutuhanKhusus> listSiswa = new ArrayList<>();
        String sql = "SELECT * FROM siswa_khusus WHERE id_sekolah = ?::uuid";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idSekolah);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Sesuaikan urutan dengan constructor: id, alergi, nama, idSekolah, kelas
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
        // Karena idSekolah sudah ada di dalam model, kita ambil lewat siswa.getIdSekolah()
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
        // Catatan: Pastikan kolom di database kamu bernama 'jumlah_siswa' atau sesuaikan dengan nama kolom yang ada.
        // Saya menambahkan ::uuid karena sebelumnya kita tahu bahwa ID menggunakan tipe data UUID di PostgreSQL.
        String sql = "UPDATE users SET jumlah_siswa = ? WHERE id = ?::uuid AND role = 'sekolah'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, totalPorsi);
            stmt.setString(2, idSekolah);
            stmt.executeUpdate();
        }
    }
}
