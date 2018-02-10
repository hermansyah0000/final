package com.andi.absensi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    TextView username,password;
    Button login;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekLogin();
            }
        });
    }
    private void cekLogin(){

        final String u_m = username.getText().toString();
        final String paswd = password.getText().toString();

        class CekLogin extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            protected String doInBackground(Void... V){
                HashMap<String,String> data = new HashMap<>();
                data.put("LoginForm[username]",u_m);
                data.put("LoginForm[password]",paswd);
                Request request = new Request();
                String respon = request.SendPostRequest(Config.login,data);
                return respon;
            }

            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,null,"Harap Tunggu",false,false);
            }

            protected void onPostExecute(String hasil){
                super.onPostExecute(hasil);
                loading.dismiss();
                //getJson(hasil);
                if (hasil.toString() == ""){
                    message("Koneksi Data Terputus");
                }
                else
                {
                    getJson(hasil);
                }

            }
        }
        CekLogin cl = new CekLogin();
        cl.execute();
    }
    private void getJson(String json){
       try
       {
           JSONObject jsonObject = new JSONObject(json);
           JSONArray jsonArray = jsonObject.getJSONArray("hasil");
           JSONObject jsonObject1   = jsonArray.getJSONObject(0);

           if (jsonObject1.getString("val").toString() == "false"){
               JSONObject status = jsonArray.getJSONObject(1);
               JSONObject foto = jsonArray.getJSONObject(2);
               JSONObject nama = jsonArray.getJSONObject(3);


              session = new Session(getApplicationContext());
              session.setSession(session.Key_status,status.getString("status").toString());
              session.setSession(session.Key_id,username.getText().toString());
              session.setSession(session.Key_foto,foto.getString("foto").toString());
              session.setSession(session.Key_nama,nama.getString("nama").toString());
               Intent intent = new Intent(Login.this,MainActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
               finish();


           }
           else{
               message("Kata Sandi Atau Nama Pengguna Tidak Ditemukan");
           }


       }catch (JSONException c){

       }

    }
    private void message(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pesan");
        alert.setMessage(msg);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                password.setText(null);
                password.setFocusable(true);
            }
        });
        alert.create().show();
    }

}
