package me.bayupaoh.mudah.Model;

/**
 * Created by codelabsunikom on 8/7/16.
 */
public class ModelHalal {
    private String namaProduk;
    private String namaProdusen;
    private String idProduk;
    private String tanggal;

    public ModelHalal() {
    }

    public ModelHalal(String namaProduk, String namaProdusen, String idProduk, String tanggal) {
        this.namaProduk = namaProduk;
        this.namaProdusen = namaProdusen;
        this.idProduk = idProduk;
        this.tanggal = tanggal;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNamaProdusen() {
        return namaProdusen;
    }

    public void setNamaProdusen(String namaProdusen) {
        this.namaProdusen = namaProdusen;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
