package com.example.howtocook.uis.fragments.post;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PostCommentAdapter;
import com.example.howtocook.model.basemodel.Comments;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.PersonalPostActivity;
import com.example.howtocook.uis.acticities.addpostac.AddPostStepActivity;
import com.example.howtocook.utils.DateUtil;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostCommentFragment extends Fragment {

    RecyclerView post_comment_recycle;
    EditText edt_post_user_comment;
    Button btn_post_user_send_comment;
    RatingBar post_comment_ratingBar;
    CircleImageView post_user_ava_comment;

    private ArrayList<Comments> listComment;
    PostCommentAdapter postCommentAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth mAuth;
    Post post;
    ProgressDialog progressDialog;
    public PostCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post_comment_recycle =  view.findViewById(R.id.post_comment_recycle);
        edt_post_user_comment = view.findViewById(R.id.edt_post_user_comment);
        btn_post_user_send_comment = view.findViewById(R.id.btn_post_user_send_comment);
        post_comment_ratingBar = view.findViewById(R.id.post_comment_ratingBar);
        post_user_ava_comment = view.findViewById(R.id.post_user_ava_comment);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getActivity().getIntent();
        post = (Post) intent.getSerializableExtra("post");
        initUser(currentUser.getUid());

        post_comment_ratingBar.setRating(4);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        post_comment_recycle.setLayoutManager(manager);
        listComment = new ArrayList<>();

        postCommentAdapter = new PostCommentAdapter(listComment, getContext());
        post_comment_recycle.setAdapter(postCommentAdapter);

        initListComment();
//

        btn_post_user_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                String content = edt_post_user_comment.getText().toString();
                Comments comment = new Comments();
                comment.setContent(content);
                comment.setRating(post_comment_ratingBar.getRating());
                comment.setCommentId(DateUtil.getcurrentDate());
                comment.setUserId(currentUser.getUid());
                comment.setPostId(post.getPostId());
                writeComment(comment);
                postCommentAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), " "+post_comment_ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                edt_post_user_comment.clearFocus();
                hideKeyboard(getActivity());

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_comment, container, false);
    }

    public void initUser(String Uid){
        final DatabaseReference user_reference = firebaseDatabase.getReference("Users").child(Uid);
        user_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                Glide.with(getActivity()).load(users.getUserImg()).into(post_user_ava_comment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "");
            }
        });
    }

    public void initListComment(){
        final DatabaseReference user_reference = firebaseDatabase.getReference("Post/"+post.getPostId());
        user_reference.child("Comments").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Comments comments = snapshot.getValue(Comments.class);
                    listComment.add(comments);
                }
                postCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "");
            }
        });
    }

    public void writeComment(Comments comments){

        // lay gia tri nhap tu activty
        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post/"+post.getPostId());

        String key = reference.child("Comments").push().getKey();

        Map<String, Object> cm_values = comments.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Comments/"+key, cm_values);

        Task<Void> task = reference.updateChildren(child_add);

        if(task.isSuccessful() == false){
            progressDialog.dismiss();

        } else {
            Log.e("aaa", "onCompleteAddUser: Failed=" + task.getException().getMessage());

            progressDialog.dismiss();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
