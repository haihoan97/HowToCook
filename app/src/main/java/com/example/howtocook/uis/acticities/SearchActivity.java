package com.example.howtocook.uis.acticities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.adapter.ShowSearchPostAdapter;
import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.utils.HeartAnimation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner search_spinner;
    private Button search_filter;
    private TextView search_tv_content;
    private RecyclerView search_recycle;
    String searchContent = "no";
    String season = "no";

    private ShowSearchPostAdapter postAdapter;
    ArrayList<Post> listPost;

    HeartAnimation animation;
    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //init
        search_spinner = findViewById(R.id.search_spinner);
        search_filter = findViewById(R.id.search_filter);
        search_tv_content = findViewById(R.id.search_tv_content);
        search_recycle = findViewById(R.id.search_recycle);

        animation = new HeartAnimation();
        firebase_database = FirebaseDatabase.getInstance();

        listPost = new ArrayList<>();

        //init Spinner
        initSpinner();

        //get search Content
        Intent intent = getIntent();
        searchContent = intent.getStringExtra("searchContent");
        season = intent.getStringExtra("season");



        //init Recycle View

        postAdapter = new ShowSearchPostAdapter(listPost,this,  new ShowSearchPostAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView) {
                int i = search_recycle.getChildAdapterPosition(itemView);
                Intent intent = new Intent(SearchActivity.this, PersonalPostActivity.class);
                intent.putExtra("post", listPost.get(i));
                startActivity(intent);
            }

            @Override
            public void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = search_recycle.getChildAdapterPosition(itemView);
                if (isCheck) {
                    addLike(button, listPost.get(i), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeLike(button, listPost.get(i), currentUser.getUid());
                    button.setChecked(false);

                }
            }
        });
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        search_recycle.setLayoutManager(manager);
        search_recycle.setAdapter(postAdapter);

        search_filter.setOnClickListener(this);
        if (!season.equals("no")){
            search_tv_content.setText("Tìm kiếm: "+season);
            searchPost(season);
        }else{
            search_tv_content.setText("Tìm kiếm: "+searchContent);
        }

    }

    public void initSpinner(){
        ArrayList<String> list = new ArrayList<>();
        list.add("lượt xem");
        list.add("lượt tải");
        list.add("lượt thích");
        list.add("lượt chia sẻ");

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        search_spinner.setAdapter(adp1);
    }

    public void searchPost(String season){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.orderByChild("season").equalTo(season).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPost.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    listPost.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addLike(Button button, Post post, String Uid) {

        Likes like = new Likes(post.getPostId(), Uid);
        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Post").child(post.getPostId()).child("countLike").setValue((post.getCountLike() + 1));

        String key = reference.child("Likes").push().getKey();
        like.setLikeId(key);

        Map<String, Object> like_value = like.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Likes/" + like.getLikeId(), like_value);

        Task<Void> task = reference.updateChildren(child_add);

        if (task.isSuccessful() == false) {
            animation.animationBigger(button);
        } else {
            Toast.makeText(this, "Chưa like đc", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeLike(Button button, final Post post, String Uid) {

        if (currentUser != null) {

            DatabaseReference postReference = firebase_database.getReference("Post");
            postReference.child(post.getPostId()).child("countLike").setValue((post.getCountLike()-1));
            database_reference = firebase_database.getReference("Likes");
            database_reference.orderByChild("userId").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Likes likes = snapshot.getValue(Likes.class);
                        if (likes.getPostId().equals(post.getPostId())) {
                            database_reference.child(snapshot.getKey()).removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
    }

    

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_filter:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                LayoutInflater inflater = getLayoutInflater();
//                View view = inflater.inflate(R.layout.item_post_filter);
                alert.setView(R.layout.item_post_filter);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

        }
    }
}
