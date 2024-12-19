Kişisel Not Defteri

Bu proje, dönem odevimiz olan kişisel not defteri uygulamasıdır. Projenin arayüzünü JavaFX ve onun programlama dili olan FXML kullanarak yaptık.
Projede kullanıcılar notlarını ekleyebilir, düzenleyebilir, silebilir ve filtreleme yapabilir.
Ayrıca, notlara hatırlatma tarihi eklenebilir ve notlar farklı durumlara (Yeni, Okunmuş, Düzenlenmiş, Silinmiş)
göre yönetilebilir. Projeyi yaparken Java programlama dilini kullandık, SQL servisi olarak da daha önce öğrendiğimiz bir teknoloji olan
MySQL kullandık. Dependency için gradle yerine Maven Kullandık.


Her şeyden önce sistemi kafada planlayıp Mysql ile iki tablolu bir veritabanı olusturdık. Daha sonra sistem için en temel desenimiz için ihtiyacımız olan abstact Factory classlarını olusturduk. MetinNot için ayrı Resim bulunan Not icin classlar olusturduk. Daha sonra sırasıyla observer ve Notların Durumu için state tasarım desenini kullandık. (Okunmus, Düzenlenmiş, silnmis vs. diye belirtebilmek için)

Daha sonra bu sistemi ekranda gösterebilmek için JavaFx ile bir arayüz olusturduk ve onu arayuz packetimizdeki classlarımızla handle ettik ve CRUD işlemleri, Kayıt işlemi gibi sistem olarak çalışan içeriklerimizi grafik arayüzünde de görebildik. Basit bir proje oldu çok fazla özellik yapamadık ama en azından temel özellikleri çalışıyor.

https://github.com/muhammedsali/NotDefteri
https://github.com/ahmetberahasanoglu/NotDefteri


