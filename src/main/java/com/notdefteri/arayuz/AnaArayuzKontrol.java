package com.notdefteri.arayuz;

import com.notdefteri.model.Not;
import com.notdefteri.model.NotDAO;
import com.notdefteri.tasarimdesenleri.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;

public class AnaArayuzKontrol {
    @FXML
    private TextField baslik;
    @FXML
    private TextField icerik;
    @FXML
    private ComboBox<String> kategori;
    @FXML
    private TextField etiketler;
    @FXML
    private TextField aramaAlani;
    @FXML
    private DatePicker hatirlatmaTarihi;
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

    public AnaArayuzKontrol() {
        this.notDAO = new NotDAO();
        this.notlar = FXCollections.observableArrayList();
        this.durumYonetici = new NotDurumYonetici();
    }

    @FXML
    private void initialize() {
        kategori.setItems(FXCollections.observableArrayList("Metin", "Görsel", "Diğer"));
        notTablosu.setItems(notlar);

        baslikKolonu.setCellValueFactory(cellData -> cellData.getValue().baslikProperty());
        icerikKolonu.setCellValueFactory(cellData -> cellData.getValue().icerikProperty());
        kategoriKolonu.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        hatirlatmaTarihiKolonu.setCellValueFactory(cellData -> cellData.getValue().hatirlatmaTarihiProperty());

        notlariYukle();

        // Arama alanında metin değiştikçe notları filtrele
        aramaAlani.textProperty().addListener((observable, eskiDeger, yeniDeger) -> notlariFiltrele(yeniDeger));

        // Kolonlara sıralama işlevi ekleme
        baslikKolonu.setSortType(TableColumn.SortType.ASCENDING);
        notTablosu.getSortOrder().add(baslikKolonu);
    }

    void notlariYukle() {
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
    private void notuKaydet() {
        String notBaslik = baslik.getText();
        String notIcerik = icerik.getText();
        String notKategori = kategori.getValue();
        String notEtiketler = etiketler.getText();
        LocalDate localDate = hatirlatmaTarihi.getValue();
        Timestamp hatirlatma = localDate != null ? Timestamp.valueOf(localDate.atStartOfDay()) : null;

        // Kullanıcının seçimine göre uygun fabrika kullanılarak not oluşturulması
        if (notKategori.equals("Metin")) {
            notFabrikasi = new MetinNotFabrikasi();
        } else if (notKategori.equals("Görsel")) {
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

        notDAO.notEkle(yeniNot);
        notlariYukle();
    }

    @FXML
    private void notDetaylariniGoster() {
        if (notTablosu.getSelectionModel().getSelectedItem() != null) {
            Not secilenNot = notTablosu.getSelectionModel().getSelectedItem();

            if (secilenNot != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/notdefteri/ana/notDetayArayuz.fxml"));
                    Parent root = fxmlLoader.load();

                    NotDetayArayuzKontrol kontrol = fxmlLoader.getController();
                    String hatirlatmaTarihi = secilenNot.getHatirlatmaTarihi() != null ? secilenNot.getHatirlatmaTarihi() : "";
                    kontrol.setDetaylar(secilenNot.getBaslik(), secilenNot.getIcerik(), secilenNot.getKategori(), secilenNot.getEtiketler(), hatirlatmaTarihi);
                    kontrol.setSecilenNot(secilenNot);
                    kontrol.setAnaArayuzKontrol(this); // Ana arayüz kontrol sınıfını geçiyoruz

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Not Detayları");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void notuDuzenle() {
        Not secilenNot = notTablosu.getSelectionModel().getSelectedItem();
        if (secilenNot != null) {
            secilenNot.setBaslik(baslik.getText());
            secilenNot.setIcerik(icerik.getText());
            secilenNot.setKategori(kategori.getValue());
            secilenNot.setEtiketler(etiketler.getText());
            LocalDate localDate = hatirlatmaTarihi.getValue();
            Timestamp hatirlatma = localDate != null ? Timestamp.valueOf(localDate.atStartOfDay()) : null;
            secilenNot.setHatirlatmaTarihi(hatirlatma);
            notDAO.notGuncelle(secilenNot);
            notlariYukle();

            // Durum yönetici ile düzenleme durumu set etme
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

            // Durum yönetici ile görüntüleme durumu set etme
            durumYonetici.setDurum(new GoruntulemeDurumu());
            durumYonetici.isle();
        }
    }
}
