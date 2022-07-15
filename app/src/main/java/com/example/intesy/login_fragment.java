package com.example.intesy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class login_fragment extends Fragment {

    @Nullable
    private ImageButton ps_reveal,back_button;
    private EditText number_field,password_field;
    private LinearLayout new_user_ll_login;
    private DataBaseHelper db;

    public static final String SHARED_PREFS = "sharedPrefs" ;
    public static final String USER_PHONE = "userPhone";
    public static final String SAVE_CREDENTIAL = "save_user_credential";
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);
        number_field=v.findViewById(R.id.number_field);
        password_field = v.findViewById(R.id.password_field);
        ps_reveal=v.findViewById(R.id.ps_reveal);
        password_field=v.findViewById(R.id.password_field);
        back_button = v.findViewById(R.id.back_button);
        db = new DataBaseHelper(getActivity());

        new_user_ll_login= v.findViewById(R.id.new_user_ll_login);
        Animation user_ll_blink= AnimationUtils.loadAnimation(getContext(),R.anim.blink);
        new_user_ll_login.startAnimation(user_ll_blink);

        ps_reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int password_field_type = password_field.getInputType() - 1;
                if (password_field_type == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    password_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    password_field.setTypeface(number_field.getTypeface());
                    ps_reveal.setImageResource(R.drawable.ps_visible);
                    password_field.setSelection(password_field.getText().length());
                } else if (password_field_type == InputType.TYPE_TEXT_VARIATION_NORMAL) {
                    password_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password_field.setTypeface(number_field.getTypeface());
                    ps_reveal.setImageResource(R.drawable.ps_invisible);
                    password_field.setSelection(password_field.getText().length());
                }

            }
        });


        /*-----------------------------------------------------------------Button action-----------------------------------------------------------------*/
        v.findViewById(R.id.login_button_login_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection();

            }
        });

        v.findViewById(R.id.register_button_login_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new sign_up_fragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });


        v.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new home_fragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });
        /*-----------------------------------------------------------------Button action-----------------------------------------------------------------*/


        return v;
    }
    public void Connection(){
        String phone_value = number_field.getText().toString();
        String password_value = password_field.getText().toString();


        if (phone_value.trim().isEmpty()) {
            number_field.setError("Empty number");
            number_field.requestFocus();
            return;
        }
        if (password_value.trim().isEmpty()) {
            password_field.setError("Empty password");
            password_field.requestFocus();
            return;
        }

        boolean check_credential = db.check_credential(phone_value,password_value);
        if (check_credential){
            Bundle credential_bundle = new Bundle();
            credential_bundle.putString("phone",phone_value);
            Intent i = new Intent(getActivity(), Main_Home_Activity.class);
            i.putExtras(credential_bundle);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(USER_PHONE,phone_value);
            editor.putBoolean(SAVE_CREDENTIAL,true);
            editor.apply();

            startActivity(i);
            getActivity().finish();
        }else{
            Toast.makeText(getActivity(), "Wrong Number or Password", Toast.LENGTH_SHORT).show();
        }
    }

}
