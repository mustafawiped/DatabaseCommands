package com.example.databasecommands;

public class VerilerClass {
    public String kadi, sifre, eposta, tel;

    /*

    Kullanıcı adı, şifre, eposta ve telefon no gibi verileri diğer class lardan çekmek için
    ve hangi verinin hangisi olduğunu ayrıştırmak için VerilerClass sınıfını oluşturduk
    ve tüm verilere ilk baş Constractor oluşturduk daha sonra Getter ve Setter oluşturduk.

     */

    public VerilerClass(String kadi, String sifre, String eposta, String tel) {
        this.kadi = kadi;
        this.sifre = sifre;
        this.eposta = eposta;
        this.tel = tel;
    }

    public String getKadi() {
        return kadi;
    }

    public void setKadi(String kadi) {
        this.kadi = kadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
