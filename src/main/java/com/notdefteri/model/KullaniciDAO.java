package com.notdefteri.model;

import com.notdefteri.veritabani.VeritabaniBaglantisi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KullaniciDAO {
    private Connection baglanti;

    public KullaniciDAO() {
        baglanti = VeritabaniBaglantisi.getOrnek().getBaglanti();
    }

    public void kullaniciEkle(Kullanici kullanici) {
        String query = "INSERT INTO kullanicilar (kullaniciAdi, sifre) VALUES (?, ?)";
        try (PreparedStatement ps = baglanti.prepareStatement(query)) {
            ps.setString(1, kullanici.getKullaniciAdi());
            ps.setString(2, kullanici.getSifre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Kullanici kullaniciGetir(String kullaniciAdi, String sifre) {
        String query = "SELECT * FROM kullanicilar WHERE kullaniciAdi = ? AND sifre = ?";
        try (PreparedStatement ps = baglanti.prepareStatement(query)) {
            ps.setString(1, kullaniciAdi);
            ps.setString(2, sifre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Kullanici kullanici = new Kullanici();
                kullanici.setId(rs.getInt("id"));
                kullanici.setKullaniciAdi(rs.getString("kullaniciAdi"));
                kullanici.setSifre(rs.getString("sifre"));
                return kullanici;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
