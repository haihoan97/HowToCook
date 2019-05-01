package com.example.howtocook.uis.fragments.personal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.howtocook.R;
import com.example.howtocook.adapter.CongThucAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CongThucFragment extends Fragment {
    //private ArrayList<Post> arr;
    private ArrayList<String> arr;
    private RecyclerView recyclerView;
    private CongThucAdapter congThucAdapter;

    private static final String TAG = "CongThucFragment";

    public CongThucFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cong_thuc, container, false);
//        arr = new ArrayList<>();
//        try {
//            //JDBCData jdbcData = new JDBCData();
//            //arr = jdbcData.getList();
//            for (int i = 1; i < 10; i++) {
//                arr.add("Công thức " + i);
//            }
//            Log.d(TAG, "onCreateView: size" + arr.size());
////            for(int i=0;i<arr.size();i++){
////                Log.d(TAG, "onCreateView: "+arr.get(i).getTitle());
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        arr = new ArrayList<>();
        arr.add("mô tả này");
        for (int i = 1; i <= 10; i++) {
            arr.add("mô tả: " + i);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        congThucAdapter = new CongThucAdapter(arr);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(congThucAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(getContext(), arr.get(position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getContext(), ViewCongThuc.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
//        //recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

}
