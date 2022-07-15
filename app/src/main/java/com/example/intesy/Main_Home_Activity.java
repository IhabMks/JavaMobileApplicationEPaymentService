package com.example.intesy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class Main_Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer_layout;
    public static NavigationView nav_view;
    private TextView nav_header_profile_name;
    private Button nav_log_out;
    private String user_username, phone_connected_with;
    private DataBaseHelper db;
    private int user_adsl_day, user_line_day;
    private long mBackPressed;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home_activity);

        phone_connected_with = getIntent().getExtras().getString("phone");
        get_current_data();
        if (user_adsl_day != 0){
            db = new DataBaseHelper(this);
            db.update_adsl_days_remaining(phone_connected_with);
        }
        if (user_line_day != 0){
            db = new DataBaseHelper(this);
            db.update_line_days_remaining(phone_connected_with);
        }


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_2, new actual_home_fragment(),"Home_frag").commit();
        //menu_nav
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);


        nav_view.setNavigationItemSelectedListener(this);
        nav_view.setCheckedItem(R.id.nav_home);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_header_profile_name = nav_view.getHeaderView(0).findViewById(R.id.nav_header_profile_name);
        nav_header_profile_name.setText(user_username);

        nav_log_out = findViewById(R.id.nav_log_out);
        nav_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(login_fragment.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(login_fragment.SAVE_CREDENTIAL, false);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        actual_home_fragment actualHomeFragment = (actual_home_fragment) getSupportFragmentManager().findFragmentByTag("Home_frag");
        profile_frag profileFrag = (profile_frag) getSupportFragmentManager().findFragmentByTag("Profile_frag");
        option_selection_frag optionSelectionFrag = (option_selection_frag) getSupportFragmentManager().findFragmentByTag("Option_selection_frag");
        news_frag newsFrag = (news_frag) getSupportFragmentManager().findFragmentByTag("News_frag");
        history_frag historyFrag = (history_frag) getSupportFragmentManager().findFragmentByTag("History_frag");
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                if (actualHomeFragment != null && !actualHomeFragment.isVisible())
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new actual_home_fragment(),"Home_frag")
                        //.addToBackStack(null)
                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;

            case R.id.nav_profile:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new profile_frag(),"Profile_frag")
                        //.addToBackStack(null)
                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;

            case R.id.nav_e_payment:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new option_selection_frag(),"Option_selection_frag")
                        //.addToBackStack(null)
                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;

            case R.id.nav_news:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new news_frag(),"News_frag")
                        //.addToBackStack(null)
                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;

            case R.id.nav_history:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new history_frag(),"History_frag")
                        //.addToBackStack(null)
                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;

            case R.id.nav_contact_us:
                String recipientList = "ihab.ozark@gmail.com";
                String[] recipients = recipientList.split(",");

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an email client"));
                break;

            case R.id.nav_about_us:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new about_us_frag())
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;

        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 0) {
                if (mBackPressed + 3000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    Toast.makeText(this, "Press again to exit.", Toast.LENGTH_SHORT).show();
                }

                mBackPressed = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }

    }

    public void get_current_data() {

        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.get_credential(phone_connected_with);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data found for this user", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {

            user_username = cursor.getString(cursor.getColumnIndex(Table_list.client_table.username_col));
            user_adsl_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.adsl_day_col));
            user_line_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.line_day_col));
        }

    }

}
