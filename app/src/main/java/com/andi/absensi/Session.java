package com.andi.absensi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * Created by andi on 12/19/17.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
     Context context;

    int mode = 0;
    private static final String SESS_NAME = "absenSess";
    private static final String isLogin = "isLogin";
    public static final String Key_status = "status";
    public static final String Key_id = "id";
    public static final String Key_nama = "nama";
    public static final String Key_foto = "foto";

    public Session(Context context_){
        this.context = context_;
        sharedPreferences = context.getSharedPreferences(SESS_NAME,mode);
        editor = sharedPreferences.edit();
    }

    public void setSession(String key,String value){
        editor.putBoolean(isLogin,true);
        editor.commit();

        editor.putString(key,value);
        editor.commit();
    }

    public boolean checkSession(){
        if (!this.isLogin()){
            redirect();
            return false;
        }
        else
            return true;
    }

    private boolean isLogin(){
        return sharedPreferences.getBoolean(isLogin,false);
    }

    public void logout(){
        editor.putBoolean(isLogin,false);
        editor.commit();
        redirect();
    }

    public String getSession(String key){
        return sharedPreferences.getString(key,"").toString();
    }

    private void redirect(){
        Intent o = new Intent(context,Login.class);
        o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        o.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(o);
    }
}
