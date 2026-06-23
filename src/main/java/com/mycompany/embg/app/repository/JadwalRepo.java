package com.mycompany.embg.app.repository;

import com.mycompany.embg.app.config.DbConfig;
import com.mycompany.embg.app.models.JadwalPengiriman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalRepo {

    // ─────────────────────────────────────────────
    // READ – ambil semua jadwal (untuk admin/dinas)
    // ─────────────────────────────────────────────
    public List<JadwalPengiriman> getAllJadwal() throws SQLException {
        List<JadwalPengiriman> list = new ArrayList<>();
        String sql = """
                SELECT
                    j.id,
                    sekolah.username  AS nama_sekolah,
                    vendor.username   AS nama_vendor,
                    p.nama_menu,     
                    j.jumlah_porsi,
                    j.tanggal::text,
                    j.status
                FROM jadwal j
                JOIN users sekolah ON sekolah.id = j.sekolah_id
                JOIN users vendor  ON vendor.id  = j.vendor_id
                JOIN menu p    ON p.id        = j.menu_id
                ORDER BY j.tanggal DESC, j.created_at DESC
                """;
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }
    
    // ─────────────────────────────────────────────
    // CREATE – Menambahkan jadwal lewat Admin / Dinas
    // ─────────────────────────────────────────────
    public boolean createJadwal(
        String vendorId,
        String sekolahId,
        String menuId,
        int jumlahPorsi,
        java.sql.Date tanggal
    ) throws SQLException {

        String sql = """
            INSERT INTO jadwal
            (
                vendor_id,
                sekolah_id,
                menu_id,
                jumlah_porsi,
                tanggal,
                status
            )
            VALUES
            (
                ?::uuid,
                ?::uuid,
                ?::uuid,
                ?,
                ?,
                'Dimasak'
            )
            """;

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, vendorId);
            stmt.setString(2, sekolahId);
            stmt.setString(3, menuId);
            stmt.setInt(4, jumlahPorsi);
            stmt.setDate(5, tanggal);

            return stmt.executeUpdate() > 0;
        }
    }

    // ─────────────────────────────────────────────
    // READ – jadwal milik vendor tertentu
    // ─────────────────────────────────────────────
    public List<JadwalPengiriman> getJadwalByVendor(String vendorId) throws SQLException {
        List<JadwalPengiriman> list = new ArrayList<>();
        String sql = """
                SELECT
                    j.id,
                    sekolah.username  AS nama_sekolah,
                    vendor.username   AS nama_vendor,
                    p.nama_menu,
                    j.jumlah_porsi,
                    j.tanggal::text,
                    j.status
                FROM jadwal j
                JOIN users sekolah ON sekolah.id = j.sekolah_id
                JOIN users vendor  ON vendor.id  = j.vendor_id
                JOIN menu p    ON p.id        = j.menu_id
                WHERE j.vendor_id = ?::uuid
                ORDER BY j.tanggal DESC
                """;
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vendorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // ─────────────────────────────────────────────
    // READ – jadwal milik sekolah tertentu
    // ─────────────────────────────────────────────
    public List<JadwalPengiriman> getJadwalBySekolah(String sekolahId) throws SQLException {
        List<JadwalPengiriman> list = new ArrayList<>();
        String sql = """
                SELECT
                    j.id,
                    sekolah.username  AS nama_sekolah,
                    vendor.username   AS nama_vendor,
                    p.nama_menu,
                    j.jumlah_porsi,
                    j.tanggal::text,
                    j.status
                FROM jadwal j
                JOIN users sekolah ON sekolah.id = j.sekolah_id
                JOIN users vendor  ON vendor.id  = j.vendor_id
                JOIN menu p    ON p.id        = j.menu_id
                WHERE j.sekolah_id = ?::uuid
                ORDER BY j.tanggal DESC
                """;
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sekolahId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // ─────────────────────────────────────────────
    // UPDATE – vendor mengubah status (dimasak/dikirim)
    // ─────────────────────────────────────────────
    public boolean updateStatusVendor(String jadwalId, String statusBaru) throws SQLException {
        String statusSaatIni = getStatusById(jadwalId);

        if (statusSaatIni == null) {
            throw new IllegalStateException("Jadwal tidak ditemukan");
        }

        if ("diterima".equalsIgnoreCase(statusSaatIni)) {
            throw new IllegalStateException("Status sudah diterima dan tidak dapat diubah.");
        }

        if ("dimasak".equalsIgnoreCase(statusSaatIni) && !"dikirim".equalsIgnoreCase(statusBaru)) {
            throw new IllegalStateException("Status dimasak hanya boleh berubah menjadi dikirim.");
        }

        String sql = "UPDATE jadwal SET status = ? WHERE id = ?::uuid";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statusBaru);
            stmt.setString(2, jadwalId);

            return stmt.executeUpdate() > 0;
        }
    }

    // ─────────────────────────────────────────────
    // UPDATE – sekolah mengkonfirmasi penerimaan
    // ─────────────────────────────────────────────
    public boolean konfirmasiPenerimaanSekolah(String jadwalId, String sekolahId) throws SQLException {
        String statusSaatIni = getStatusByIdAndSekolah(jadwalId, sekolahId);

        if (statusSaatIni == null) {
            throw new IllegalAccessError("Jadwal tidak ditemukan atau tidak milik sekolah ini.");
        }
        if (!"dikirim".equalsIgnoreCase(statusSaatIni)) {
            throw new IllegalStateException("Hanya jadwal berstatus 'dikirim' yang dapat dikonfirmasi diterima. Status saat ini: " + statusSaatIni);
        }

        String sql = "UPDATE jadwal SET status = 'diterima' WHERE id = ?::uuid AND sekolah_id = ?::uuid";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jadwalId);
            stmt.setString(2, sekolahId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ─────────────────────────────────────────────
    // CREATE – Vendor membuat jadwal/pengiriman baru (Diselaraskan dengan DbConfig)
    // ─────────────────────────────────────────────
    public void tambahJadwal(JadwalPengiriman item, String idVendor) throws SQLException {
        // Query disesuaikan dengan skema UUID dan penamaan kolom database yang konsisten
        String query = """
            INSERT INTO jadwal (vendor_id, sekolah_id, menu_id, jumlah_porsi, tanggal, status) 
            VALUES (?::uuid, ?::uuid, ?::uuid, ?, ?::date, ?)
            """;
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idVendor);
            ps.setString(2, item.getNamaSekolah()); // Pastikan ini berisi ID Sekolah berupa string UUID
            ps.setString(3, item.getNamaMenu());    // Pastikan ini berisi ID Menu berupa string UUID
            ps.setInt(4, item.getJumlahPorsi());
            ps.setString(5, item.getTanggal());     // Format string tanggal: 'YYYY-MM-DD'
            ps.setString(6, item.getStatus());
            ps.executeUpdate();
        }
    }

    // ─────────────────────────────────────────────
    // HELPER – ambil status berdasarkan id
    // ─────────────────────────────────────────────
    private String getStatusById(String jadwalId) throws SQLException {
        String sql = "SELECT status FROM jadwal WHERE id = ?::uuid";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jadwalId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("status");
            }
        }
        return null;
    }

    private String getStatusByIdAndSekolah(String jadwalId, String sekolahId) throws SQLException {
        String sql = "SELECT status FROM jadwal WHERE id = ?::uuid AND sekolah_id = ?::uuid";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jadwalId);
            stmt.setString(2, sekolahId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("status");
            }
        }
        return null;
    }

    // ─────────────────────────────────────────────
    // HELPER – mapping ResultSet → JadwalPengiriman
    // ─────────────────────────────────────────────
    private JadwalPengiriman mapRow(ResultSet rs) throws SQLException {
        return new JadwalPengiriman(
                rs.getString("id"),
                rs.getString("nama_vendor"),
                rs.getString("nama_sekolah"),
                rs.getString("nama_menu"),
                rs.getInt("jumlah_porsi"),
                rs.getString("tanggal"),
                rs.getString("status")
        );
    }
}