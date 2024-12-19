package com.notdefteri.tasarimdesenleri;

import com.notdefteri.model.GorselNot;
import com.notdefteri.model.Not;

public class GorselNotFabrikasi extends NotFabrikasi {
    @Override
    public Not notOlustur() {
        return new GorselNot();
    }
}
