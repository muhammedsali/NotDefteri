package com.notdefteri.state;

import com.notdefteri.model.Not;

public class DuzenlenmisNot implements NotState {
    @Override
    public void oku(Not not) {
        not.setState(new OkunmusNot());
    }

    @Override
    public void duzenle(Not not, String yeniBaslik, String yeniIcerik) {
        not.setBaslik(yeniBaslik);
        not.setIcerik(yeniIcerik);
    }

    @Override
    public void sil(Not not) {
        not.setState(new SilinmisNot());
    }
}
