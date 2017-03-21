package me.bayupaoh.mudah;

import android.app.Dialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.bayupaoh.mudah.Fragment.HalalFragment;
import me.bayupaoh.mudah.Fragment.KursAsingFragment;
import me.bayupaoh.mudah.Fragment.TagihanListrikFragment;

public class MainActivity extends AppCompatActivity {
    
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView txtToolbar;
    ImageView imgToolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        declarationWidget();
        setToolbar();
        settingNavBar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void settingNavBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        txtToolbar.setText("Produk Halal MUI");
        imgToolbar.setImageResource(R.drawable.halal_sign);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container_body, new HalalFragment()).commit();


        navigationView.setNavigationItemSelectedListener(navItemSelect);
    }

    private void declarationWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.fragment_navigation_drawer);
        imgToolbar = (ImageView) findViewById(R.id.logo_in_toolbar);
        txtToolbar = (TextView) findViewById(R.id.title_toolbar);
    }

    NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            menuItem.setCheckable(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (menuItem.getItemId()){
                case R.id.id_menu_halal:
                    txtToolbar.setText("Produk Halal MUI");
                    imgToolbar.setImageResource(R.drawable.halal_sign);
                    fragmentManager.beginTransaction().replace(R.id.container_body, new HalalFragment()).commit();
                    return true;
                case R.id.id_menu_kurs:
                    txtToolbar.setText("Kurs Mata Asing");
                    imgToolbar.setImageResource(R.drawable.change);
                    fragmentManager.beginTransaction().replace(R.id.container_body, new KursAsingFragment()).commit();
                    return true;
                case R.id.id_menu_tagihan:
                    txtToolbar.setText("Tagihan PLN");
                    imgToolbar.setImageResource(R.drawable.idea);
                    fragmentManager.beginTransaction().replace(R.id.container_body, new TagihanListrikFragment()).commit();
                    return true;
                case R.id.id_menu_about:
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.about_me_alert);
                    dialog.setTitle("About Me");
                    dialog.setCancelable(true);
                    dialog.show();
                    return true;
                default:
                    return true;
            }
        }
    };
}
