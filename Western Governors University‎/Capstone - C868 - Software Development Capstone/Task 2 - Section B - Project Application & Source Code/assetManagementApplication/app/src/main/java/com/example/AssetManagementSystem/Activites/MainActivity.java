package com.example.AssetManagementSystem.Activites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyVendorActivity;
import com.example.AssetManagementSystem.fragments.WarrantyFragment;
import com.example.AssetManagementSystem.fragments.ProductFragment;
import com.example.AssetManagementSystem.fragments.HomeFragment;
import com.example.AssetManagementSystem.fragments.VendorFragment;
import com.example.AssetManagementSystem.fragments.NoteFragment;
import com.example.AssetManagementSystem.fragments.CampusFragment;
import com.example.degreetracker.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String passedFragmentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        passedFragmentState = intent.getStringExtra(ModifyVendorActivity.FRAGMENT_STRING);
        if (passedFragmentState != null) {
            switch (passedFragmentState) {
                case "nav_term":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CampusFragment()).commit();
                    break;
                case "nav_course":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment()).commit();
                    break;
                case "nav_assessment":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WarrantyFragment()).commit();
                    break;
                case "nav_mentor":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VendorFragment()).commit();
                    break;
                case "nav_note":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NoteFragment()).commit();
                    break;
            }
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_campus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CampusFragment()).commit();
                break;
            case R.id.nav_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment()).commit();
                break;
            case R.id.nav_warranty:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WarrantyFragment()).commit();
                break;
            case R.id.nav_vendor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VendorFragment()).commit();
                break;
            case R.id.nav_note:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NoteFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
