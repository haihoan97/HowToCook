package com.example.howtocook.uis.fragments.post;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PostShareAdapter;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Share;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.AddPostActivity;
import com.example.howtocook.uis.acticities.addpostac.AddPostStepActivity;
import com.example.howtocook.utils.DateUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostShareFragment extends Fragment implements View.OnClickListener {

    private RecyclerView post_share_recycle;
    private ArrayList<Share> listShare;
    private PostShareAdapter postShareAdapter;
    Button btn_post_add_share;
    CircleImageView btn_post_share_ava;
    LinearLayout item_add_share_des_layout;
    ImageView item_add_share_send;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    Post post;
    ImageView item_add_share_img;
    EditText item_add_share_des;

    Bitmap imgAvaBitmap = null;

    private static final int REQUEST_CAPTURE = 111;
    private static final int REQUEST_GALLERY = 222;


    Uri uri;
    ProgressDialog progressDialog;

    private StorageReference mStorageReference;
    private boolean isOpen = false;


    public PostShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseApp.initializeApp(getActivity());

        post_share_recycle = view.findViewById(R.id.post_share_recycle);
        btn_post_add_share = view.findViewById(R.id.btn_post_add_share);
        btn_post_share_ava = view.findViewById(R.id.btn_post_share_ava);
        item_add_share_img = view.findViewById(R.id.item_add_share_img);
        item_add_share_des = view.findViewById(R.id.item_add_share_des);
        item_add_share_des_layout = view.findViewById(R.id.item_add_share_des_layout);
        item_add_share_send = view.findViewById(R.id.item_add_share_send);
        firebaseDatabase = FirebaseDatabase.getInstance();

        initUser(currentUser.getUid());
        mStorageReference = FirebaseStorage.getInstance().getReference("postShare");

        listShare = new ArrayList<>();
        Intent intent = getActivity().getIntent();
        post = (Post) intent.getSerializableExtra("post");


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        post_share_recycle.setLayoutManager(manager);
        postShareAdapter = new PostShareAdapter(listShare, getActivity());
        post_share_recycle.setAdapter(postShareAdapter);
        setShareData();
        btn_post_add_share.setOnClickListener(this);
        item_add_share_img.setOnClickListener(this);
        item_add_share_send.setOnClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_share, container, false);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAPTURE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(requestCode);
                } else {
                    Toast.makeText(getActivity(), "Camera isn't available", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(requestCode);
                } else {
                    Toast.makeText(getActivity(), "Can't not open Library", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURE:
                if (resultCode == getActivity().RESULT_OK) {
                    uri = data.getData();
                    Bundle extras = data.getExtras();
                    imgAvaBitmap = (Bitmap) extras.get("data");
                    item_add_share_img.setImageBitmap(imgAvaBitmap);
                }
                break;
            case REQUEST_GALLERY:
                if (resultCode == getActivity().RESULT_OK) {
                    uri = data.getData();
                    imgAvaBitmap = getBitmapFromUri(getActivity(), uri);
                    item_add_share_img.setImageBitmap(imgAvaBitmap);
                }
                break;

        }

    }

    public Bitmap getBitmapFromUri(Context context, Uri uriFile) {
        Bitmap bitmap = null;
        try {
            bitmap = decodeUri(context, uriFile);
        } catch (IOException e) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher);
            e.printStackTrace();

        }
        return bitmap;
    }

    private static Bitmap decodeUri(Context context, Uri selectedImage) throws FileNotFoundException {

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o);

        // the new size we want to scale
        final int REQUIRED_SIZE = 100;

        //find the correct scale value. It should power of 2
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        //DECODE WITH inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o2);
    }

    private void handleGetImageFromLibrary(int libraryCode) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, libraryCode);
        } else {
            openGallery(libraryCode);
        }
    }

    public void openGallery(int libraryCode) {
        Intent intent = new Intent();
        //show only img, no video or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Chon 1 ảnh"), libraryCode);
    }

    //Take a photo

    private void handleGetImageFromCamera(int cameraCode) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, cameraCode);
        } else {
            openCamera(cameraCode);
        }
    }

    public void openCamera(int cameraCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, cameraCode);
        }
    }

    public void openCameraImage(final int cameraCode, final int libraryCode) {
        CharSequence[] items = {"Camera", "Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chọn ảnh");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //take photo
                    handleGetImageFromCamera(cameraCode);

                } else {
                    //pick Image from library
                    handleGetImageFromLibrary(libraryCode);

                }
            }
        });
        builder.show();
    }

    public void initUser(String Uid) {
        final DatabaseReference user_reference = firebaseDatabase.getReference("Users").child(Uid);
        user_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                Glide.with(getActivity()).load(users.getUserImg()).into(btn_post_share_ava);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "");
            }
        });
    }

    public void setShareData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Share");
        databaseReference.orderByChild("postId").equalTo(post.getPostId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listShare.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Share share = snapshot.getValue(Share.class);
                    listShare.add(share);
                }
                postShareAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    public void uploadFile(final String content) {
        if (uri != null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadIamgeUri(fileReference, content);
                    //Toast.makeText(AddPostActivity.this, "them thanh cong : ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Log.d("url","");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(getActivity(), "choose a image", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    private void getDownloadIamgeUri(StorageReference file_url, final String content) {
        final DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        file_url.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Log.d("url", uri.toString());

                Share share = new Share();
                share.setPostId(post.getPostId());
                share.setShareImg(uri.toString());
                share.setUserId(currentUser.getUid());
                share.setShareTime(DateUtil.getcurrentDate());
                share.setShareContent(content);

                writeShare(share);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                return;
            }
        });


    }



    private void writeShare(Share share){
        // lay gia tri nhap tu activty


        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String key = reference.child("Share").push().getKey();
        share.setShareId(key);

        Map<String, Object> share_value = share.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Share/"+key, share_value);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post_add_share:
                if (currentUser.getUid().equals(post.getUserId())){
                    Toast.makeText(getActivity(), "Bạn không thể tự chia sẻ bài viết của mình", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isOpen){
                    item_add_share_des_layout.setVisibility(View.GONE);
                    isOpen = false;
                }else{
                    item_add_share_des_layout.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
                break;
            case R.id.item_add_share_img:
                openCameraImage(REQUEST_CAPTURE, REQUEST_GALLERY);
                break;
            case R.id.item_add_share_send:
                String content = item_add_share_des.getText().toString().trim();
                if (content.isEmpty()){
                    item_add_share_des.setError("Bạn không được bỏ trống trường này");
                }else{
                    uploadFile(content);
                    item_add_share_des.clearFocus();
                    hideKeyboard(getActivity());
                }
                break;
        }

    }
}
