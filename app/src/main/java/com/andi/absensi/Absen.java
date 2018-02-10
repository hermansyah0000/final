package com.andi.absensi;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by andi on 1/17/18.
 */

public class Absen  extends SQLiteOpenHelper {


    public Absen(Context context) {
        super(context,"absen",null,14);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists absen (id integer primary key autoincrement," +
                "nim varchar(13),nama_siswa varchar(25),tgl varchar(10),jam varchar(10),status_hadir varchar(1),status varchar(1),id_mapel varchar(12));");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+"absen");
        onCreate(sqLiteDatabase);
//        sqLiteDatabase.execSQL("alter table absen add status_hadir varchar(1);");
    }

    public boolean addData(String nim,String nama_siswa,String tgl,String jam,String status,String id_mapel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim",nim);
        contentValues.put("nama_siswa",nama_siswa);
        contentValues.put("tgl",tgl);
        contentValues.put("jam",jam);
        contentValues.put("status_hadir",status);
        contentValues.put("status","p");
        contentValues.put("id_mapel",id_mapel);
        long hasil = db.insert("absen",null,contentValues);

        if (hasil == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public int deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("absen","status = ?",new String[]{"s"});
    }
    public Cursor showAbsen(String nim){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("absen",new String[]{"*"},"nim = ?",new String[]{nim},null,null,null);
        return cursor;
    }
    public boolean updateAbsen(String nim,String status_absen){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status_hadir",status_absen);
        sqLiteDatabase.update("absen",contentValues,"nim = ?",new String[]{nim});
        return  true;
    }
    public Cursor getDataLocal(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query("absen",new String[]{"*"},"status = ?",new String[]{"p"},null,null,null);
        return cursor;
    }
    public Cursor all(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("absen",new String[]{"*"},"status = ?",new String[]{"p"},null,null,null);
        return cursor;
    }
    public boolean updateStatus(String nim){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status","s");
        sqLiteDatabase.update("absen",contentValues,"nim = ? and status = ?",new String[]{nim,"p"});
        return true;

    }
    public Cursor allAgain(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("absen",new String[]{"*"},null,null,null,null,null);
        return cursor;
    }
}
