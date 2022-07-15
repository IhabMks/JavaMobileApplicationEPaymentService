package com.example.intesy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class home_fragment extends Fragment {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.home_fragment,container,false);



        v.findViewById(R.id.login_button_home_frag).setOnClickListener(new View.OnClickListener() {
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
        v.findViewById(R.id.register_button_home_frag).setOnClickListener(new View.OnClickListener() {
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

        return v;
    }

}
