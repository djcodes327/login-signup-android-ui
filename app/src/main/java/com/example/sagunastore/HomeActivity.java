package com.example.sagunastore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.sagunastore.ui.homeFragment;
import com.example.sagunastore.ui.websiteFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //For Custom Toolbar
    Toolbar toolbar;

    //For navigation of Header and Menus
    NavigationView navigationView;

    //Used For Fragment Handling
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout parentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Drawer Layout (A.K.A : Main Activity)
        drawerLayout = findViewById(R.id.drawer);

        //Custom Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation bar for Drawer
        navigationView = findViewById(R.id.nav_view);
        //As we have implemented the NavigationView.OnNavigationItemSelectedListener we need to set listener
        navigationView.setNavigationItemSelectedListener(this);

        //This - activity and we need to set the drawer layout and then toolbar for that and 2strings for opening and closing of the drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        //Pass toggle for action
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //enabling hamburger sign
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Fragment Manager (Specifically For Loading Fragment in Main Activity

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //For Loading The Fragment using content_main's Container Layout and set fragment you want
        fragmentTransaction.add(R.id.container_fragment,new homeFragment());
        // For Loading we need to commit
        fragmentTransaction.commit();
        //To Set Home Menu as Default Loaded Fragment
        navigationView.getMenu().getItem(0).setCheckable(true);

       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_my_orders, R.id.nav_rewards,R.id.nav_cart,R.id.nav_wishlist,R.id.nav_my_account,R.id.nav_website,R.id.nav_contact_us,R.id.nav_logout)
                .setDrawerLayout(drawerLayout)
                .build();
        */

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.main_search_icon) {

        }
        if(item.getItemId() == R.id.main_notification) {

        }
        if(item.getItemId() == R.id.main_cart_icon) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.nav_home) {
            setFragment(new homeFragment());
        }
        if(item.getItemId() == R.id.nav_my_orders) {
            setFragment(new MyOrdersFragment());
        }
        if(item.getItemId() == R.id.nav_rewards) {

        }
        if(item.getItemId() == R.id.nav_cart) {

        }
        if(item.getItemId() == R.id.nav_wishlist) {

        }
        if(item.getItemId() == R.id.nav_my_account) {

        }
        if(item.getItemId() == R.id.nav_website) {
            setFragment(new websiteFragment());
        }
        if(item.getItemId() == R.id.nav_contact_us) {

        }
        if(item.getItemId() == R.id.nav_logout) {

        }
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //For Loading The Fragment using content_main's Container Layout and set fragment you want
        fragmentTransaction.replace(R.id.container_fragment,fragment);
        // For Loading we need to commit
        fragmentTransaction.commit();
    }

   /* public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}




