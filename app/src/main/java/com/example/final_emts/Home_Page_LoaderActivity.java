package com.example.final_emts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_Page_LoaderActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout; // Use the class-level variable
    FirebaseUser user;
    FirebaseAuth auth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_loader);

        drawerLayout = findViewById(R.id.drawer_layout); // Use the class-level variable

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_about) {
                Intent intent = new Intent(Home_Page_LoaderActivity.this, About_Activity.class);
                startActivity(intent);
            } else if (id == R.id.nav_developer) {
                Intent intent = new Intent(Home_Page_LoaderActivity.this, Developer_Activity.class);
                startActivity(intent);

            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut(); // Sign out the user
                Intent intent = new Intent(Home_Page_LoaderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return true; // Return true to indicate the item click is consumed
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        TabLayout tablayout = findViewById(R.id.tabMode);
        ViewPager viewPager = findViewById(R.id.viewPager);
        vpAdapter viewPagerAdapter = new vpAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        String userEmail = getCurrentUserEmail();
        String encodedEmail = userEmail.replace(".", ",");

        if (user == null) {
            Intent intent = new Intent(Home_Page_LoaderActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (userEmail.equals("estatem.cst@rub.edu.bt")) {
            viewPagerAdapter.addFragment(new EM_HomeFragment(), "Home");
            viewPagerAdapter.addFragment(new AddFragment(), "Add");
            viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
            viewPager.setAdapter(viewPagerAdapter);
            tablayout.setupWithViewPager(viewPager);
        } else if (userEmail != null && userEmail.equals("maintenanceteam.cst@rub.edu.bt")) {
            viewPagerAdapter.addFragment(new MaintenanceTeamHomeFragment(), "Home");
            viewPagerAdapter.addFragment(new AddFragment(), "Add");
            viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
            viewPager.setAdapter(viewPagerAdapter);
        } else if (userEmail != null && userEmail.equals("sso.cst@rub.edu.bt")) {
            viewPagerAdapter.addFragment(new SSOHomePageFragment(), "Home");
            viewPagerAdapter.addFragment(new AddFragment(), "Add");
            viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
            viewPager.setAdapter(viewPagerAdapter);
            tablayout.setupWithViewPager(viewPager);
        } else if (userEmail != null && userEmail.equals("president.cst@rub.edu.bt")) {
            viewPagerAdapter.addFragment(new PresedentHomeFragment(), "Home");
            viewPagerAdapter.addFragment(new AddFragment(), "Add");
            viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
            viewPager.setAdapter(viewPagerAdapter);
            tablayout.setupWithViewPager(viewPager);
        } else {
            viewPagerAdapter.addFragment(new UserFragment(), "Home");
            viewPagerAdapter.addFragment(new AddFragment(), "Add");
            viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
            viewPager.setAdapter(viewPagerAdapter);
            tablayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCurrentUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        }
        return null;
    }
}
