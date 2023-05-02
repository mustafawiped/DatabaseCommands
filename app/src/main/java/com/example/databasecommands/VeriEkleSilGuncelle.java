package com.example.databasecommands;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class VeriEkleSilGuncelle extends AppCompatActivity {
    // D E V E L O P E D     M U S T A F A W I P E D \\
    SQLiteDatabase db;
    EditText kadiText, sifreText, epostaText, telText;

    private void init() {   // init "initalize" nin kısaltılmış halidir. Başlangıç tanımlamaları genellikle bu isimle yapılır.
        kadiText = findViewById(R.id.kadiTxt);
        sifreText = findViewById(R.id.sifreTxt);    // xml dosyaları ile gerekli bağlantılar sağlandı.
        epostaText = findViewById(R.id.epostaTxt);
        telText = findViewById(R.id.telTxt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_ekle_sil_guncelle);
        init();   //tanımlama fonksiyonları çağırıldı.
        db = openOrCreateDatabase("veritabani", MODE_PRIVATE, null);  // Veritabanı eğer yoksa oluşturuldu ve açıldı. Varsa açıldı.
        db.execSQL("CREATE TABLE IF NOT EXISTS veriler (id INTEGER PRIMARY KEY," +
                "kadi TEXT," +
                "sifre TEXT," +
                "eposta TEXT," +
                "tel TEXT)");
        // burada eğer veriler isminde tablo yoksa oluşturduk. veriler isminde tablo varsa bir daha oluşturmaz.
        // id yi Auto Increment yani veri eklendikçe otomatik artan ve benzersiz sekilde ayarladık.
        // kullanıcı adı sifre eposta text olarak ayarlamamız normal lakin neden telefon numarasını
        // text olarak ayarladığımı merak ediyorsanız, veritabanında daha az yer kaplamak için. (text 4 byte kaplarken long 8 byte kaplar)
    }

    public void btnClick(View view) {
        RadioGroup rdgroup = findViewById(R.id.rdGroup);  // Radio Group tanımladık
        rdgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // radio group seçili durum değiştiğinde
            @Override
            public void onCheckedChanged(RadioGroup group, int secilenId) { // seçim değiştiğinde çalışacak method
                if (secilenId == R.id.rdbtnVeriEkle)  // eğer seçilen radio button bu id ye sahipse
                    VeriEkle();                 // VeriEkle methodunu çağır.
                else if (secilenId == R.id.rdbtnVeriGuncelle)
                    VeriGuncelle();
                else if (secilenId == R.id.rdbtnVeriSil)
                    VeriSil();
            }
        });
    }

    private void VeriGuncelle() {
        String kadi = kadiText.getText().toString();  // Kullanıcı adını çektik.
        Cursor cs = db.rawQuery("SELECT kadi FROM veriler WHERE kadi = '" + kadi + "'", null); //girilen kullanıcı adına sahip veriyi çektik
        if (cs != null && cs.moveToFirst()) { // eğer null değilse ve en az 1 satır varsa..
            cs.close();
            String sifre = sifreText.getText().toString(),
                    eposta = epostaText.getText().toString(),   // şifre eposta ve telefonu çektik.
                    tel = telText.getText().toString();
            SQLiteStatement statement = db.compileStatement("UPDATE veriler SET sifre = ?, eposta = ?, tel = ? WHERE kadi = ?"); //sorgu oluşturduk
            statement.bindString(1, sifre);
            statement.bindString(2, eposta);
            statement.bindString(3, tel);    // gerekli verileri, olması gerektiği sırayla belirttik.
            statement.bindString(4, kadi);
            statement.execute();   // sorguyu çalıştırdık.
            Toast.makeText(this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();   // Bildiri gönderdik
        } else {
            cs.close();// Cursor u kapadık
        Toast.makeText(this, "Veri Bulunamadı!", Toast.LENGTH_SHORT).show();  // Eğer gelen değer null ise veya hiç veri gelmemişse hata ver.
        }
    }

    private void VeriEkle() {
        String kadi = kadiText.getText().toString(),
                sifre = sifreText.getText().toString(),   // EditText lerdeki verileri değişkenlere aktardık.
                eposta = epostaText.getText().toString(),
                tel = telText.getText().toString();
        Cursor cs = db.rawQuery("SELECT * FROM veriler", null);
        // Cursor yani imleç ile veritabanındaki tüm verileri seçtik.
        if (cs != null && cs.moveToFirst()) {
            // gelen veriler bilinmeyen yani 0 değilse ve 1. Veriden başlıyorsa aşağıdaki kodları yap.
            do {
                @SuppressLint("Range") String gelenkadi = cs.getString(cs.getColumnIndex("kadi"));
                // supperess komutu hatayı görmezden gelir. Bu komut tehlikelidir ve yalnızca gerektiğinde kullanılmalıdır.
                if(gelenkadi.equals(kadi)) { // eğer gelen kullanıcı adı, edittextteki kullanıcı adıyla aynıysa yani eşitse..
                    Toast.makeText(this, "Bu kullanıcı adı zaten kullanılıyor.", Toast.LENGTH_SHORT).show(); // toast at
                    return; // ve kodu durdur. Başa dönder. Return atarsanız kod aşağıdaki kodları okumaz ve direk methodu bitirir.
                }
            } while (cs.moveToNext()); // do-while döngüsü ile tüm verileri sırayla okuduk ve Kullanıcı adı kontrolü yaptık.
            SQLiteStatement statement = db.compileStatement("INSERT INTO veriler (kadi,sifre,eposta,tel) VALUES (?,?,?,?)");
            // üst satırdaki kodda SQLite Statement tanımladık. Bu bize veritabanında sorgu oluşturmamıza ve çalıştırmamıza yarar.
            // insert into ise veri eklememizi sağlar. VALUES kısmındaki soru işaretlerinin anlamı sonradan veri ekleyeceğimizdir.
            statement.bindString(1, kadi);
            statement.bindString(2, sifre);
            statement.bindString(3, eposta);
            statement.bindString(4, tel);
            // parantezdeki ilk veri, ekleyeceğimiz sütunun sırasıdır. İkinci yer ise eklenecek değerdir.
            statement.execute();
            // statement i execute ederek yukarıda yaptığımız tüm işlemleri çalıştırırız.
            Toast.makeText(this, "Veri başarıyla veri tabanına eklendi!", Toast.LENGTH_SHORT).show();
        }
    }

    private void VeriSil() {
        String kadi = kadiText.getText().toString();   // silinecek veriyi ayrıştırmak için kullanıcı adını çekiyoruz.
        String[] sorgu = {kadi};    // Cursor nesnesiyle verinin olup olmadığını kontrol ederkenki kullanılack sorgu için dizi oluşturuyoruz.
        if (!kadi.isEmpty()) {    // burda kadi değişkeni boş mu dolu mu kontrol ediyoruz. Normalde kadi.isEmpty() düz boşsa true verir ama başına ! koyduğum için true ise false değeri verir yani tam tersi değer.
            Cursor cs = db.rawQuery("SELECT kadi FROM veriler WHERE kadi = ?",sorgu);  // Cursor ile veritabanı sorgusu yapıp veri çektik
            if (cs != null && cs.moveToFirst()) {   // ve eğer gelen veri null değilse ve en az 1 veri varsa aşağıdaki kodları çalıştır dedik.
                cs.close();   // ilk başta cursor u kapattık çünkü artık işimize yaramayacak.
                SQLiteStatement statement = db.compileStatement("DELETE FROM veriler WHERE kadi = ?");  // sqlstatement ile silme sorgusu çalıştırdık
                statement.bindString(1,kadi);  // soru işareti olan yere veri ekledik bindString ile.
                statement.execute();  // sorguyu veritabanında çalıştırdık.
            } else {  // eğer cursor nesnesindeki değer null ise veya hiç bir değer gelmemişse..
                cs.close();  // cursor u kapatıyoruz
                Toast.makeText(this, "Girdiğiniz kullanıcı adında veri bulunamadı.", Toast.LENGTH_SHORT).show(); // ve uyarı veriyoruz.
            }
        }
        else Toast.makeText(this, "Lütfen silmek istediğiniz metni girin.", Toast.LENGTH_SHORT).show(); // eğer kadi değişkeni dolu değilse yapılacaklar...
    }
}