package com.example.intesy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {
    private String user_phone;
    private boolean save_user_credential;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, new splash_fragment()).commit();

        final SharedPreferences sharedPreferences = getSharedPreferences(login_fragment.SHARED_PREFS, MODE_PRIVATE);
        save_user_credential = sharedPreferences.getBoolean(login_fragment.SAVE_CREDENTIAL, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (save_user_credential) {
                    user_phone = sharedPreferences.getString(login_fragment.USER_PHONE, "");

                    Bundle credential_bundle = new Bundle();
                    credential_bundle.putString("phone", user_phone);
                    Intent i = new Intent(MainActivity.this, Main_Home_Activity.class);
                    i.putExtras(credential_bundle);

                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Happy to see you again!", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.fragment_container, new home_fragment()).commit();
                }

            }
        }, 3000);
    }


    @Override
    public void onBackPressed() {
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
