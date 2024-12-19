package com.notdefteri.tasarimdesenleri;

public class NotDurumYonetici {
    private Durum durum;

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public void isle() {
        if (durum != null) {
            durum.isle();
        }
    }
}
