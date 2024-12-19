package com.notdefteri.arayuz;

import com.notdefteri.model.Not;
import com.notdefteri.model.NotDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Timestamp;

public class NotDetayArayuzKontrol {
    @FXML
    private TextField detayBaslik;
    @FXML
    private TextField detayIcerik;
    @FXML
    private TextField detayKategori;
    @FXML
    private TextField detayEtiketler;
    @FXML
    private TextField detayHatirlatmaTarihi;

    private Not secilenNot;
    private NotDAO notDAO = new NotDAO();
    private AnaArayuzKontrol anaArayuzKontrol;

    public void setDetaylar(String baslik, String icerik, String kategori, String etiketler, String hatirlatmaTarihi) {
        detayBaslik.setText(baslik);
        detayIcerik.setText(icerik);
        detayKategori.setText(kategori);
        detayEtiketler.setText(etiketler);
        detayHatirlatmaTarihi.setText(hatirlatmaTarihi);
    }

    public void setSecilenNot(Not not) {
        this.secilenNot = not;
    }

    public void setAnaArayuzKontrol(AnaArayuzKontrol anaArayuzKontrol) {
        this.anaArayuzKontrol = anaArayuzKontrol;
    }

    @FXML
    private void detaylariKapat() {
        Stage stage = (Stage) detayBaslik.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void notuDuzenle() {
        if (secilenNot != null) {
            secilenNot.setBaslik(detayBaslik.getText());
            secilenNot.setIcerik(detayIcerik.getText());
            secilenNot.setKategori(detayKategori.getText());
            secilenNot.setEtiketler(detayEtiketler.getText());
            secilenNot.setHatirlatmaTarihi(detayHatirlatmaTarihi.getText().isEmpty() ? null : Timestamp.valueOf(detayHatirlatmaTarihi.getText()));

            notDAO.notGuncelle(secilenNot);
            if (anaArayuzKontrol != null) {
                anaArayuzKontrol.notlariYukle();
            }
            detaylariKapat();
        }
    }

    @FXML
    private void notuSil() {
        if (secilenNot != null) {
            notDAO.notSil(secilenNot.getId());
            if (anaArayuzKontrol != null) {
                anaArayuzKontrol.notlariYukle();
            }
            detaylariKapat();
        }
    }
}