package com.andi.absensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by andi on 1/23/18.
 */

public class Generate extends RecyclerView.Adapter<Generate.ViewHolder>{
    private ArrayList<String> nim,nama,foto;
    private String id_pelajaran;
    Context context;
    Toolbar toolbar;
    MenuItem menuItem;
    Integer hasil;

    public Generate(ArrayList<String> nim, ArrayList<String> nama, ArrayList<String> foto, String id_mapel, Toolbar toolbar, MenuItem menuItem) {
        this.nim = nim;
        this.nama = nama;
        this.foto = foto;
        this.id_pelajaran = id_mapel;
        this.toolbar = toolbar;
        this.menuItem = menuItem;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id_siswa;
        public TextView nama_siswa;
        public ImageView foto;
        public LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            id_siswa = (TextView) itemView.findViewById(R.id.id_siswa);
            nama_siswa = (TextView) itemView.findViewById(R.id.namaSiswa);
            foto = (ImageView) itemView.findViewById(R.id.imgSiswa);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Custom custom = new Custom();
                   hasil = custom.showDialog(id_siswa.getText().toString(),nama_siswa.getText().toString(),custom.makeDate(),custom.makeTime(),id_pelajaran,itemView,context);
//                    if (getItemCount() == o){
                        menuItem.setVisible(true);
//                    }
                    Toast.makeText(context,hasil.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.siswa,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nim = this.nim.get(position);
        String nama = this.nama.get(position);
        String foto = this.foto.get(position);
        holder.id_siswa.setText(nim);
        holder.nama_siswa.setText(nama);
        Glide.with(context).load(Config.link_foto+foto).into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return nim.size();
    }


}
