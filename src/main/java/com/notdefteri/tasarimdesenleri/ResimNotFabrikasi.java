package com.notdefteri.tasarimdesenleri;

import com.notdefteri.model.Not;

public class ResimNotFabrikasi extends NotFabrikasi {
    @Override
    public Not notOlustur() {
        // Resim notu oluşturma işlemleri
        return new Not();
    }
}
