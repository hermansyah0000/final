package com.andi.absensi;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Session session;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    URL link = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("App Absensi");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new Session(getApplicationContext());
        if (!session.checkSession()){
            finish();
        }
        if (!session.getSession(session.Key_status).equals("Guru")){

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.absen).setVisible(false);
            menu.findItem(R.id.listabsen).setVisible(false);
            Toast.makeText(getApplicationContext(),"Not Module For You",Toast.LENGTH_LONG).show();
        }
        fragment = new Welcome();
        newPage(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        makeID();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Absen absen = new Absen(getApplicationContext());
//        absen.deleteData();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
      //  getMenuInflater().inflate(R.menu.send,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.logout){
            session.logout();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.absen) {
            fragment = new Nilai();
            newPage(fragment);
        }
// else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void makeID(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView name = hView.findViewById(R.id.nama);
        name.setText(session.getSession(session.Key_nama));
        TextView id = (TextView) hView.findViewById(R.id.id_);
        id.setText(session.getSession(session.Key_id));
        ImageView imageView = (ImageView) hView.findViewById(R.id.foto_user);
        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(Config.link_foto+session.getSession(session.Key_foto));
    }
    private void newPage(Fragment fragment){
        fragmentManager  = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frm,fragment).commit();
    }
}
