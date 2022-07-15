package com.example.intesy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class option_selection_frag extends Fragment implements View.OnClickListener {
    private TextView user_number_view;
    private ConstraintLayout e_pyament_fixe_button,e_pyament_adsl_button,e_pyament_4g_button,card_ticket_adsl_button,card_ticket_4g_button;
    private String dialog_type_1 = "CARD_NB";
    private String dialog_type_2 = "CARD_NB_MSISDN";
    private String dialog_type_3 = "BILL_NB";
    private String dialog_type_4 = "MSISDN_DATA";
    private String dialog_type_5 = "TERMS_ONLY";
    private String user_number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.option_selection_frag,container,false);
        e_pyament_fixe_button = v.findViewById(R.id.e_pyament_fixe_button);
        e_pyament_adsl_button = v.findViewById(R.id.e_pyament_adsl_button);
        e_pyament_4g_button = v.findViewById(R.id.e_pyament_4g_button);
        card_ticket_adsl_button = v.findViewById(R.id.card_ticket_adsl_button);
        card_ticket_4g_button = v.findViewById(R.id.card_ticket_4g_button);
        user_number_view = v.findViewById(R.id.user_number_view);

        e_pyament_fixe_button.setOnClickListener(this);
        e_pyament_adsl_button.setOnClickListener(this);
        e_pyament_4g_button.setOnClickListener(this);
        card_ticket_adsl_button.setOnClickListener(this);
        card_ticket_4g_button.setOnClickListener(this);

        user_number = getActivity().getIntent().getExtras().getString("phone");
        user_number_view.setText(user_number);

        return v;
    }


    @Override
    public void onClick(View v) {
        Bundle dialog_type = new Bundle();

        switch (v.getId()){
            case R.id.e_pyament_fixe_button:
                dialog_type.putString("dialog_mode",dialog_type_3);
                dialog_type.putString("option_val","LINE");
                dialog_type.putString("method_val","E-payment");
                Opendialog(dialog_type);
                break;
            case R.id.e_pyament_adsl_button:
                dialog_type.putString("dialog_mode",dialog_type_5);
                dialog_type.putString("option_val","ADSL");
                dialog_type.putString("method_val","E-payment");
                Opendialog(dialog_type);
                break;
            case R.id.e_pyament_4g_button:
                dialog_type.putString("dialog_mode",dialog_type_4);
                dialog_type.putString("option_val","4G");
                dialog_type.putString("method_val","E-payment");
                Opendialog(dialog_type);
                break;
            case R.id.card_ticket_adsl_button:
                dialog_type.putString("dialog_mode",dialog_type_1);
                dialog_type.putString("option_val","ADSL");
                dialog_type.putString("method_val","E-Card/Ticket");
                Opendialog(dialog_type);
                break;
            case R.id.card_ticket_4g_button:
                dialog_type.putString("dialog_mode",dialog_type_2);
                dialog_type.putString("option_val","4G");
                dialog_type.putString("method_val","E-Card/Ticket");
                Opendialog(dialog_type);
                break;
        }
    }

    public void Opendialog(Bundle dialog_type) {
        dialog_fragment d_f = new dialog_fragment();
        d_f.setArguments(dialog_type);
        d_f.show(getActivity().getSupportFragmentManager(), "dialog_fragment");
    }

}
