package com.example.intesy;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class profile_frag extends Fragment {
    private TextView profile_username,profile_number,profile_client_code,profile_account_type,profile_payment,profile_subs_date,profile_ADSL_remaining_date,profile_LINE_remaining_date ;
    private String user_username,user_phone,user_client_code,user_account_type,user_subscription_date;
    private int user_overall_payment,user_adsl_day,user_line_day;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_frag,container,false);
        //declaration..
        profile_username = v.findViewById(R.id.profile_username);
        profile_number = v.findViewById(R.id.profile_number);
        profile_client_code = v.findViewById(R.id.profile_client_code);
        profile_account_type = v.findViewById(R.id.profile_account_type);
        profile_payment = v.findViewById(R.id.profile_payment);
        profile_subs_date = v.findViewById(R.id.profile_subs_date);
        profile_ADSL_remaining_date = v.findViewById(R.id.profile_ADSL_remaining_date);
        profile_LINE_remaining_date = v.findViewById(R.id.profile_LINE_remaining_date);

        get_current_data();
        profile_username.setText(user_username);
        profile_number.setText(user_phone);
        profile_client_code.setText(user_client_code);
        profile_account_type.setText(user_account_type);
        profile_payment.setText(String.valueOf(user_overall_payment));
        profile_ADSL_remaining_date.setText(String.valueOf(user_adsl_day));
        profile_LINE_remaining_date.setText(String.valueOf(user_line_day));
        profile_subs_date.setText(user_subscription_date);


        return v;
    }
    private void get_current_data() {
        DataBaseHelper db = new DataBaseHelper(getActivity());
        String phone_connected_with = getActivity().getIntent().getExtras().getString("phone");
        Cursor cursor = db.get_credential(phone_connected_with);
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data found for this user", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            user_username = cursor.getString(cursor.getColumnIndex(Table_list.client_table.username_col));
            user_phone = cursor.getString(cursor.getColumnIndex(Table_list.client_table.phone_col));
            user_client_code = cursor.getString(cursor.getColumnIndex(Table_list.client_table.client_code_col));
            user_account_type = cursor.getString(cursor.getColumnIndex(Table_list.client_table.account_type_col));
            user_overall_payment = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.overall_payment_col));
            user_adsl_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.adsl_day_col));
            user_line_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.line_day_col));
            user_subscription_date = cursor.getString(cursor.getColumnIndex(Table_list.client_table.subscription_date_col));
        }
    }
}
