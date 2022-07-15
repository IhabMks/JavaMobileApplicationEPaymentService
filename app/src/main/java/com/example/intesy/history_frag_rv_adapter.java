package com.example.intesy;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class history_frag_rv_adapter extends RecyclerView.Adapter<history_frag_rv_adapter.MyViewHolder> {
    private Context context;
    private Cursor cursor;
    private String date_value, option_value, method_value, amount_value, payment_status_value;

    public history_frag_rv_adapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.rv_custom_1, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        date_value = cursor.getString(cursor.getColumnIndex(Table_list.billing_history_table.payment_date_col));
        option_value = cursor.getString(cursor.getColumnIndex(Table_list.billing_history_table.option_col));
        method_value = cursor.getString(cursor.getColumnIndex(Table_list.billing_history_table.method_col));
        amount_value = cursor.getString(cursor.getColumnIndex(Table_list.billing_history_table.payment_amount_col));
        payment_status_value = cursor.getString(cursor.getColumnIndex(Table_list.billing_history_table.payment_status_col));

        holder.date_txt.setText(date_value);
        holder.option_txt.setText(option_value);
        holder.method_txt.setText(method_value);
        holder.amount_txt.setText(amount_value);
        holder.payment_status_txt.setText(payment_status_value);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }

        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date_txt, amount_txt, payment_status_txt, option_txt, method_txt;
        private CardView card_view;

        public MyViewHolder(@NonNull View v) {
            super(v);
            card_view = v.findViewById(R.id.card_view);
            date_txt = v.findViewById(R.id.date_txt);
            amount_txt = v.findViewById(R.id.amount_txt);
            payment_status_txt = v.findViewById(R.id.payment_status_txt);
            option_txt = v.findViewById(R.id.option_txt);
            method_txt = v.findViewById(R.id.method_txt);
        }
    }
}