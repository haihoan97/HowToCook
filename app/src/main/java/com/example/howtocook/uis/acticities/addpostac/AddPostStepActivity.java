package com.example.howtocook.uis.acticities.addpostac;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howtocook.R;
import com.example.howtocook.adapter.addpostadapter.AddPostStepAdapter;
import com.example.howtocook.adapter.postadapter.PostResourceAdapter;
import com.example.howtocook.adapter.postadapter.PostStepAdapter;
import com.example.howtocook.adapter.postadapter.PostStepUriAdapter;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Images;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.PostStep;
import com.example.howtocook.model.basemodel.Prepare;
import com.example.howtocook.uis.acticities.AddPostActivity;
import com.example.howtocook.uis.acticities.MainActivity;
import com.example.howtocook.uis.acticities.PersonalActivity;
import com.example.howtocook.uis.acticities.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPostStepActivity extends AppCompatActivity implements View.OnClickListener {


    EditText add_post_step_resources_name;
    EditText add_post_step_resources_weight;
    Button add_post_step_back;
    Button add_post_step_next;
    EditText add_post_step_des;


    LinearLayout remove_post_step_add_resource;
    LinearLayout add_post_step_delete_step;
    RecyclerView add_post_step_recycle_resources;
    RecyclerView add_post_step_recycle;
    LinearLayout add_post_step_add_resource;
    LinearLayout add_post_step_add_step;
    LinearLayout add_post_step2;
    ProgressDialog progressDialog;

    Bitmap imgAvaBitmap = null;


    PostResourceAdapter addPostResourceAdapter;
    ArrayList<Prepare> listResource;
    AddPostStepAdapter addPostStepAdapter;
    ArrayList<PostStep> listStep;

    PostStep postStep;
    Uri uri;
    Post post;

    int recyclePos = 0;


    //Firebase
    private FirebaseAuth mAuth;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;


    private static final int REQUEST_CAPTURE1 = 1111;
    private static final int REQUEST_GALLERY1 = 2221;
    private static final int REQUEST_CAPTURE2 = 1112;
    private static final int REQUEST_GALLERY2 = 2222;
    private static final int REQUEST_CAPTURE3 = 1113;
    private static final int REQUEST_GALLERY3 = 2223;
    private static final int REQUEST_CAPTURE4 = 1114;
    private static final int REQUEST_GALLERY4 = 2224;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_step);


        add_post_step_back = findViewById(R.id.add_post_step_back);
        add_post_step_next = findViewById(R.id.add_post_step_next);
        add_post_step_resources_weight = findViewById(R.id.add_post_step_resources_weight);
        add_post_step_resources_name = findViewById(R.id.add_post_step_resources_name);

        add_post_step_recycle_resources = findViewById(R.id.add_post_step_recycle_resources);
        add_post_step_recycle = findViewById(R.id.add_post_step_recycle);

        add_post_step_add_resource = findViewById(R.id.add_post_step_add_resource);
        add_post_step_add_step = findViewById(R.id.add_post_step_add_step);
        add_post_step2 = findViewById(R.id.add_post_step2);
        remove_post_step_add_resource = findViewById(R.id.remove_post_step_add_resource);
        add_post_step_delete_step = findViewById(R.id.add_post_step_delete_step);

        add_post_step_des = findViewById(R.id.add_post_step_des);

        mAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference("poststep");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");
        Log.d("aaa", post.getPostImage());
        postStep = new PostStep();
        ArrayList<Images> listImg = new ArrayList<>();
        postStep.setListImg(listImg);

        add_post_step_add_resource.setOnClickListener(this);
        add_post_step_delete_step.setOnClickListener(this);
        add_post_step_add_step.setOnClickListener(this);
        add_post_step_next.setOnClickListener(this);
        remove_post_step_add_resource.setOnClickListener(this);
        add_post_step_back.setOnClickListener(this);


        add_post_step2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(AddPostStepActivity.this);
                return false;
            }
        });

        initListResource();
        initListStep();
    }

    public void initListResource() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        add_post_step_recycle_resources.setLayoutManager(manager);
        listResource = new ArrayList<>();
        addPostResourceAdapter = new PostResourceAdapter(listResource);
        add_post_step_recycle_resources.setAdapter(addPostResourceAdapter);
    }

    public void initListStep() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        add_post_step_recycle.setLayoutManager(manager);
        listStep = new ArrayList<>();
        addPostStepAdapter = new AddPostStepAdapter(listStep, this, new AddPostStepAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView) {
                int i = add_post_step_recycle.getChildAdapterPosition(itemView);
            }

            @Override
            public void onImageClick(View itemView, ImageView image) {
                recyclePos = add_post_step_recycle.getChildAdapterPosition(itemView);
                if (image.getId() == R.id.add_post_step_img) {
                    recyclePos = add_post_step_recycle.getChildAdapterPosition(itemView);
                    if (listStep.get(recyclePos).getListImg().size() < 4) {
                        openCameraImage(REQUEST_CAPTURE1, REQUEST_GALLERY1);
                    } else {
                        Toast.makeText(AddPostStepActivity.this, "Bạn đã có đủ 4 ảnh rồi", Toast.LENGTH_SHORT).show();
                    }

                } else {
//                    recyclePos = add_post_step_recycle.getChildAdapterPosition(itemView);
//                    listStep.remove(recyclePos);
//                    addPostStepAdapter.notifyItemRemoved(recyclePos);
                }


            }

            @Override
            public void onEditorClick(View itemView, EditText editText) {
                recyclePos = add_post_step_recycle.getChildAdapterPosition(itemView);
                listStep.get(recyclePos).setStepContent(editText.getText().toString().trim());
            }
        });
        add_post_step_recycle.setAdapter(addPostStepAdapter);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAPTURE1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(requestCode);
                } else {
                    Toast.makeText(this, "Camera isn't available", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(requestCode);
                } else {
                    Toast.makeText(this, "Can't not open Library", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CAPTURE2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(requestCode);
                } else {
                    Toast.makeText(this, "Camera isn't available", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(requestCode);
                } else {
                    Toast.makeText(this, "Can't not open Library", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CAPTURE3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(requestCode);
                } else {
                    Toast.makeText(this, "Camera isn't available", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(requestCode);
                } else {
                    Toast.makeText(this, "Can't not open Library", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CAPTURE4:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(requestCode);
                } else {
                    Toast.makeText(this, "Camera isn't available", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY4:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(requestCode);
                } else {
                    Toast.makeText(this, "Can't not open Library", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURE1:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    Bundle extras = data.getExtras();
                    imgAvaBitmap = (Bitmap) extras.get("data");
                    listStep.get(recyclePos).getListImg().add(new Images("", "", uri.toString(), ""));
                    addPostStepAdapter.notifyDataSetChanged();
                    uri = null;
                }
                break;
            case REQUEST_GALLERY1:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();

                    imgAvaBitmap = getBitmapFromUri(AddPostStepActivity.this, uri);
                    Log.d("aaa", uri.toString());
                    listStep.get(recyclePos).getListImg().add(new Images("", "", uri.toString(), ""));
                    addPostStepAdapter.notifyDataSetChanged();
                    uri = null;
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, cameraCode);
        } else {
            openCamera(cameraCode);
        }
    }

    public void openCamera(int cameraCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, cameraCode);
        }
    }

    public void openCameraImage(final int cameraCode, final int libraryCode) {
        CharSequence[] items = {"Camera", "Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPostStepActivity.this);
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

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadFile(final Images images, Uri uri) {
        if (uri != null) {

            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadIamgeUri(images, fileReference);
                    progressDialog.dismiss();


                }
            })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Log.d("url", "");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPostStepActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(AddPostStepActivity.this, "choose a image", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    private void getDownloadIamgeUri(final Images images, StorageReference file_url) {
        final DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        file_url.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Log.d("url", uri.toString());
                images.setImgLink(uri.toString());
                //Log.d("imgLink ", images.getImgLink());
                writePostStepImage(images);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                return;
            }
        });


    }

    private void writePostStepImage(Images images) {

        // lay gia tri nhap tu activty


        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post/" + post.getPostId() + "/PostStep/" + images.getPostStepId());

        String key = reference.child("Images").push().getKey();
        images.setImageId(key);

        Map<String, Object> image_values = images.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Images/" + key, image_values);

        Task<Void> task = reference.updateChildren(child_add);

        if (task.isSuccessful() == false) {
            //progressDialog.dismiss();

        } else {
            Log.e("aaa", "onCompleteAddUser: Failed=" + task.getException().getMessage());


        }

    }

    private void writePostStep(PostStep postStep) {

        postStep.setPostId(post.getPostId());
        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post/" + post.getPostId());

        String key = reference.child("PostStep").push().getKey();
        postStep.setPostStepId(key);

        Map<String, Object> post_values = postStep.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/PostStep/" + key, post_values);

        Task<Void> task = reference.updateChildren(child_add);
        for (Images images : postStep.getListImg()) {
            Uri uri = Uri.parse(images.getImgLink());
            images.setPostStepId(key);
            uploadFile(images, uri);
        }

        if (task.isSuccessful() == false) {
            //progressDialog.dismiss();
            Log.d("add", "step " + postStep.getStep() + " ok");
        } else {
            Log.e("aaa", "onCompleteAddUser: Failed=" + task.getException().getMessage());


        }


    }

    private void writePostSource(ArrayList<Prepare> listSource) {

        for (Prepare prepare : listSource) {
            // lay gia tri nhap tu activty
            prepare.setPostId(post.getPostId());


            // day du lieu len firebase
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post/" + post.getPostId());

            String key = reference.child("Prepare").push().getKey();
            prepare.setPrepareId(key);

            Map<String, Object> post_values = prepare.toMap();

            Map<String, Object> child_add = new HashMap<>();
            child_add.put("/Prepare/" + key, post_values);

            Task<Void> task = reference.updateChildren(child_add);


            if (task.isSuccessful() == false) {
                //progressDialog.dismiss();
                Log.d("step", "them source");

            } else {
                Log.e("aaa", "onCompleteAddUser: Failed= Source" + task.getException().getMessage());


            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_post_step_add_resource:
                String sourceName = add_post_step_resources_name.getText().toString().trim();
                String sourceAmount = add_post_step_resources_weight.getText().toString().trim();
                if (sourceName.equals("")){
                    add_post_step_resources_name.setError("Bạn chưa điền tên nguyên liệu");
                    return;
                }
                if (sourceAmount.equals("")){
                    add_post_step_resources_weight.setError("Bạn chưa điền tên nguyên liệu");
                    return;
                }
                Prepare prepare = new Prepare("", "", sourceName, sourceAmount);
                listResource.add(prepare);
                addPostResourceAdapter.notifyDataSetChanged();
                break;

            case R.id.remove_post_step_add_resource:
                listResource.remove(listResource.size() - 1);
                addPostResourceAdapter.notifyDataSetChanged();
                break;
            case R.id.add_post_step_back:
                //deletePost(post.getPostId());
                finish();
                break;

            case R.id.add_post_step_add_step:
                PostStep postStep = new PostStep();
                ArrayList<Images> listImg = new ArrayList<>();
                postStep.setListImg(listImg);
                listStep.add(postStep);
                addPostStepAdapter.notifyDataSetChanged();
                break;
            case R.id.add_post_step_next:
                progressDialog = new ProgressDialog(this);
                progressDialog.show();

                if (listResource.size() < 2) {
                    Toast.makeText(this, "Bạn chưa thêm tối thiểu 2 nguyên liệu", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                if (listStep.size() < 2) {
                    Toast.makeText(this, "Bạn phải thêm tối thiểu 2 bước", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                for (int i = 0; i < listStep.size(); i++) {

                    PostStep postStep1 = listStep.get(i);
                    View view = add_post_step_recycle.getChildAt(i);
                    EditText editText = view.findViewById(R.id.add_post_step_des);

                    if (editText.getText().toString().trim().isEmpty()) {
                        editText.setError("Banj chua dien mo ta");
                        progressDialog.dismiss();
                        return;
                    }
                    if (postStep1.getListImg().size() == 0 || postStep1.getListImg() == null) {
                        Toast.makeText(this, "Bạn chưa thêm ảnh bước " + (i+1), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }
                }
                writePost(post);
                writePostSource(listResource);
                for (int i = 0; i < listStep.size(); i++) {

                    PostStep postStep1 = listStep.get(i);
                    View view = add_post_step_recycle.getChildAt(i);
                    EditText editText = view.findViewById(R.id.add_post_step_des);
                    postStep1.setStepContent(editText.getText().toString().trim());
                    postStep1.setStep(i);
                    writePostStep(postStep1);
                }
                showDialogSuccess();
                progressDialog.dismiss();
                break;

        }

    }

//    public void deletePost(final String postId){
//            mDatabaseReference = FirebaseDatabase.getInstance().getReference("Post");
//            mDatabaseReference.orderByChild("postId").equalTo(postId).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Post post = snapshot.getValue(Post.class);
//                        if (post.getPostId().equals(postId)){
//                            mDatabaseReference.child(snapshot.getKey()).removeValue();
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.e("Loi", "");
//                }
//            });
//
//
//    }

    private void showDialogSuccess() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_success);

        Button btn_success;
        TextView txt_title_message_success, txt_content_message_success;

        txt_title_message_success = dialog.findViewById(R.id.txt_title_message_success);
        txt_content_message_success = dialog.findViewById(R.id.txt_content_message_success);
        btn_success = dialog.findViewById(R.id.btn_success);

        txt_title_message_success.setText("Thành công");
        txt_content_message_success.setText("Bạn đã đăng bài viết thành công");

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent personal_intent = new Intent(AddPostStepActivity.this, MainActivity.class);
                startActivity(personal_intent);
                AddPostStepActivity.this.finish();
            }
        });

        dialog.show();
    }

    private Bitmap getBitmap(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return bitmap;
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private void writePost(Post post){
        FirebaseUser curren_user = mAuth.getCurrentUser();
        // lay gia tri nhap tu activty
        post.setUserId(curren_user.getUid());


        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String key = reference.child("Post").push().getKey();
        post.setPostId(key);

        Map<String, Object> post_values = post.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Post/"+key, post_values);

        Task<Void> task = reference.updateChildren(child_add);

        if(task.isSuccessful() == false){
            progressDialog.dismiss();



        } else {
            Log.e("aaa", "onCompleteAddUser: Failed=" + task.getException().getMessage());

            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        //deletePost(post.getPostId());
        super.onBackPressed();
    }
}
