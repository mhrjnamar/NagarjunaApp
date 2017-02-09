package nagarjuna.com.nagarjunaapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import nagarjuna.com.nagarjunaapa.fragments.AboutUs;
import nagarjuna.com.nagarjunaapa.fragments.CollegeIntro;
import nagarjuna.com.nagarjunaapa.fragments.FeePayment;
import nagarjuna.com.nagarjunaapa.fragments.HomeFragment;
import nagarjuna.com.services.RegistrationIntentService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 0X18;
    public MenuItem calendar;
    public MenuItem feePayment;
    public MenuItem login;
    public HomeFragment homeFrag;
    NavigationView nav_drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    FragmentManager fm;
    SessionManager manager;
    Menu navMenu;
    private long toastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new SessionManager(MainActivity.this);

        //initialize views
        nav_drawer = (NavigationView) findViewById(R.id.nav_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nagarjuna College of IT");

        // set visibility of calender menu false if not logged in
        navMenu = (Menu) nav_drawer.getMenu();
        calendar = navMenu.findItem(R.id.calender);
        calendar.setVisible(true);
        feePayment = navMenu.findItem(R.id.calender);
        feePayment.setVisible(false);
        login = navMenu.findItem(R.id.login);

        //set actions when drawer is opened or closed
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //hide keyboard when drawer opened
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(drawerView.getApplicationWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //initialize fragment manager and replace the view(dummyLayout) in the mainactivity layout with HomeFragment() fragment
        fm = getSupportFragmentManager();
        homeFrag = new HomeFragment();
        fm.beginTransaction().replace(R.id.dummyLayout, homeFrag).addToBackStack(null).commit();
        nav_drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.home:
                        toolbar.setVisibility(View.VISIBLE);
                        getSupportActionBar().setTitle("Nagarjuna College of IT");
                        fm.beginTransaction().replace(R.id.dummyLayout, homeFrag).commit();
                        break;

                    case R.id.fee_payment:
                        toolbar.setVisibility(View.VISIBLE);
                        getSupportActionBar().setTitle("Fee Payment");
                        fm.beginTransaction().replace(R.id.dummyLayout, new FeePayment()).commit();
                        break;

                    case R.id.contact_us:
                        toolbar.setVisibility(View.VISIBLE);
                        getSupportActionBar().setTitle("About Us");
                        fm.beginTransaction().replace(R.id.dummyLayout, new AboutUs()).commit();
                        break;

                    case R.id.calender:
                        Intent i = new Intent(MainActivity.this, Calender_activity.class);
                        startActivity(i);

                        break;

                    case R.id.introduction:
                        toolbar.setVisibility(View.GONE);
                        fm.beginTransaction().replace(R.id.dummyLayout, new CollegeIntro()).commit();
                        break;

                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, NewSettings.class));
                        break;

                    case R.id.notifications:
                        startActivity(new Intent(MainActivity.this,Notifications.class));
                        break;

                    case R.id.login:
//                        homeFrag.tabCount();
                        if (!manager.isLoggedIn()) {
//                            calendar.setVisible(manager.isLoggedIn());
                            feePayment.setVisible(manager.isLoggedIn());
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            manager.logOutUser();
//                            calendar.setVisible(manager.isLoggedIn());
                            feePayment.setVisible(manager.isLoggedIn());
                        }

                    default:
                        break;
                }

                return true;
            }
        });

        drawerLayout.setDrawerListener(drawerToggle);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public Toolbar getCustomActionBar() {
        return toolbar;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        calendar.setVisible(manager.isLoggedIn());
        feePayment.setVisible(manager.isLoggedIn());
    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setTitle(manager.isLoggedIn() ? "Logout" : "Login");
//        calendar.setVisible(manager.isLoggedIn());
        feePayment.setVisible(manager.isLoggedIn());

    }

    @Override
    public void onBackPressed() {
        login.setTitle(manager.isLoggedIn() ? "Logout" : "Login");
//        calendar.setVisible(manager.isLoggedIn());
        feePayment.setVisible(manager.isLoggedIn());
        toolbar.setVisibility(View.VISIBLE);

        long time = System.currentTimeMillis();
        int accuringTime = 2000;
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        fm.beginTransaction().replace(R.id.dummyLayout, new HomeFragment()).commit();

        getSupportActionBar().setTitle("Nagarjuna College of IT");
        if (time > toastTime + accuringTime) {

            Toast.makeText(MainActivity.this, "Press again to exit", Toast.LENGTH_LONG).show();
        } else {
            MainActivity.this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        toastTime = time;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
