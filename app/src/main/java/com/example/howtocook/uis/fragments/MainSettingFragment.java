package com.example.howtocook.uis.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.howtocook.R;
import com.example.howtocook.uis.acticities.LoginActivity;
import com.example.howtocook.uis.acticities.PersonalActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainSettingFragment extends Fragment implements View.OnClickListener {

   LinearLayout setting_log_out;
   LinearLayout ln_setting_personal;

    public MainSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setting_log_out = view.findViewById(R.id.setting_log_out);
        ln_setting_personal = view.findViewById(R.id.ln_setting_personal);

        setting_log_out.setOnClickListener(this);
        ln_setting_personal.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_log_out:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ln_setting_personal:
                Intent personal_intent = new Intent(getActivity(), PersonalActivity.class);
                startActivity(personal_intent);
                break;

        }
    }
}
