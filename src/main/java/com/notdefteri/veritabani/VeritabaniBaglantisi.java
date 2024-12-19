package com.notdefteri.veritabani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {
    private static VeritabaniBaglantisi ornek;
    private Connection baglanti;

    private VeritabaniBaglantisi() {
        try {
            // MySQL veritabanı bağlantı ayrıntıları
            baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/notdefteri", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized VeritabaniBaglantisi getOrnek() {
        if (ornek == null) {
            ornek = new VeritabaniBaglantisi();
        }
        return ornek;
    }

    public Connection getBaglanti() {
        return baglanti;
    }
}
