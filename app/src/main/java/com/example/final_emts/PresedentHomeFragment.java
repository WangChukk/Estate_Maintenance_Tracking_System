package com.example.final_emts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class PresedentHomeFragment extends Fragment {

    public PresedentHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presedent_home, container, false);

        Button validate = view.findViewById(R.id.preValidatebtn);
        Button records = view.findViewById(R.id.preRecordbtn);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), preValidateActivity.class);
                startActivity(intent);
            }
        });
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
