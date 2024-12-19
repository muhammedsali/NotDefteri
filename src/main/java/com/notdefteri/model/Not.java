package com.notdefteri.model;

import com.notdefteri.state.NotState;
import com.notdefteri.state.YeniNot;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Not {
    private int id;
    private StringProperty baslik;
    private StringProperty icerik;
    private StringProperty kategori;
    private StringProperty etiketler;
    private StringProperty hatirlatmaTarihi; // Time yerine StringProperty kullanıldı
    private String tarih;
    private NotState state;

    public Not() {
        this.baslik = new SimpleStringProperty();
        this.icerik = new SimpleStringProperty();
        this.kategori = new SimpleStringProperty();
        this.etiketler = new SimpleStringProperty();
        this.hatirlatmaTarihi = new SimpleStringProperty();
        this.state = new YeniNot(); // Varsayılan başlangıç durumu
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik.get();
    }

    public void setBaslik(String baslik) {
        this.baslik.set(baslik);
    }

    public StringProperty baslikProperty() {
        return baslik;
    }

    public String getIcerik() {
        return icerik.get();
    }

    public void setIcerik(String icerik) {
        this.icerik.set(icerik);
    }

    public StringProperty icerikProperty() {
        return icerik;
    }

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String kategori) {
        this.kategori.set(kategori);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public String getEtiketler() {
        return etiketler.get();
    }

    public void setEtiketler(String etiketler) {
        this.etiketler.set(etiketler);
    }

    public StringProperty etiketlerProperty() {
        return etiketler;
    }

    public String getHatirlatmaTarihi() {
        return hatirlatmaTarihi.get();
    }

    public void setHatirlatmaTarihi(Timestamp hatirlatmaTarihi) {
        if (hatirlatmaTarihi != null) {
            // Formatlama yapılarak daha okunabilir hale getirildi
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.hatirlatmaTarihi.set(hatirlatmaTarihi.toLocalDateTime().format(formatter));
        } else {
            this.hatirlatmaTarihi.set(null);
        }
    }

    public void setHatirlatmaTarihi(String hatirlatmaTarihi) {
        this.hatirlatmaTarihi.set(hatirlatmaTarihi);
    }

    public StringProperty hatirlatmaTarihiProperty() {
        return hatirlatmaTarihi;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public void setState(NotState state) {
        this.state = state;
    }

    public NotState getState() {
        return state;
    }

    public void oku() {
        state.oku(this);
    }

    public void duzenle(String yeniBaslik, String yeniIcerik) {
        state.duzenle(this, yeniBaslik, yeniIcerik);
    }

    public void sil() {
        state.sil(this);
    }


}
