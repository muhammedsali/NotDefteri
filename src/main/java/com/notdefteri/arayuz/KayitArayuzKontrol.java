package com.notdefteri.arayuz;

import com.notdefteri.model.Kullanici;
import com.notdefteri.model.KullaniciDAO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class KayitArayuzKontrol {
    @FXML
    private TextField kullaniciAdi;
    @FXML
    private PasswordField sifre;

    private final KullaniciDAO kullaniciDAO;

    public KayitArayuzKontrol() {
        this.kullaniciDAO = new KullaniciDAO();
    }

    @FXML
    private void kayitOl() {
        String adi = kullaniciAdi.getText();
        String sifresi = sifre.getText();
        Kullanici yeniKullanici = new Kullanici();
        yeniKullanici.setKullaniciAdi(adi);
        yeniKullanici.setSifre(sifresi);

        kullaniciDAO.kullaniciEkle(yeniKullanici);
        Alert alert = new Alert(AlertType.INFORMATION, "Kayıt Başarılı!");
        alert.showAndWait();

        // Kayıt işlemi tamamlandıktan sonra giriş ekranına yönlendirme
        Stage stage = (Stage) kullaniciAdi.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/notdefteri/ana/kullaniciArayuz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Kişisel Not Defteri - Kullanıcı Girişi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
