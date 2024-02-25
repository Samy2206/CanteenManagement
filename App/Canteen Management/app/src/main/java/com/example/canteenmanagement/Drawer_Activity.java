package com.example.canteenmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Drawer_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationview);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);

        loadFragment(new MenuFragment(),true);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Menu");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.admin)
                {
                    startActivity(new Intent(Drawer_Activity.this, AdminActivity.class));
                }else if (id==R.id.contact)
                {
                    startActivity(new Intent(Drawer_Activity.this, ContactActivity.class));
                }else if(id==R.id.feedback)
                {
                    startActivity(new Intent(Drawer_Activity.this, FeedbackActivity.class));
                }else
                {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Drawer_Activity.this, Login_Register.class));
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        setProfile();

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.nav_menu)
                {
                    loadFragment(new MenuFragment(),false);
                    setSupportActionBar(toolbar);
                    toolbar.setTitle("Menu");
                }
                else if (id==R.id.nav_billing)
                {
                    loadFragment(new BillingFragment(),false);
                    setSupportActionBar(toolbar);
                    toolbar.setTitle("Billing");
                }else {
                    loadFragment(new DashboardFragment(),false);
                    setSupportActionBar(toolbar);
                    toolbar.setTitle("Dahsboard");
                }
                return true;
            }
        });


    }

    private void setProfile() {

        View view = navigationView.getHeaderView(0);

    }

    private void loadFragment(Fragment fragment,boolean b)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(b)
            ft.add(R.id.containerX,fragment);
        else
            ft.replace(R.id.containerX,fragment);

        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            AlertDialog exitAlert = new AlertDialog.Builder(Drawer_Activity.this)

                    .setTitle("Exit")
                    .setMessage("Do you want to exit")
                    .setIcon(R.drawable.exit_app_icon)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Drawer_Activity.super.onBackPressed();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }
}