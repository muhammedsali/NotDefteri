package com.notdefteri.arayuz;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import com.notdefteri.model.GorselNot;
import com.notdefteri.model.Not;
import com.notdefteri.model.NotDAO;
import com.notdefteri.tasarimdesenleri.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.stream.Collectors;
public class AnaArayuzKontrol {
    @FXML
    private TextField baslik;
    @FXML
    private TableColumn<Not, ImageView> resimKolonu;
    @FXML
    private TextArea icerik;
    @FXML
    private ComboBox<String> kategori;
    @FXML
    private TextField etiketler;
    @FXML
    private TextField aramaAlani;
    @FXML
    private DatePicker hatirlatmaTarihi;
    @FXML
    private ImageView resimGoruntuleyici;
    @FXML
    private TableView<Not> notTablosu;
    @FXML
    private TableColumn<Not, String> baslikKolonu;
    @FXML
    private TableColumn<Not, String> icerikKolonu;
    @FXML
    private TableColumn<Not, String> kategoriKolonu;
    @FXML
    private TableColumn<Not, String> hatirlatmaTarihiKolonu;

    private final NotDAO notDAO;
    private ObservableList<Not> notlar;
    private NotFabrikasi notFabrikasi;
    private NotDurumYonetici durumYonetici;
    private File yuklenenResim;

    public AnaArayuzKontrol() {
        this.notDAO = new NotDAO();
        this.notlar = FXCollections.observableArrayList();
        this.durumYonetici = new NotDurumYonetici();
    }





