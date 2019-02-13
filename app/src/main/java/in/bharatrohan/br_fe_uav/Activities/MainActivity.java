package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView farmers, visits, repo, money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        farmers.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Farmers.class));
        });

        visits.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MyVisits.class));
        });

        repo.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "This feature is coming soon!!", Toast.LENGTH_SHORT).show();
        });

        money.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "This feature is coming soon!!", Toast.LENGTH_SHORT).show();
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void init() {
        //Toast.makeText(MainActivity.this, new PrefManager(MainActivity.this).getName(), Toast.LENGTH_SHORT).show();
        farmers = findViewById(R.id.farmers);
        visits = findViewById(R.id.myVisits);
        repo = findViewById(R.id.repo);
        money = findViewById(R.id.money);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.logout) {
            new PrefManager(MainActivity.this).saveLoginDetails("", "", "");
            new PrefManager(MainActivity.this).saveToken("");
            new PrefManager(MainActivity.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
            new PrefManager(MainActivity.this).saveUserType("");
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
