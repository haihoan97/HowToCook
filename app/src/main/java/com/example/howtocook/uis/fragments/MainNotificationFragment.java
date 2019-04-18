package com.example.howtocook.uis.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.howtocook.R;
import com.example.howtocook.adapter.MainNotificationViewAdapter;
import com.example.howtocook.model.UserNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainNotificationFragment extends Fragment {

    private ArrayList<UserNotification> listNoti;
    private RecyclerView main_noti_recycle_view;

    private MainNotificationViewAdapter adapter;

    public MainNotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_notification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_noti_recycle_view = view.findViewById(R.id.main_noti_recycle_view);
        listNoti = new ArrayList<>();
        try {
            fakeData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new MainNotificationViewAdapter(listNoti);
        main_noti_recycle_view.setLayoutManager(layoutManager);
        main_noti_recycle_view.setAdapter(adapter);
        main_noti_recycle_view.setNestedScrollingEnabled(false);


    }
    public void fakeData() throws ParseException {
        String date = "17-12-2019 22:47:00";
        Date date1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date);


        UserNotification noti = new UserNotification(1, "Hoan","Thit lon quay",
                "da them bai viet","Miku",date1, false);
        UserNotification noti1 = new UserNotification(1, "Hiep","Thit lon quay akj afjhas afksh afa jagf ajf gasjkf gaf asfas jfgsakjfgsajfgsajkf gasfasfasfg a<br/> khafha kkashf akahfask",
                "da thich bai viet","Miku",date1, false);
        UserNotification noti2 = new UserNotification(1, "Lam","Thit lon quay",
                "da chia se bai viet","Miku",date1,true);
        UserNotification noti3 = new UserNotification(1, "HAnh","Thit lon quay",
                "da binh luan bai viet","Miku",date1,true);

        listNoti.add(noti);
        listNoti.add(noti1);
        listNoti.add(noti2);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);
        listNoti.add(noti3);

    }
}
