package com.notdefteri.state;

import com.notdefteri.model.Not;

public interface NotState {
    void oku(Not not);
    void duzenle(Not not, String yeniBaslik, String yeniIcerik);
    void sil(Not not);
}
