package com.example.intesy;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class checkout_frag extends Fragment {
    private TextView affected_card_number, affected_card_name, affected_card_date, test;
    private TextInputEditText card_name_field, card_number_field;
    private EditText card_ccv_field;
    private Spinner card_month_field, card_year_field;
    private Button confirm_button;
    private String MSISDN_VALUE, CARD_CODE_VALUE, BILL_NUMBER_VALUE, RECHARGE_AMOUNT_VALUE, option_val, method_val;
    private String[] available_card_code = {"1000000000000000", "1000000000000001", "1000000000000002", "1000000000000003", "1000000000000004", "1000000000000005"};
    private String[] available_card_ccv = {"100", "101", "102", "103", "104", "105"};

    private DataBaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.checkout_frag, container, false);
        //test = v.findViewById(R.id.test);
        //test.setText(String.format("%s %s %s %s", MSISDN_VALUE, CARD_CODE_VALUE, BILL_NUMBER_VALUE, RECHARGE_AMOUNT_VALUE));
        get_data();

        db = new DataBaseHelper(getActivity());
        //declaration
        affected_card_number = v.findViewById(R.id.affected_card_number);
        affected_card_name = v.findViewById(R.id.affected_card_name);
        affected_card_date = v.findViewById(R.id.affected_card_date);

        card_name_field = v.findViewById(R.id.card_name_field);
        card_number_field = v.findViewById(R.id.card_number_field);
        card_ccv_field = v.findViewById(R.id.card_ccv_field);

        card_month_field = v.findViewById(R.id.card_month_field);
        card_year_field = v.findViewById(R.id.card_year_field);

        confirm_button = v.findViewById(R.id.confirm_button);


        //text watcher
        card_name_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String var_string = card_name_field.getText().toString().trim();
                affected_card_name.setText(var_string);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        card_number_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String var_string = card_number_field.getText().toString().trim();
                affected_card_number.setText(var_string);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        card_month_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String var_string_month = card_month_field.getSelectedItem().toString().trim();
                String var_string_year = card_year_field.getSelectedItem().toString().trim();
                affected_card_date.setText(String.format("%s/%s", var_string_month, var_string_year));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        card_year_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String var_string_month = card_month_field.getSelectedItem().toString().trim();
                String var_string_year = card_year_field.getSelectedItem().toString().trim();
                affected_card_date.setText(String.format("%s/%s", var_string_month, var_string_year));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //button action
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Dialog progress_dialog = new Dialog(getActivity());
                ProgressBar pb = new ProgressBar(getActivity());
                progress_dialog.setContentView(pb);
                progress_dialog.show();
                String val_card_code = card_number_field.getText().toString();
                String val_card_name = card_name_field.getText().toString();
                String val_card_ccv = card_ccv_field.getText().toString();

                if (val_card_code.length() != 16) {
                    if (val_card_code.trim().isEmpty()) {
                        card_number_field.setError("Empty card number");
                        card_number_field.requestFocus();
                        return;
                    }
                    card_number_field.requestFocus();
                    Toast.makeText(getActivity(), "Card number must contain 16 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (val_card_name.trim().isEmpty()) {
                    card_name_field.setError("Empty card holder's Name");
                    card_name_field.requestFocus();
                    return;
                }

                if (val_card_ccv.length() < 3) {
                    if (val_card_ccv.trim().isEmpty()) {
                        card_ccv_field.setError("Empty CCV code");
                        card_ccv_field.requestFocus();
                        return;
                    }
                    card_ccv_field.requestFocus();
                    Toast.makeText(getActivity(), "Your CCV must contain 3 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Arrays.asList(available_card_code).contains(val_card_code) && Arrays.asList(available_card_ccv).contains(val_card_ccv)) {
                    String phone_connected_with = getActivity().getIntent().getExtras().getString("phone");
                    db.insert_history(phone_connected_with, RECHARGE_AMOUNT_VALUE, option_val, method_val, "Paid");

                    progress_dialog.cancel();
                    Toast.makeText(getActivity(), "Operation Successful!\nChanges will apply soon if not already", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .replace(R.id.fragment_container_2, new actual_home_fragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        fragmentTransaction.addToBackStack(null);
                    }
                    fragmentTransaction.commit();

                } else {
                    Toast.makeText(getActivity(), "Invalid Credential", Toast.LENGTH_LONG).show();
                }

            }
        });

        return v;
    }

    private void get_data() {
        Bundle get_data_bundle = this.getArguments();
        if (get_data_bundle != null) {
            MSISDN_VALUE = get_data_bundle.getString("MSISDN_VALUE");
            CARD_CODE_VALUE = get_data_bundle.getString("CARD_CODE_VALUE");
            BILL_NUMBER_VALUE = get_data_bundle.getString("BILL_NUMBER_VALUE");
            RECHARGE_AMOUNT_VALUE = get_data_bundle.getString("RECHARGE_AMOUNT_VALUE");
            option_val = get_data_bundle.getString("option_val");
            method_val = get_data_bundle.getString("method_val");
            get_data_bundle.clear();
        } else {
            Toast.makeText(getActivity(), "NO DATA ACQUIRED", Toast.LENGTH_SHORT).show();
        }

    }
}
