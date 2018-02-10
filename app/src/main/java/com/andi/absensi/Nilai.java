package com.andi.absensi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;

import java.util.ArrayList;

public class Nilai extends android.app.Fragment{
    public Nilai(){}
    View view,absen;
    TextView mapel,id_mapel_;
    Session session;
    Config config;
    Absen db_absen;
    String[] id_list_siswa;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    URL link = null;
    int sekarang = 0 ;
    Toolbar toolbar;
    Fragment fragment = null;
    MenuItem menuItem;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        view = (View) inflater.inflate(R.layout.fragment_nilai, container, false);
        mapel = (TextView) view.findViewById(R.id.nama_mapel);
        id_mapel_ = (TextView) view.findViewById(R.id.id_mapel);
        final LinearLayout linearLayout  = (LinearLayout) view.findViewById(R.id.btnKirim);
        progressBar = (ProgressBar) view.findViewById(R.id.progres);
        db_absen = new Absen(getActivity());
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.daftar_siswa);
        session = new Session(getActivity().getApplicationContext());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id_menu = item.getItemId();
                if (id_menu == R.id.send){
                    menuItem = item;

                    new KirimAbsen().execute(id_mapel_.getText().toString());
                }else if (id_menu == R.id.logout){
                    session.logout();
                }
                return false;
            }
        });
        new GetSiswa().execute(session.getSession(session.Key_id));
        getActivity().setTitle("Absesnsi Siswa");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.send,menu);
        menuItem = menu.findItem(R.id.send);
       menuItem.setVisible(false);

    }
    class KirimAbsen extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),null,"Mengirin Absen",false);
        }

        @Override
        protected String doInBackground(String... Strings) {
            Absen db = new Absen(getActivity());
            Cursor kk = db.all();
            Integer i = 0;

            JSONObject jsonObject= null;
            JSONArray jsonArray = new JSONArray() ;
            id_list_siswa = new String[kk.getCount()];
            while (kk.moveToNext()){
                try {
                   JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id_siswa",kk.getString(1));
                    jsonObject1.put("nama_siswa",kk.getString(2));
                    jsonObject1.put("tgl",kk.getString(3));
                    jsonObject1.put("jam",kk.getString(4));
                    jsonObject1.put("status",kk.getString(5));
                    jsonObject1.put("id_mapel",id_mapel_.getText().toString());
                    jsonArray.put(jsonObject1);
                    id_list_siswa[i] = kk.getString(1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
            JSONObject jsonObject1 = new JSONObject();
            JSONArray jsonArray1 = new JSONArray();
            JSONObject jo2 = new JSONObject();
            try {
                jo2.put("data",jsonArray);
                jsonArray1.put(0,jo2);
                jsonObject1.put("value",jsonArray1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Request request = new Request();
            return request.sendPostAbsen(Config.SAVE_ABSEN,jsonObject1.toString());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s == null || s.equals("")){
                Toast.makeText(getActivity().getApplicationContext(),"Koneksi Gagal",Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.optJSONArray("value");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    if (jsonObject1.getString("respon") == "true"){
                        new NextAction().execute();
                        Snackbar.make(getView(),"Berhasil mengirim Absen",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        fragment = new Welcome();
                        fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frm,fragment).commit();
                        menuItem.setVisible(false);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }
    }
    class NextAction extends AsyncTask<Void,Void,Void>{
        Absen absen = new Absen(getActivity());
        Integer o;
        @Override
        protected Void doInBackground(Void... voids) {
            for (o = 0; o < id_list_siswa.length; o++){
                absen.updateStatus(id_list_siswa[o]);
            }
            absen.deleteData();
            return null;
        }
    }

    class GetSiswa extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String hasil;
            String s = strings[0];
            Request request = new Request();
            hasil = request.getResponAmbilSiswa(s);
            return hasil;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (s == null){
                Toast.makeText(getActivity().getApplicationContext(),"Koneksi Gagal",Toast.LENGTH_SHORT).show();
            }
            else {
                jSon(s);
            }
        }
    }
    public void jSon(String json){
        String hasil = null;
        String id_mapel = null;
        try {

           JSONObject jsonObject = new JSONObject(json);
           JSONArray jsonArray = jsonObject.getJSONArray("val");
           JSONObject joVal = jsonArray.getJSONObject(0);
           JSONArray jaPelajaran = joVal.getJSONArray("pelajaran");
           JSONObject jsonObject2 = jaPelajaran.getJSONObject(0);
           hasil = "Pelajaran : "+jsonObject2.getString("id_mapel")+ " - " +jsonObject2.getString("nama_mapel");
           id_mapel = jsonObject2.getString("id_mapel");
           final JSONArray jSiswa = joVal.getJSONArray("siswa");
           ArrayList<String> nim = new ArrayList<>();
           ArrayList<String> nama = new ArrayList<>();
           ArrayList<String> foto = new ArrayList<>();

           for (int i =0; i<jSiswa.length(); i++){
               JSONObject allSiswa = jSiswa.getJSONObject(i);
               nim.add(allSiswa.getString("id_siswa"));
               nama.add(allSiswa.getString("nama_siswa"));
               foto.add(allSiswa.getString("foto"));
           }
            id_mapel_.setText(id_mapel);
            mapel.setText(hasil);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new Generate(nim,nama,foto,id_mapel,toolbar,menuItem);

            recyclerView.setAdapter(adapter);
       }catch (JSONException b){

       }
    }
}
