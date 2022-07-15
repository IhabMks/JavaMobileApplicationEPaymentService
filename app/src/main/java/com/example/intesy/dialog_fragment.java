package com.example.intesy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class dialog_fragment extends DialogFragment {
    private TextInputEditText MSISDN_field, card_code_field, bill_number_field;
    private Spinner recharge_amount;
    private CheckBox check_accept_condition;
    private TextInputLayout MSISDN_field_container, card_code_field_container, bill_number_field_container;
    private String dialog_mode, option_val, method_val;
    private String dialog_type_1 = "CARD_NB";
    private String dialog_type_2 = "CARD_NB_MSISDN";
    private String dialog_type_3 = "BILL_NB";
    private String dialog_type_4 = "MSISDN_DATA";
    private String dialog_type_5 = "TERMS_ONLY";
    private DataBaseHelper db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment, null);

        builder.setView(v)
                .setTitle("Additional information")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (check_accept_condition.isChecked()) {
                            if (dialog_mode.equals(dialog_type_1)) {
                                String card_code_value = card_code_field.getText().toString().trim();
                                send_data(null, card_code_value, null, "1500 DA");
                            } else if (dialog_mode.equals(dialog_type_2)) {
                                String MSISDN_value = MSISDN_field.getText().toString().trim();
                                String card_code_value = card_code_field.getText().toString().trim();
                                send_data(MSISDN_value, card_code_value, null, "4500 DA");
                            } else if (dialog_mode.equals(dialog_type_3)) {
                                String user_phone = getActivity().getIntent().getExtras().getString("phone");
                                db = new DataBaseHelper(getActivity());
                                Cursor cursor = db.get_credential(user_phone);
                                cursor.moveToFirst();
                                int user_line_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.line_day_col));
                                if (user_line_day == 0){
                                    double x = (int) (Math.random() * ((1300 - 300) + 1)) + 300;
                                    String bill_number_value = bill_number_field.getText().toString().trim();
                                    send_data(null, null, bill_number_value, x + " DA");
                                }else{
                                    Toast.makeText(getActivity(), "It seems that you're Line subscription\nis still valid", Toast.LENGTH_SHORT).show();
                                }
                            } else if (dialog_mode.equals(dialog_type_4)) {
                                String MSISDN_value = MSISDN_field.getText().toString().trim();
                                String recharge_amount_value = recharge_amount.getSelectedItem().toString();
                                send_data(MSISDN_value, null, null, recharge_amount_value);
                            } else if (dialog_mode.equals(dialog_type_5)) {
                                String user_phone = getActivity().getIntent().getExtras().getString("phone");
                                db = new DataBaseHelper(getActivity());
                                Cursor cursor = db.get_credential(user_phone);
                                cursor.moveToFirst();
                                int user_adsl_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.adsl_day_col));
                                if (user_adsl_day == 0){
                                    send_data(null, null, null, "1660.00 DA");
                                }else{
                                    Toast.makeText(getActivity(), "It seems that you're ADSL subscription\nis still valid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "You must accept the Terms and Conditions to proceed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        MSISDN_field = v.findViewById(R.id.MSISDN_field);
        card_code_field = v.findViewById(R.id.card_code_field);
        bill_number_field = v.findViewById(R.id.bill_number_field);
        recharge_amount = v.findViewById(R.id.recharge_amount);
        check_accept_condition = v.findViewById(R.id.check_accept_condition);

        MSISDN_field_container = v.findViewById(R.id.MSISDN_field_container);
        card_code_field_container = v.findViewById(R.id.card_code_field_container);
        bill_number_field_container = v.findViewById(R.id.bill_number_field_container);

        /*---------------------Dialog_type---------------------*/
        Bundle dialog_mode_bundle = this.getArguments();
        if (dialog_mode_bundle != null) {
            dialog_mode = dialog_mode_bundle.getString("dialog_mode");
            option_val = dialog_mode_bundle.getString("option_val");
            method_val = dialog_mode_bundle.getString("method_val");
            dialog_mode_bundle.clear();
        } else {
            Toast.makeText(getActivity(), "UNDEFINED DIALOG", Toast.LENGTH_LONG).show();
        }

        if (dialog_mode.equals(dialog_type_1)) {
            card_code_field_container.setVisibility(View.VISIBLE);
        } else if (dialog_mode.equals(dialog_type_2)) {
            card_code_field_container.setVisibility(View.VISIBLE);
            MSISDN_field_container.setVisibility(View.VISIBLE);
        } else if (dialog_mode.equals(dialog_type_3)) {
            bill_number_field_container.setVisibility(View.VISIBLE);
        } else if (dialog_mode.equals(dialog_type_4)) {
            recharge_amount.setVisibility(View.VISIBLE);
            MSISDN_field_container.setVisibility(View.VISIBLE);
        }


        return builder.create();
    }

    private void send_data(String MSISDN_VALUE, String CARD_CODE_VALUE, String BILL_NUMBER_VALUE, String RECHARGE_AMOUNT_VALUE) {
        Bundle send_data_bundle = new Bundle();

        send_data_bundle.putString("MSISDN_VALUE", MSISDN_VALUE);
        send_data_bundle.putString("CARD_CODE_VALUE", CARD_CODE_VALUE);
        send_data_bundle.putString("BILL_NUMBER_VALUE", BILL_NUMBER_VALUE);
        send_data_bundle.putString("RECHARGE_AMOUNT_VALUE", RECHARGE_AMOUNT_VALUE);
        send_data_bundle.putString("option_val", option_val);
        send_data_bundle.putString("method_val", method_val);


        checkout_frag checkoutFrag = new checkout_frag();
        checkoutFrag.setArguments(send_data_bundle);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.fragment_container_2, checkoutFrag)
                .addToBackStack(null)
                .commit();
    }

}