package com.plug.mod3class04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.plug.mod3class04.fragments.DosFragment;
import com.plug.mod3class04.fragments.UnoFragment;

import org.w3c.dom.Text;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btnFragment2;
    private SimpleDraweeView sdvImagen;
    private TextView etNombre,etApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        CoordinatorLayout coordinator=(CoordinatorLayout)findViewById(R.id.view_coordinator);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view=navigationView.getHeaderView(0);
        SimpleDraweeView sdvImagen=(SimpleDraweeView)view.findViewById(R.id.sdvImagen);
        TextView etNombre=(TextView)view.findViewById(R.id.tvNombre);
        TextView etApellido=(TextView)view.findViewById(R.id.tvApellido);


        SharedPreferences sp=
                getSharedPreferences("facebook",MODE_PRIVATE);


        //String rutaImagen=getIntent().getStringExtra("imagen");
        //String nombre=getIntent().getStringExtra("nombre");
        //String apellido=getIntent().getStringExtra("apellido");
        //sdvImagen.setImageURI(Uri.parse(rutaImagen));


        String rutaImagen=sp.getString("fotoruta","");
        String nombre=sp.getString("nombre","");
        String apellido=sp.getString("apellido","");
        sdvImagen.setImageURI(Uri.parse(rutaImagen));



        etNombre.setText(nombre);
        etApellido.setText(apellido);



        btnFragment2=(Button)coordinator.findViewById(R.id.btnFragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "Good Madafakas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

        return super.onOptionsItemSelected(item);
    }

    //Creamos metodo para pasar datos de un fragment a otro fragment
    public void pasarDatos(String dato){
        DosFragment fragment=new DosFragment();
        //Bundle=intent para fragments
        Bundle bundle=new Bundle();
        bundle.putString("dato",dato);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor,fragment)
                .commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;

        if (id == R.id.nav_camera) {
            fragment=new UnoFragment();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragment=new DosFragment();

        } else if (id == R.id.nav_slideshow) {
            SharedPreferences.Editor sp=getSharedPreferences("facebook",MODE_PRIVATE).edit();
            sp.clear();
            sp.apply();
            Intent intent2=new Intent(MenuActivity.this,MainActivity.class);
            startActivity(intent2);
            LoginManager.getInstance().logOut();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {


        }
        if (fragment!=null){
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor,fragment)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
