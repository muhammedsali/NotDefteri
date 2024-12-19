package com.notdefteri.arayuz;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.notdefteri.model.Not;
import com.notdefteri.model.GorselNot;
import com.notdefteri.model.NotDAO;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @FXML
    private ImageView detayResimGoruntuleyici;

    private Not secilenNot;
    private NotDAO notDAO = new NotDAO();
    private AnaArayuzKontrol anaArayuzKontrol; // Ana kontrol referansı

    public void setDetaylar(String baslik, String icerik, String kategori, String etiketler, String hatirlatmaTarihi) {
        detayBaslik.setText(baslik);
        detayIcerik.setText(icerik);
        detayKategori.setText(kategori);
        detayEtiketler.setText(etiketler);
        detayHatirlatmaTarihi.setText(hatirlatmaTarihi);
    }

    public void setSecilenNot(Not not) {
        this.secilenNot = not;
        if (not instanceof GorselNot) {
            byte[] resimVerisi = ((GorselNot) not).getResim();
            if (resimVerisi != null) {
                Image resim = new Image(new ByteArrayInputStream(resimVerisi));
                detayResimGoruntuleyici.setImage(resim);
            }
        }
    }

    public void setAnaArayuzKontrol(AnaArayuzKontrol anaArayuzKontrol) {
        this.anaArayuzKontrol = anaArayuzKontrol;
    }

    @FXML
    private void notuDuzenle() {
        secilenNot.duzenle(detayBaslik.getText(), detayIcerik.getText());

        // Hatırlatma tarihini dönüştürerek ayarlayın
        String hatirlatmaTarihiStr = detayHatirlatmaTarihi.getText();
        if (!hatirlatmaTarihiStr.isEmpty()) {
            LocalDateTime hatirlatmaTarihi = LocalDateTime.parse(hatirlatmaTarihiStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            secilenNot.setHatirlatmaTarihi(Timestamp.valueOf(hatirlatmaTarihi));
        }

        notDAO.notGuncelle(secilenNot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Not güncellendi!", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void notuSil() {
        if (secilenNot != null) {
            secilenNot.sil();
            notDAO.notSil(secilenNot);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Not silindi!", ButtonType.OK);
            alert.showAndWait();
            anaArayuzKontrol.notlariYukle(); // Tabloyu güncelle
            pencereyiKapat();
        }
    }

    @FXML
    private void pencereyiKapat() {
        Stage stage = (Stage) detayBaslik.getScene().getWindow();
        stage.close();
    }


@FXML
    private void detaylariKapat() {
        Stage stage = (Stage) detayBaslik.getScene().getWindow();
        stage.close();
    }
}
