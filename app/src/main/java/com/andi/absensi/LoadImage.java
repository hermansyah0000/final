package com.andi.absensi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by andi on 12/21/17.
 */

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public LoadImage(ImageView imgImageView){

        bmImage = imgImageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String link = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(link).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);


        }catch (Exception e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap hasil){
        bmImage.setImageBitmap(hasil);
    }
}
