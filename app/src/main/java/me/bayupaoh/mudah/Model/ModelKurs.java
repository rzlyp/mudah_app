package me.bayupaoh.mudah.Model;

/**
 * Created by codelabsunikom on 8/8/16.
 */
public class ModelKurs {
    private String namaBank;
    private String status;
    private String mataUang;
    private int hargaJual;
    private int hargaBeli;

    public ModelKurs() {
    }

    public ModelKurs(String namaBank, String status, String mataUang, int hargaJual, int hargaBeli) {
        this.namaBank = namaBank;
        this.status = status;
        this.mataUang = mataUang;
        this.hargaJual = hargaJual;
        this.hargaBeli = hargaBeli;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMataUang() {
        return mataUang;
    }

    public void setMataUang(String mataUang) {
        this.mataUang = mataUang;
    }

    public int getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(int hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(int hargaBeli) {
        this.hargaBeli = hargaBeli;
    }
}
