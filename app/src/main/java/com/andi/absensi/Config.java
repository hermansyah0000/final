package com.andi.absensi;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andi on 12/17/17.
 */

public class Config {
    public static final String login = "http://192.168.43.171/webschool/admin/service/login";
    public static final String link_foto = "http://192.168.43.171/webschool/admin/img/";
    public static final String PARAM = "id_guru";
    public static final String BASE_URL = "http://192.168.43.171/webschool/admin/service/ambil-siswa";

    public static final String SAVE_ABSEN = "http://192.168.43.171/webschool/admin/service/kirim-absen";
    public static String makeUrl(String id_user) throws MalformedURLException {
        Uri makeUrl = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAM,id_user).build();
        URL url = null;
        try {
            url = new URL(makeUrl.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url.toString();
    }
}
