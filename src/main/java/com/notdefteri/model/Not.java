package com.notdefteri.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.Timestamp;

public class Not {
    private int id;
    private StringProperty baslik;
    private StringProperty icerik;
    private StringProperty kategori;
    private StringProperty etiketler;
    private StringProperty hatirlatmaTarihi;
    private String tarih;

    public Not() {
        this.baslik = new SimpleStringProperty();
        this.icerik = new SimpleStringProperty();
        this.kategori = new SimpleStringProperty();
        this.etiketler = new SimpleStringProperty();
        this.hatirlatmaTarihi = new SimpleStringProperty();
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
            this.hatirlatmaTarihi.set(hatirlatmaTarihi.toString());
        } else {
            this.hatirlatmaTarihi.set(null);
        }
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
}