    @FXML
    private void initialize() {
        kategori.setItems(FXCollections.observableArrayList("Kişisel Notlar", "İş ve Projeler", "Eğitim","Alışveriş"));
        notTablosu.setItems(notlar);

        baslikKolonu.setCellValueFactory(cellData -> cellData.getValue().baslikProperty());
        icerikKolonu.setCellValueFactory(cellData -> cellData.getValue().icerikProperty());
        kategoriKolonu.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        hatirlatmaTarihiKolonu.setCellValueFactory(cellData -> cellData.getValue().hatirlatmaTarihiProperty());

        resimKolonu.setCellValueFactory(cellData -> {
            Not not = cellData.getValue();
            if (not instanceof GorselNot) {
                GorselNot gorselNot = (GorselNot) not;
                byte[] resimVerisi = gorselNot.getResim();
                if (resimVerisi != null) {
                    ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(resimVerisi)));
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    return new SimpleObjectProperty<>(imageView);
                }
            }
            return new SimpleObjectProperty<>(null);
        });

        notlariYukle();

        aramaAlani.textProperty().addListener((observable, eskiDeger, yeniDeger) -> notlariFiltrele(yeniDeger));

        baslikKolonu.setSortType(TableColumn.SortType.ASCENDING);
        notTablosu.getSortOrder().add(baslikKolonu);
    }




    public void notlariYukle() {
        notlar.clear();
        notlar.addAll(notDAO.notlariGetir());
    }

    private void notlariFiltrele(String filtre) {
        notlar.clear();
        notlar.addAll(notDAO.notlariGetir().stream()
                .filter(not -> not.getBaslik().toLowerCase().contains(filtre.toLowerCase()))
                .collect(Collectors.toList()));
    }

    @FXML
    private Label bilgilendirmeMesaji;

    @FXML
    private void notuKaydet() throws IOException {
        String notBaslik = baslik.getText();
        String notIcerik = icerik.getText();
        String notKategori = kategori.getValue();
        String notEtiketler = etiketler.getText();
        LocalDate localDate = hatirlatmaTarihi.getValue();
        Timestamp hatirlatma = localDate != null ? Timestamp.valueOf(localDate.atStartOfDay()) : null;

        // Kullanıcının seçimine göre uygun fabrika kullanılarak not oluşturulması
        if (notKategori != null && notKategori.equals("Metin")) {
            notFabrikasi = new MetinNotFabrikasi();
        } else if (notKategori != null && notKategori.equals("Görsel")) {
            notFabrikasi = new GorselNotFabrikasi();
        } else {
            // Default olarak metin notu oluştur
            notFabrikasi = new MetinNotFabrikasi();
        }
        Not yeniNot = notFabrikasi.notOlustur();

        yeniNot.setBaslik(notBaslik);
        yeniNot.setIcerik(notIcerik);
        yeniNot.setTarih("2024-12-19"); // Örnek tarih
        yeniNot.setKategori(notKategori);
        yeniNot.setEtiketler(notEtiketler);
        yeniNot.setHatirlatmaTarihi(hatirlatma);

        if (yeniNot instanceof GorselNot && yuklenenResim != null) {
            byte[] resimVerisi = Files.readAllBytes(yuklenenResim.toPath());
            ((GorselNot) yeniNot).setResim(resimVerisi);
        }

        notDAO.notEkle(yeniNot);
        notlariYukle();

        // Bilgilendirme mesajı gösterme
        Alert alert = new Alert(AlertType.INFORMATION, "Not kaydedildi!", ButtonType.OK);
        alert.showAndWait();

        // Giriş alanlarını sıfırlama
        baslik.clear();
        icerik.clear();
        kategori.setValue(null);
        etiketler.clear();
        hatirlatmaTarihi.setValue(null);
        resimGoruntuleyici.setImage(null);
    }





    @FXML
    private void resimYukle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) baslik.getScene().getWindow();
        yuklenenResim = fileChooser.showOpenDialog(stage);
        if (yuklenenResim != null) {
            Image resim = new Image(yuklenenResim.toURI().toString());
            resimGoruntuleyici.setImage(resim);
        }
    }

    @FXML
    private void notDetaylariniGoster() {
        Not secilenNot = notTablosu.getSelectionModel().getSelectedItem();
        if (secilenNot != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/notdefteri/ana/notDetayArayuz.fxml"));
                Parent root = fxmlLoader.load();

                NotDetayArayuzKontrol kontrol = fxmlLoader.getController();
                String hatirlatmaTarihi = secilenNot.getHatirlatmaTarihi() != null ? secilenNot.getHatirlatmaTarihi() : "";
                kontrol.setDetaylar(secilenNot.getBaslik(), secilenNot.getIcerik(), secilenNot.getKategori(), secilenNot.getEtiketler(), hatirlatmaTarihi);
                kontrol.setSecilenNot(secilenNot);
                kontrol.setAnaArayuzKontrol(this);

                if (secilenNot instanceof GorselNot) {
                    byte[] resimVerisi = ((GorselNot) secilenNot).getResim();
                    if (resimVerisi != null) {
                        Image resim = new Image(new ByteArrayInputStream(resimVerisi));
                        kontrol.getResimGoruntuleyici().setImage(resim);
                    }
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Not Detayları");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void notuDuzenle() throws IOException {
        Not secilenNot = notTablosu.getSelectionModel().getSelectedItem();
        if (secilenNot != null) {
            secilenNot.setBaslik(baslik.getText());
            secilenNot.setIcerik(icerik.getText());
            secilenNot.setKategori(kategori.getValue());
            secilenNot.setEtiketler(etiketler.getText());
            LocalDate localDate = hatirlatmaTarihi.getValue();
            Timestamp hatirlatma = localDate != null ? Timestamp.valueOf(localDate.atStartOfDay()) : null;
            secilenNot.setHatirlatmaTarihi(hatirlatma);

            if (secilenNot instanceof GorselNot && yuklenenResim != null) {
                byte[] resimVerisi = Files.readAllBytes(yuklenenResim.toPath());
                ((GorselNot) secilenNot).setResim(resimVerisi);
            }

            notDAO.notGuncelle(secilenNot);
            notlariYukle();

            durumYonetici.setDurum(new DuzenlemeDurumu());
            durumYonetici.isle();
        }
    }

    @FXML
    private void notuSil() {
        Not secilenNot = notTablosu.getSelectionModel().getSelectedItem();
        if (secilenNot != null) {
            notDAO.notSil(secilenNot.getId());
            notlariYukle();

            durumYonetici.setDurum(new GoruntulemeDurumu());
            durumYonetici.isle();
        }
    }
}
