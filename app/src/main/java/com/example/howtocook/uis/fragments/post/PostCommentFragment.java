package com.example.howtocook.uis.fragments.post;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PostCommentAdapter;
import com.example.howtocook.model.basemodel.Comments;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostCommentFragment extends Fragment {

    RecyclerView post_comment_recycle;
    EditText edt_post_user_comment;
    Button btn_post_user_send_comment;
    RatingBar post_comment_ratingBar;

    private ArrayList<Comments> listComment;
    PostCommentAdapter postCommentAdapter;
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

        post_comment_ratingBar.setRating(4);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        post_comment_recycle.setLayoutManager(manager);
        listComment = new ArrayList<>();

        postCommentAdapter = new PostCommentAdapter(listComment);
        post_comment_recycle.setAdapter(postCommentAdapter);

        initListComment();
//

        btn_post_user_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edt_post_user_comment.getText().toString();
                Comments comment = new Comments();
                listComment.add(0,comment);
                postCommentAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), " "+post_comment_ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                edt_post_user_comment.clearFocus();
                hideKeyboard(getActivity());

            }
        });

//        post_comment_user_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_comment, container, false);
    }

    public void initListComment(){
//        listComment.clear();
//        for (int i = 0; i<5;i++){
//            Comment comment = new Comment(i, new Post(1), new User(1, "MIKU", true), "hay "+i, "2019-05-07", 5-i);
//            listComment.add(comment);
//        }
//
//        postCommentAdapter.notifyDataSetChanged();
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
