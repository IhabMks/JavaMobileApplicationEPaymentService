package com.example.intesy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class sign_up_fragment extends Fragment {
    @Nullable
    private ImageButton ps_reveal, client_code_info;
    private EditText username_field, phone_field, password_field, client_code_field;
    private Spinner account_type_spinner;
    private LinearLayout already_user_ll_register;
    private TextView steps_mod_1;
    private FirebaseAuth mAuth;
    private String CodeSent;
    private DataBaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fragment, container, false);

        username_field = v.findViewById(R.id.username_field);
        phone_field = v.findViewById(R.id.phone_field);
        client_code_field = v.findViewById(R.id.client_code_field);
        password_field = v.findViewById(R.id.password_field);
        account_type_spinner = v.findViewById(R.id.account_type_spinner);


        ps_reveal = v.findViewById(R.id.ps_reveal);
        client_code_info = v.findViewById(R.id.client_code_info);
        already_user_ll_register = v.findViewById(R.id.already_user_ll_register);

        db = new DataBaseHelper(getActivity());

        client_code_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Client code is located in top of your billing paper.", Toast.LENGTH_LONG).show();
            }
        });


        ps_reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int password_field_type = password_field.getInputType() - 1;
                if (password_field_type == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    password_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    password_field.setTypeface(phone_field.getTypeface());
                    ps_reveal.setImageResource(R.drawable.ps_visible);
                    password_field.setSelection(password_field.getText().length());
                } else if (password_field_type == InputType.TYPE_TEXT_VARIATION_NORMAL) {
                    password_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password_field.setTypeface(phone_field.getTypeface());
                    ps_reveal.setImageResource(R.drawable.ps_invisible);
                    password_field.setSelection(password_field.getText().length());
                }

            }
        });


        String[] acc_type_array = new String[]{
                "Individual Account",
                "Enterprise Account",
        };
        ArrayAdapter<String> spinner = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner_item_login_frag, acc_type_array);
        spinner.setDropDownViewResource(R.layout.spinner_item_login_frag);
        account_type_spinner.setAdapter(spinner);


        Animation user_ll_blink = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
        already_user_ll_register.startAnimation(user_ll_blink);


        /*-----------------------phase_modifier-----------------------*/
        steps_mod_1 = v.findViewById(R.id.steps_mod_1);
        String steps_mod_1_string = "<b>1</b><small><sub>/<b>2</b></sub></small><br><small><small><small><b>STEPS</b></small></small></small>";
        steps_mod_1.setText(Html.fromHtml(steps_mod_1_string));
        /*-----------------------phase_modifier-----------------------*/


        /*-----------------------initialize the FirebaseAuth instance-----------------------*/
        //mAuth = FirebaseAuth.getInstance();
        /*-----------------------initialize the FirebaseAuth instance-----------------------*/




        /*-----------------------------------------------------------------Button action-----------------------------------------------------------------*/
        v.findViewById(R.id.register_button_signup_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
            }
        });

        v.findViewById(R.id.login_button_signup_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new login_fragment())
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

    private void Registration() {
        String account_type__value = "";
        String username_value = username_field.getText().toString();
        String phone_value = phone_field.getText().toString();
        String client_code_value = client_code_field.getText().toString();
        String password_value = password_field.getText().toString();
        String account_type__raw_value = account_type_spinner.getSelectedItem().toString();

        if (account_type__raw_value.equals("Individual Account")) {
            account_type__value = "Individual";
        } else if (account_type__raw_value.equals("Enterprise Account")) {
            account_type__value = "Enterprise";
        }

        if (username_value.length() < 5) {
            if (username_value.trim().isEmpty()) {
                username_field.setError("Empty username");
                username_field.requestFocus();
                return;
            }
            username_field.requestFocus();
            Toast.makeText(getActivity(), "Username should contain at least 5 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone_value.length() != 10) {
            if (phone_value.trim().isEmpty()) {
                phone_field.setError("Empty number");
                phone_field.requestFocus();
                return;
            }
            phone_field.requestFocus();
            Toast.makeText(getActivity(), "Your number should contain at 10 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (client_code_value.length() < 5) {
            if (client_code_value.trim().isEmpty()) {
                client_code_field.setError("Empty client code");
                client_code_field.requestFocus();
                return;
            }
            client_code_field.requestFocus();
            Toast.makeText(getActivity(), "Please enter a valid client code", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password_value.length() < 5) {
            if (password_value.trim().isEmpty()) {
                password_field.setError("Empty password");
                password_field.requestFocus();
                return;
            }
            password_field.requestFocus();
            Toast.makeText(getActivity(), "Strengthens your password for better security", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean check_username_existence = db.check_username_existence(username_value);
        if (check_username_existence) {
            boolean check_phone_existence = db.check_phone_existence(phone_value);
            if (check_phone_existence) {
                boolean check_client_code_existence = db.check_client_code_existence(client_code_value);
                if (check_client_code_existence) {
                    boolean insert_client = db.insert_client(username_value, phone_value, client_code_value, password_value, account_type__value);
                    if (insert_client) {
                        Toast.makeText(getActivity(), "You've successfully registered!", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(login_fragment.SHARED_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(login_fragment.USER_PHONE,phone_value);
                        editor.putBoolean(login_fragment.SAVE_CREDENTIAL,true);
                        editor.apply();

                        Bundle credential_bundle = new Bundle();
                        credential_bundle.putString("phone", phone_value);
                        Intent i = new Intent(getActivity(), Main_Home_Activity.class);
                        i.putExtras(credential_bundle);
                        startActivity(i);
                        getActivity().finish();

                    } else {
                        Toast.makeText(getActivity(), "An unexpected error happened while trying to register.\nPlease try again and if this error still occur, try later.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Client code Already exists", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Phone Already exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Username Already exists", Toast.LENGTH_SHORT).show();
        }

    }
}