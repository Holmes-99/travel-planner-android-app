package com.example.a1231279_1230239_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        drawerLayout = findViewById(R.id.admin_drawer_layout);
        navigationView = findViewById(R.id.admin_navigation_view);

        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Panel");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // load  fragment
        loadFragment(new AdminUsersFragment());

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment fragment = null;
                        int id = item.getItemId();

                        if (id == R.id.admin_nav_users) {
                            fragment = new AdminUsersFragment();
                        } else if (id == R.id.admin_nav_trips) {
                            fragment = new AdminTripsFragment();
                        } else if (id == R.id.admin_nav_add_trip) {
                            fragment = new AdminAddTripFragment();
                        } else if (id == R.id.admin_nav_reservations) {
                            fragment = new AdminReservationsFragment();
                        } else if (id == R.id.admin_nav_logout) {
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
                .replace(R.id.admin_fragment_container, fragment)
                .commit();
    }

    private void logout() {
        SharedPreManager prefs = SharedPreManager.getInstance(this);
        prefs.writeInt("loggedInUserId", -1);
        prefs.writeString("LoggedInRole", "");
        prefs.writeString("LoggedInFirstName", "");
        prefs.writeString("LoggedInEmail", "");
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }
}