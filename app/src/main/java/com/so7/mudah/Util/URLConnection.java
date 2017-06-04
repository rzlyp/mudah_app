package com.so7.mudah.Util;

/**
 * Created by codelabsunikom on 8/7/16.
 */
public class URLConnection {
    static String urlAPI = "http://ibacor.com/api";

    public static String getUrlHalal(String menu,String query,int page) {
        return "http://sites.google.com/macros/exec?service=AKfycbx_-gZbLP7Z2gGxehXhWMWDAAQsTp3e3bmpTBiaLuzSDQSbIFWD&menu="+menu+"&query="+query+"&page="+page;
    }

    public static String getUrlKurs(String namaBank) {
        return urlAPI+"/kurs?bank="+namaBank;
    }

    public static String getUrlPLN(String idPelanggan,String tahun,String bln) {
        return urlAPI+"/tagihan-pln?idp="+idPelanggan.trim()+"&thn="+tahun+"&bln="+bln;
    }

    public static String URL_INFO = "http://mudahapp.herokuapp.com/api/artikel";


}
