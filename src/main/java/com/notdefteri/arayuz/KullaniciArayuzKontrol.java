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

public class KullaniciArayuzKontrol {
    @FXML
    private TextField kullaniciAdi;
    @FXML
    private PasswordField sifre;

    private final KullaniciDAO kullaniciDAO;

    public KullaniciArayuzKontrol() {
        this.kullaniciDAO = new KullaniciDAO();
    }

    @FXML
    private void girisYap() {
        String adi = kullaniciAdi.getText();
        String sifresi = sifre.getText();
        Kullanici kullanici = kullaniciDAO.kullaniciGetir(adi, sifresi);

        if (kullanici != null) {
            Alert alert = new Alert(AlertType.INFORMATION, "Giriş Başarılı!");
            alert.showAndWait();

            // Ana arayüze yönlendirme
            Stage stage = (Stage) kullaniciAdi.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/notdefteri/ana/anaArayuz.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.setTitle("Kişisel Not Defteri - Ana Arayüz");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Giriş Başarısız! Kullanıcı adı veya şifre yanlış.");
            alert.showAndWait();
        }
    }

    @FXML
    private void uyeOl() {
        Stage stage = (Stage) kullaniciAdi.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/notdefteri/ana/kayitArayuz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Kişisel Not Defteri - Kayıt Ol");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
