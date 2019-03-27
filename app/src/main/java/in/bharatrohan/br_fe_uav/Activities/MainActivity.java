package in.bharatrohan.br_fe_uav.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView userAvatar, farmers, visits, repo, money;
    private MaterialSpinner navHelpSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CheckInternet(this).checkConnection();
        init();

        navHelpSpinner.setOnItemSelectedListener((view1, position, id, item) -> {
            if (position == 1) {
                startActivity(new Intent(this, FeedbackComplaints.class));
            } else if (position == 2) {
                startActivity(new Intent(this, ImpContacts.class));
            }

        });

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


    }


    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Toast.makeText(MainActivity.this, new PrefManager(MainActivity.this).getName(), Toast.LENGTH_SHORT).show();
        farmers = findViewById(R.id.farmers);
        visits = findViewById(R.id.myVisits);
        repo = findViewById(R.id.repo);
        money = findViewById(R.id.money);

        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.tvUserEmail);
        userAvatar = navigationView.getHeaderView(0).findViewById(R.id.userPic);

        if (!new PrefManager(MainActivity.this).getAvatar().equals("")) {
            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userAvatar, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().into(userAvatar);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).into(userAvatar);
        }

        userName.setText(new PrefManager(MainActivity.this).getName());
        userEmail.setText(new PrefManager(MainActivity.this).getEmail());

        navHelpSpinner = (MaterialSpinner) navigationView.getMenu().findItem(R.id.nav_help).getActionView();

        ArrayList<String> helpList = new ArrayList<>();
        helpList.add("-Help and Support-");
        helpList.add("Feedback and Complaints");
        helpList.add("Imp Contacts");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, helpList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        navHelpSpinner.setAdapter(adapter1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, UAVProfile.class));
        } else if (id == R.id.nav_pass) {
            startActivity(new Intent(this, ChangePassword.class));
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_tc) {

        } else if (id == R.id.logout) {
            new PrefManager(MainActivity.this).saveLoginDetails("", "", "");
            new PrefManager(MainActivity.this).saveToken("");
            new PrefManager(MainActivity.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
            new PrefManager(MainActivity.this).saveUserType("");
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        } else if (id == R.id.stop_service) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!new PrefManager(MainActivity.this).getAvatar().equals("")) {
            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userAvatar, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().into(userAvatar);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).into(userAvatar);
        }
    }
}
