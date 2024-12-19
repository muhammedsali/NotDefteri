package com.notdefteri.tasarimdesenleri;

abstract class NotBileseni {
    public abstract String getIcerik();
}

class TemelNot extends NotBileseni {
    private String icerik;

    public TemelNot(String icerik) {
        this.icerik = icerik;
    }

    @Override
    public String getIcerik() {
        return icerik;
    }
}

abstract class NotDecorator extends NotBileseni {
    protected NotBileseni not;

    public NotDecorator(NotBileseni not) {
        this.not = not;
    }

    @Override
    public String getIcerik() {
        return not.getIcerik();
    }
}

class IsimliNotDecorator extends NotDecorator {
    private String isim;

    public IsimliNotDecorator(NotBileseni not, String isim) {
        super(not);
        this.isim = isim;
    }

    @Override
    public String getIcerik() {
        return isim + ": " + super.getIcerik();
    }
}
