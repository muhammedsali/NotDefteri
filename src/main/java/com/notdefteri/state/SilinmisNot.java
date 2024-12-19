package com.notdefteri.state;

import com.notdefteri.model.Not;

public class SilinmisNot implements NotState {
    @Override
    public void oku(Not not) {
        // Silinmiş not okunamaz
    }

    @Override
    public void duzenle(Not not, String yeniBaslik, String yeniIcerik) {
        // Silinmiş not düzenlenemez
    }

    @Override
    public void sil(Not not) {
        // Zaten silinmiş durumda
    }
}
