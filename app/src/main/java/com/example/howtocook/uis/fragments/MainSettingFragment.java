package com.example.howtocook.uis.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.LoginActivity;
import com.example.howtocook.uis.acticities.PersonalActivity;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainSettingFragment extends Fragment implements View.OnClickListener {

    LinearLayout setting_log_out;
    LinearLayout ln_setting_personal;
    CircleImageView img_setting_ava;
    TextView tv_setting_name;
    Users currentUser;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MainSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setting_log_out = view.findViewById(R.id.setting_log_out);
        ln_setting_personal = view.findViewById(R.id.ln_setting_personal);
        img_setting_ava = view.findViewById(R.id.img_setting_ava);
        tv_setting_name = view.findViewById(R.id.tv_setting_name);

        setting_log_out.setOnClickListener(this);
        ln_setting_personal.setOnClickListener(this);
        firebase_database = FirebaseDatabase.getInstance();
        setUserInfo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_log_out:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            case R.id.ln_setting_personal:
                Intent personal_intent = new Intent(getActivity(), PersonalActivity.class);
                getActivity().startActivity(personal_intent);

                break;

        }
    }

    private void setUserInfo(){
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            database_reference = firebase_database.getReference("Users").child(user.getUid());
            database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);

                    tv_setting_name.setText(users.getFullName());

                    if (getActivity() != null){
                        Glide.with(getActivity()).load(users.getUserImg()).into(img_setting_ava);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
    }
}
