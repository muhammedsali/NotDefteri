package com.notdefteri.model;

import com.notdefteri.veritabani.VeritabaniBaglantisi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotDAO {
    private Connection baglanti;

    public NotDAO() {
        baglanti = VeritabaniBaglantisi.getOrnek().getBaglanti();
    }

    public void notEkle(Not not) {
        String query = "INSERT INTO notlar (baslik, icerik, tarih, kategori, etiketler, hatirlatmaTarihi, resim) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = baglanti.prepareStatement(query)) {
            ps.setString(1, not.getBaslik());
            ps.setString(2, not.getIcerik());
            ps.setString(3, not.getTarih());
            ps.setString(4, not.getKategori());
            ps.setString(5, not.getEtiketler());
            ps.setString(6, not.getHatirlatmaTarihi());

            if (not instanceof GorselNot) {
                ps.setBytes(7, ((GorselNot) not).getResim());
            } else {
                ps.setBytes(7, null);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Not> notlariGetir() {
        List<Not> notlar = new ArrayList<>();
        String query = "SELECT * FROM notlar";
        try (PreparedStatement ps = baglanti.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Not not;
                String kategori = rs.getString("kategori");
                if ("Görsel".equals(kategori)) {
                    not = new GorselNot();
                    ((GorselNot) not).setResim(rs.getBytes("resim"));
                } else {
                    not = new MetinNot();
                }
                not.setId(rs.getInt("id"));
                not.setBaslik(rs.getString("baslik"));
                not.setIcerik(rs.getString("icerik"));
                not.setTarih(rs.getString("tarih"));
                not.setKategori(kategori);
                not.setEtiketler(rs.getString("etiketler"));
                String hatirlatmaTarihi = rs.getString("hatirlatmaTarihi");
                if (hatirlatmaTarihi != null) {
                    not.setHatirlatmaTarihi(Timestamp.valueOf(hatirlatmaTarihi));
                }
                notlar.add(not);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notlar;
    }

    public void notGuncelle(Not not) {
        String query = "UPDATE notlar SET baslik = ?, icerik = ?, tarih = ?, kategori = ?, etiketler = ?, hatirlatmaTarihi = ?, resim = ? WHERE id = ?";
        try (PreparedStatement ps = baglanti.prepareStatement(query)) {
            ps.setString(1, not.getBaslik());
            ps.setString(2, not.getIcerik());
            ps.setString(3, not.getTarih());
            ps.setString(4, not.getKategori());
            ps.setString(5, not.getEtiketler());
            ps.setString(6, not.getHatirlatmaTarihi());

            if (not instanceof GorselNot) {
                ps.setBytes(7, ((GorselNot) not).getResim());
            } else {
                ps.setBytes(7, null);
            }

            ps.setInt(8, not.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void notSil(Not not) {
        String sql = "DELETE FROM notlar WHERE id = ?";
        try (PreparedStatement pstmt = baglanti.prepareStatement(sql)) {
            pstmt.setInt(1, not.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Not> tumNotlariGetir() {//not veri tipinde classsa dgore tutuyor
        List<Not> notlar = new ArrayList<>();
        String sql = "SELECT * FROM notlar";
        try (Statement stmt = baglanti.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Not not = new MetinNot(); // veya doğru sınıfı kullanarak nesne oluşturun
                not.setId(rs.getInt("id"));
                not.setBaslik(rs.getString("baslik"));
                not.setIcerik(rs.getString("icerik"));
                not.setKategori(rs.getString("kategori"));
                not.setEtiketler(rs.getString("etiketler"));
                not.setHatirlatmaTarihi(rs.getString("hatirlatmaTarihi"));
                // Eğer GorselNot kullanıyorsanız, resim verisini de ekleyin
                if (not instanceof GorselNot) {
                    ((GorselNot) not).setResim(rs.getBytes("resim"));
                }
                notlar.add(not);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notlar;
    }

}
