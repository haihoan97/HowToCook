package com.example.howtocook.uis.fragments.personal;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.adapter.MainPersonalPostAdapter;
import com.example.howtocook.adapter.personalpage.CongThucAdapter;
import com.example.howtocook.model.PersonalPost;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.uis.acticities.PersonalActivity;
import com.example.howtocook.uis.acticities.PersonalPostActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CongThucFragment extends Fragment {
    //private ArrayList<Post> arr;
    private ArrayList<String> arr;
    private RecyclerView recyclerView;
    private CongThucAdapter congThucAdapter;

    ArrayList<PersonalPost> listPost;
    MainPersonalPostAdapter adapter;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "CongThucFragment";

    public CongThucFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        listPost = new ArrayList<>();

        adapter = new MainPersonalPostAdapter(getContext(),currentUser.getUid(), new MainPersonalPostAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView) {

            }

            @Override
            public void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck) {

            }

            @Override
            public void onFollowButtonClick(View itemView, ToggleButton button, boolean isCheck) {

            }
        } , listPost);
        recyclerView.setAdapter(adapter);

        initListPost();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
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
//        arr = new ArrayList<>();
//        arr.add("mô tả này");
//        for (int i = 1; i <= 10; i++) {
//            arr.add("mô tả: " + i);
//        }

//        congThucAdapter = new CongThucAdapter(arr);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setAdapter(congThucAdapter);
//        recyclerView.setLayoutManager(gridLayoutManager);
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


    public void initListPost(){


        adapter.notifyDataSetChanged();
    }

}
