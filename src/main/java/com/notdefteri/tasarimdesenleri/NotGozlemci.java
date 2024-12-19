package com.notdefteri.tasarimdesenleri;

import java.util.ArrayList;
import java.util.List;

public class NotGozlemci {
    private List<Gozlemci> gozlemciler = new ArrayList<>();

    public void gozlemciEkle(Gozlemci gozlemci) {
        gozlemciler.add(gozlemci);
    }

    public void bildirimYap() {
        for (Gozlemci gozlemci : gozlemciler) {
            gozlemci.guncelle();
        }
    }
}
