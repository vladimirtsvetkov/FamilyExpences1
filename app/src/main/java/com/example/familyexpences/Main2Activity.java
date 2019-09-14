package com.example.familyexpences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.familyexpences.Constants.Constants;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DashboardFragment.OnFragmentInteractionListener {


    NavigationView mNavigationView;
    View mHeaderView;

    TextView textViewUsername;
    TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Navigtion View
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // NavigationView Header
        mHeaderView =  mNavigationView.getHeaderView(0);

        // View
        textViewUsername = (TextView) mHeaderView.findViewById(R.id.textViewUsername);
        textViewEmail= (TextView) mHeaderView.findViewById(R.id.textViewEmail);

        String UserName = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserName = extras.getString("username");
            //The key argument here must match that used in the other activity
        }
        // Set username & email
        textViewUsername.setText(UserName);
        textViewEmail.setText(UserName + "@email.com");

        mNavigationView.setNavigationItemSelectedListener(this);
       // Window w = getWindow();
        //w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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

        getMenuInflater().inflate(R.menu.main2, menu);

        //Add Dashboard on login

        Fragment fragment = new DashboardFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.placeholder, fragment);
        transaction.commit();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;


        if (id == R.id.nav_dashboard) {
            fragment  = new DashboardFragment();

        }else if(id==R.id.nav_expenses)
        {   fragment = new ExpensesFragment();

        } else if (id == R.id.nav_categories) {
            fragment = new CategoriesFragment();

        } else if (id == R.id.nav_members) {
            fragment= new MembersFragment();

        } else if (id == R.id.nav_statistics) {
            fragment  = new StatisticsFragment();

        } else if(id == R.id.nav_reports){

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_exit) {
            SharedPreferences sp = getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
            sp.edit().putString(Constants.LOGGED_USER, "").apply();
            Intent intent = new Intent(Main2Activity.this,LoginActivity.class);
            startActivity(intent);
        } else if(id == R.id.nav_about){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()){
                        new AlertDialog.Builder(Main2Activity.this)
                                .setTitle("Information")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setMessage("This is a university project. \n" +
                                        "This is the possession of Vladimir and Deniz. " +
                                        "All rights reserved ®")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Whatever...
                                    }
                                }).show();
                    }
                }
            });
        }

        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.placeholder, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
