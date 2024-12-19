

# Kişisel Not Defteri 

Bu proje, dönem odevimiz olan kişisel not defteri uygulamasıdır. Projenin arayüzünü JavaFX ve onun programlama dili olan FXML kullanarak yaptık.
Projede kullanıcılar notlarını ekleyebilir, düzenleyebilir, silebilir ve filtreleme yapabilir. 
Ayrıca, notlara hatırlatma tarihi eklenebilir ve notlar farklı durumlara (Yeni, Okunmuş, Düzenlenmiş, Silinmiş) 
göre yönetilebilir. Projeyi yaparken Java programlama dilini kullandık, SQL servisi olarak da daha önce öğrendiğimiz bir teknoloji olan 
MySQL kullandık. Dependency için gradle yerine Maven Kullandık. 


## Proje Yapısı

### com.notdefteri.model

- **Not.java**: Not sınıfı, notların temel özelliklerini ve durum yönetimini içerir.
- **GorselNot.java**: Görsel notlar için genişletilmiş bir sınıf.
- **NotDAO.java**: Veritabanı işlemlerini gerçekleştiren sınıf.

### com.notdefteri.state

- **NotState.java**: Notların durumlarını belirten arayüz.
- **YeniNot.java**: Yeni not durumunu temsil eden sınıf.
- **OkunmusNot.java**: Okunmuş not durumunu temsil eden sınıf.
- **DuzenlenmisNot.java**: Düzenlenmiş not durumunu temsil eden sınıf.
- **SilinmisNot.java**: Silinmiş not durumunu temsil eden sınıf.

### com.notdefteri.arayuz

- **AnaArayuzKontrol.java**: Ana arayüzün kontrol sınıfı. Not ekleme, düzenleme, silme ve filtreleme işlemlerini yönetir.
- **NotDetayArayuzKontrol.java**: Not detaylarının görüntülendiği arayüzün kontrol sınıfı.

### com.notdefteri.veritabani

- **VeritabaniBaglantisi.java**: Veritabanı bağlantısını yöneten sınıf.

## Özellikler

- **Not Ekleme**: Kullanıcılar yeni not ekleyebilirler.
- **Not Düzenleme**: Mevcut notları düzenleyebilirler.
- **Not Silme**: Kullanıcılar notları silebilirler.
- **Durum Yönetimi**: Notlar farklı durumlara göre yönetilebilir (Yeni, Okunmuş, Düzenlenmiş, Silinmiş).
- **Filtreleme**: Notlar başlık, içerik, kategori, etiket ve hatırlatma tarihine göre filtrelenebilir.
- **Hatırlatma Tarihi**: Notlara hatırlatma tarihi eklenebilir ve bu tarih tabloda görüntülenebilir.



