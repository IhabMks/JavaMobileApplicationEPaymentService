package com.example.intesy;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class history_frag extends Fragment {

    private RecyclerView history_frag_rv;
    private DataBaseHelper db;
    private String user_phone;
    private Cursor mCursor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_frag,container,false);
        user_phone = getActivity().getIntent().getExtras().getString("phone");
        history_frag_rv = v.findViewById(R.id.history_frag_rv);
        db = new DataBaseHelper(getActivity());

        mCursor = db.get_history_data(user_phone);
        history_frag_rv_adapter rv_adapter = new history_frag_rv_adapter(getActivity(),mCursor);
        rv_adapter.swapCursor(db.get_history_data(user_phone));
        history_frag_rv.setAdapter(rv_adapter);
        history_frag_rv.setLayoutManager(new LinearLayoutManager(getActivity()));



        return v;
    }

}