package com.example.intesy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

public class PageviewerAdapter extends PagerAdapter {
    private Context context;
    private int [] img_index;
    private String[] date_list;
    private String[] title_list;

    PageviewerAdapter(Context context, int[] img_index, String[] date_list, String[] title_list) {
        this.context = context;
        this.img_index = img_index;
        this.date_list = date_list;
        this.title_list = title_list;
    }

    @Override
    public int getCount() {
        return img_index.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.pager_item,container,false);

        final TextView date,title;

        ImageView imageView = v.findViewById(R.id.brochure);
        date=v.findViewById(R.id.date);
        title=v.findViewById(R.id.title);

        imageView.setImageResource(img_index[position]);
        date.setText(date_list[position]);
        title.setText(title_list[position]);

        final ImageButton heart_button = v.findViewById(R.id.heart_button);

        v.findViewById(R.id.heart_button).setOnClickListener(new View.OnClickListener() {
            boolean heart_on_mod = false;
            @Override
            public void onClick(View v) {

                if (!heart_on_mod){
                    heart_on_mod = true;
                    heart_button.setBackground(context.getResources().getDrawable(R.drawable.heart_on));
                }else{
                    heart_on_mod = false;
                    heart_button.setBackground(context.getResources().getDrawable(R.drawable.heart_off));
                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connected;
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    connected = true;
                }
                else
                    connected = false;

                if (connected){
                    if (title.getText().toString().contains("pendant la prolongation du confinement")){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.algerietelecom.dz/fr/espace-presse/algerie-telecom-maintient-la-continuite-de-ses-services-pour-ses-clients-pendant-la-prolongation-du-confinement-sanitaire-art613"));
                        context.startActivity(i);
                    }
                    if (title.getText().toString().contains("Blida jusqu'au 19")){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.algerietelecom.dz/fr/espace-presse/pas-de-coupure-dinternet-pour-nos-abonnes-de-blida-jusquau-19-avril-art611"));
                        context.startActivity(i);
                    }
                    if (title.getText().toString().contains("Blida jusqu'au 04")){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.algerietelecom.dz/fr/espace-presse/pas-de-coupure-dinternet-pour-nos-abonnes-de-blida-jusquau-04-avril-art607"));
                        context.startActivity(i);
                    }
                    if (title.getText().toString().contains("signalement en ligne")){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.algerietelecom.dz/fr/espace-presse/algerie-telecom-lance-le-service-de-signalement-en-ligne-des-derangements-art605"));
                        context.startActivity(i);
                    }
                    if (title.getText().toString().contains("Commerciales de Blida")){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.algerietelecom.dz/fr/espace-presse/les-horaires-douverture-des-agences-commerciales-de-blida-et-dalger-art602"));
                        context.startActivity(i);
                    }
                }else{
                    TextView custom_toast = new TextView(context);
                    custom_toast.setText("looks like you are offline.\ncheck your connection and try again");
                    custom_toast.setGravity(Gravity.CENTER);
                    Toast toast = new Toast(context);
                    toast.setView(custom_toast);
                    toast.show();

                }

            }
        });



        container.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
