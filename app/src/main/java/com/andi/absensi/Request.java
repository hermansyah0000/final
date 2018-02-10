package com.andi.absensi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by andi on 12/17/17.
 */

public class Request {

    public String SendPostRequest(String link, HashMap<String,String> data) {
        URL url;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data(data));
            bufferedWriter.flush();
            bufferedWriter.close();
            if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                String res;
                while ((res = bufferedReader.readLine()) != null) {
                    stringBuilder.append(res);
                }
            }
        } catch (Exception v) {
            v.printStackTrace();
        }

        return stringBuilder.toString();
    }
    public String sendPostAbsen(String link, String data){
        URL url;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data);
//            bufferedWriter.writeB
            bufferedWriter.flush();
            bufferedWriter.close();
            if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                String res;
                while ((res = bufferedReader.readLine()) != null) {
                    stringBuilder.append(res);
                }
            }
        } catch (Exception v) {
            v.printStackTrace();
        }

        return stringBuilder.toString();
    }
//    private String dataSiswa(String data) throws UnsupportedEncodingException {
//        StringBuilder stringBuilder = new StringBuilder();
//        boolean pertama = true;
//        for (Map.Entry<String,HashMap<String,String>> entry: data.entrySet()){
//            if (pertama)
//                pertama = false;
//            else
//                stringBuilder.append("&");
//            entry.getKey();
//            stringBuilder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
//            stringBuilder.append("=");
//            stringBuilder.append(URLEncoder.encode(String.valueOf(entry.getValue()),"UTF-8"));
//        }
//        return stringBuilder.toString();
//    }
    private String data(HashMap<String,String> params) throws UnsupportedEncodingException{
        StringBuilder stringBuilder = new StringBuilder();
        boolean pertama = true;
        for (Map.Entry<String,String> entry: params.entrySet()){
            if (pertama)
                pertama = false;
            else
                stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return stringBuilder.toString();
    }
    public static String getResponAmbilSiswa(String s1){

        StringBuilder stringBuilder = new StringBuilder();
        String kosong = null;
        try {
            Config config = new Config();
            URL url1 = new URL(config.makeUrl(s1));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine())!= null){
                stringBuilder.append(s+"\n");
            }
            kosong = stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return kosong ;
    }


}
