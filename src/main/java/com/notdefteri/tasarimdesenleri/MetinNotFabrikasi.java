package com.notdefteri.tasarimdesenleri;

import com.notdefteri.model.MetinNot;
import com.notdefteri.model.Not;

public class MetinNotFabrikasi extends NotFabrikasi {
    @Override
    public Not notOlustur() {
        return new MetinNot();
    }
}
