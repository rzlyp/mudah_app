package me.bayupaoh.mudah.Util;

/**
 * Created by codelabsunikom on 8/7/16.
 */
public class URLConnection {
    static String urlAPI = "http://ibacor.com/api";

    public static String getUrlHalal(String menu,String query,int page) {
        return urlAPI+"/produk-halal-mui?menu="+menu+"&query="+query+"&page="+page;
    }

    public static String getUrlKurs(String namaBank) {
        return urlAPI+"/kurs?bank="+namaBank;
    }

    public static String getUrlPLN(String idPelanggan,String tahun,String bln) {
        return urlAPI+"/tagihan-pln?idp="+idPelanggan.trim()+"&thn="+tahun+"&bln="+bln;
    }


}
