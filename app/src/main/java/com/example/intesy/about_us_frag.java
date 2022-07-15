package com.example.intesy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class about_us_frag extends Fragment {

    private boolean connected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_us_frag, container, false);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        v.findViewById(R.id.fb_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ihab.mks.7545"));
                    getActivity().startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "looks like you are offline.\ncheck your connection and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        v.findViewById(R.id.twitter_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/IhabMks"));
                    getActivity().startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "looks like you are offline.\ncheck your connection and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }
}
