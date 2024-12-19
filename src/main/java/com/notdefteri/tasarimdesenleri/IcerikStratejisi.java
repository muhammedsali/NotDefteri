package com.notdefteri.tasarimdesenleri;

interface IcerikStratejisi {
    String format(String icerik);
}

class HtmlIcerikStratejisi implements IcerikStratejisi {
    @Override
    public String format(String icerik) {
        return "<html>" + icerik + "</html>";
    }
}

class MarkdownIcerikStratejisi implements IcerikStratejisi {
    @Override
    public String format(String icerik) {
        return "**" + icerik + "**";
    }
}

class NotIcerikFormatlayici {
    private IcerikStratejisi strateji;

    public void setStrateji(IcerikStratejisi strateji) {
        this.strateji = strateji;
    }

    public String formatla(String icerik) {
        return strateji.format(icerik);
    }
}
