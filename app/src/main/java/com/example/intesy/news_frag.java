package com.example.intesy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class news_frag extends Fragment {

    private ViewPager news_viewpager;
    private int[] img_urls = {R.drawable.news_1,R.drawable.news_2,R.drawable.news_3,R.drawable.news_4,R.drawable.news_1};
    private String[] date_list = {"05/04/2020","04/04/2020","28/03/2020","25/03/2020","24/03/2020"};
    private String[] title_list= {"Algérie Télécom maintient la continuité de ses services pour ses clients pendant la prolongation du confinement sanitaire.",
            "Pas de coupure d'internet pour nos abonnés de Blida jusqu'au 19 Avril",
            "Pas de coupure d'internet pour nos abonnés de Blida jusqu'au 04 Avril",
            "Algérie Télécom lance le service de signalement en ligne des dérangements",
            "Les horaires d'ouverture des Agences Commerciales de Blida et d’Alger"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_frag,container,false);
        news_viewpager = v.findViewById(R.id.news_viewpager);

        PageviewerAdapter adapter = new PageviewerAdapter(getActivity(),img_urls,date_list,title_list);
        news_viewpager.setAdapter(adapter);
        news_viewpager.setPadding(120,0,120,0);

        return v;
    }
}
