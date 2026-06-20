package com.example.a1231279_1230239_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    ActionBarDrawerToggle t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(t);
        t.syncState();

        //user info

        SharedPreManager sharedPreManager = SharedPreManager.getInstance(this);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewName = headerView.findViewById(R.id.textView_navName);
        TextView textViewEmail = headerView.findViewById(R.id.textView_navEmail);
        textViewName.setText(sharedPreManager.readString("LoggedInFirstName",""));
        textViewEmail.setText(sharedPreManager.readString("LoggedInEmail",""));

        //home fragment
        loadFragment(new HomeFragment());

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment fragment = null;
                        int id = item.getItemId();

                        if (id == R.id.nav_home) {
                            fragment = new HomeFragment();
                       /* } else if (id == R.id.nav_trips) {
                            fragment = new TripsFragment();
                        } else if (id == R.id.nav_reservations) {
                            fragment = new ReservationsFragment();
                        } else if (id == R.id.nav_favorites) {
                            fragment = new FavoritesFragment();
                        } else if (id == R.id.nav_special) {
                            fragment = new SpecialFragment();
                        } else if (id == R.id.nav_profile) {
                            fragment = new ProfileFragment();
                        } else if (id == R.id.nav_contact) {
                            fragment = new ContactFragment();*/ //still are not created
                        } else if (id == R.id.nav_logout) {
                            logout();
                            return true;
                        }

                        if (fragment != null) {
                            loadFragment(fragment);
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void logout() {
        SharedPreManager prefs = SharedPreManager.getInstance(this);
        prefs.writeInt("loggedInUserId", -1);
        prefs.writeString("LoggedInRole", "");
        prefs.writeString("LoggedInFirstName", "");
        prefs.writeString("LoggedInEmail", "");
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    }
