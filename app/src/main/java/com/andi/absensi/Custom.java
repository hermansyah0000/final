package com.andi.absensi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Inflater;

/**
 * Created by andi on 1/23/18.
 */

public class Custom {

    Integer rya = 0;
    String id_,temp;
    public String makeTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s");
        String jam = simpleDateFormat.format(calendar.getTime());
        return jam;
    }

    public String makeDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        return tgl;
    }
    public Integer showDialog(final String id, final String nama, final String tgl, final String jam, final String id_mapel, View view, final Context context){
        final ImageView fo = (ImageView) view.findViewById(R.id.status);
        final Integer[] idFoto = {null};
        final Absen absen = new Absen(context);



        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Status Kehadiran");
        alert.setMessage("Status Kehadiran : "+nama).setCancelable(true).setPositiveButton("H A D I R", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Cursor cekData = absen.showAbsen(id);
                    cekData.moveToNext();
                    if (cekData.getCount() == 0){
                        boolean work = absen.addData(id,nama,tgl,jam,"H",id_mapel);
                        if (work){
                            fo.setImageResource(R.drawable.hadir);
                            rya++;
                        }
                    }
                    else {
                        if (cekData.getString(5) != "H"){
                            if(absen.updateAbsen(cekData.getString(1),"H")){
                                fo.setImageResource(R.drawable.hadir);
                            }
                        }
                    }

                }catch (Exception b){
                    Toast.makeText(context,b.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("A L F A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    Cursor cekData = absen.showAbsen(id);
                    cekData.moveToNext();
                    if (cekData.getCount() == 0){
                        boolean work = absen.addData(id,nama,tgl,jam,"H",id_mapel);
                        if (work){
//                            Toast.makeText(getActivity().getApplicationContext(),"Berhasil Absen",Toast.LENGTH_LONG).show();
                            fo.setImageResource(R.drawable.alfa);
                            rya++;
                        }
                    }
                    else {
                        if (cekData.getString(5) != "A"){
//                            Toast.makeText(getActivity().getApplicationContext(),"Status Berubah : Hadir",Toast.LENGTH_LONG).show();

                            if(absen.updateAbsen(cekData.getString(1),"A")){
                                fo.setImageResource(R.drawable.alfa);
                            }
                        }
                    }

                }catch (Exception b){
                    Toast.makeText(context,b.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        alert.create();
        alert.show();

        return rya;
    }
}
