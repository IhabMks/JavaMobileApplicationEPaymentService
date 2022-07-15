package com.example.intesy;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;
import java.util.Timer;

public class actual_home_fragment extends Fragment {
    private ProgressBar remaining_days_progress;
    private ViewFlipper counter_switcher;
    private ImageButton pb_right_but, pb_left_but;
    private CardView profile_card, payment_card, news_card, history_card;
    private int user_ADSL_day, user_LINE_day,user_payment_count;
    private TextView ADSL_day, Line_day,payment_count;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.actual_home_fragment, container, false);
        /*-------------------------Views declaration-------------------------*/
        get_current_data();
        remaining_days_progress = v.findViewById(R.id.remaining_days_progress);
        pb_right_but = v.findViewById(R.id.pb_right_but);
        pb_left_but = v.findViewById(R.id.pb_left_but);
        counter_switcher = v.findViewById(R.id.counter_switcher);
        profile_card = v.findViewById(R.id.profile_card);
        payment_card = v.findViewById(R.id.payment_card);
        news_card = v.findViewById(R.id.news_card);
        history_card = v.findViewById(R.id.history_card);
        ADSL_day = v.findViewById(R.id.ADSL_day);
        Line_day = v.findViewById(R.id.Line_day);
        payment_count = v.findViewById(R.id.payment_count);
        ADSL_day.setText(String.valueOf(user_ADSL_day));
        Line_day.setText(String.valueOf(user_LINE_day));
        payment_count.setText(String.valueOf(user_payment_count));


        float mod = (float) 100 / 30;
        float counter_float_value = (float) user_ADSL_day;
        int progress_value = (int) (mod * counter_float_value);
        remaining_days_progress.setProgress(progress_value);

        pb_right_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter_switcher.setInAnimation(getActivity(), R.anim.slide_in_right);
                counter_switcher.setOutAnimation(getActivity(), R.anim.slide_out_left);
                counter_switcher.setDisplayedChild(1);

                float mod = (float) 100 / 90;
                float counter_float_value = (float) user_LINE_day;
                int progress_value = (int) (mod * counter_float_value);
                remaining_days_progress.setProgress(progress_value);

                Animation fading_left = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_to_left);
                Animation fading_partially_left = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_partially_to_left);

                pb_left_but.startAnimation(fading_left);
                pb_right_but.startAnimation(fading_partially_left);
                pb_left_but.setVisibility(View.VISIBLE);
                pb_right_but.setVisibility(View.INVISIBLE);

            }
        });

        pb_left_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter_switcher.setInAnimation(getActivity(), android.R.anim.slide_in_left);
                counter_switcher.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
                counter_switcher.setDisplayedChild(0);

                Animation fading_right = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_to_right);
                Animation fading_partially_right = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_partially_to_right);

                float mod = (float) 100 / 30;
                float counter_float_value = (float) user_ADSL_day;
                int progress_value = (int) (mod * counter_float_value);
                remaining_days_progress.setProgress(progress_value);

                pb_right_but.startAnimation(fading_partially_right);
                pb_left_but.startAnimation(fading_right);
                pb_right_but.setVisibility(View.VISIBLE);
                pb_left_but.setVisibility(View.INVISIBLE);

            }
        });


        //cards_clickable
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_Home_Activity.nav_view.setCheckedItem(R.id.nav_profile);
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new profile_frag())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        payment_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_Home_Activity.nav_view.setCheckedItem(R.id.nav_e_payment);
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new option_selection_frag())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        news_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_Home_Activity.nav_view.setCheckedItem(R.id.nav_news);
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new news_frag())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        history_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_Home_Activity.nav_view.setCheckedItem(R.id.nav_history);
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container_2, new history_frag())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }

    public void get_current_data() {

        DataBaseHelper db = new DataBaseHelper(getActivity());
        String phone_connected_with = getActivity().getIntent().getExtras().getString("phone");
        Cursor cursor = db.get_credential(phone_connected_with);
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data found for this user", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            user_ADSL_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.adsl_day_col));
            user_LINE_day = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.line_day_col));
            user_payment_count = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.overall_payment_col));
        }
    }

}
