package com.example.howtocook.uis.acticities;

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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PostResourceAdapter;
import com.example.howtocook.adapter.postadapter.PostStepAdapter;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.PostStep;
import com.example.howtocook.model.basemodel.Prepare;
import com.example.howtocook.uis.acticities.addpostac.AddPostStepActivity;
import com.example.howtocook.utils.DateUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {


    Spinner add_post_muc_do;
    Spinner add_post_khau_phan;

    NestedScrollView add_post_chi_tiet;

    Button add_post_chi_tiet_next;
    Button add_post_filter_back;
    Button add_post_filter_save;

    EditText add_post_name;
    EditText add_post_des;

    ImageView add_post_image;
    ImageView add_post_add_image;


    LinearLayout add_post_chi_tiet2;



    ProgressDialog progressDialog;

    Bitmap imgAvaBitmap = null;

    private static final int REQUEST_CAPTURE = 111;
    private static final int REQUEST_GALLERY = 222;


    Uri uri;

    //Firebase
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        FirebaseApp.initializeApp(this);

        add_post_muc_do = findViewById(R.id.add_post_muc_do);
        add_post_khau_phan = findViewById(R.id.add_post_khau_phan);
        add_post_chi_tiet = findViewById(R.id.add_post_chi_tiet);
        add_post_chi_tiet_next = findViewById(R.id.add_post_chi_tiet_next);
        add_post_filter_back = findViewById(R.id.add_post_filter_back);
        add_post_filter_save = findViewById(R.id.add_post_filter_save);

        add_post_name = findViewById(R.id.add_post_name);
        add_post_des = findViewById(R.id.add_post_des);

        add_post_image = findViewById(R.id.add_post_image);
        add_post_add_image = findViewById(R.id.add_post_add_image);

        add_post_chi_tiet2 = findViewById(R.id.add_post_chi_tiet2);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference("postava");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("postava");


        add_post_image.requestFocus();


        initSpinner();

        add_post_chi_tiet_next.setOnClickListener(this);
        add_post_add_image.setOnClickListener(this);

        add_post_chi_tiet2.setOnTouchListener(this);

    }



    public void initSpinner() {
        ArrayList<String> listMucDo = new ArrayList<>();
        listMucDo.add("Dễ");
        listMucDo.add("Trung bình");
        listMucDo.add("Khó");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listMucDo);
        add_post_muc_do.setAdapter(adp1);

        ArrayList<String> lisKhauPhan = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            lisKhauPhan.add(i + 1 + " người");
        }

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lisKhauPhan);
        add_post_khau_phan.setAdapter(adp2);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAPTURE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(requestCode);
                } else {
                    Toast.makeText(this, "Camera isn't available", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY:
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
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    Bundle extras = data.getExtras();
                    imgAvaBitmap = (Bitmap) extras.get("data");
                    add_post_image.setImageURI(uri);
                }
                break;
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    imgAvaBitmap = getBitmapFromUri(AddPostActivity.this, uri);
                    add_post_image.setImageBitmap(imgAvaBitmap);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_post_chi_tiet_next:

                if (TextUtils.isEmpty(add_post_name.getText().toString().trim())){
                    add_post_name.setError("Bạn phải điền tên công thức");
                    return;
                }
                if (TextUtils.isEmpty(add_post_des.getText().toString().trim())){
                    add_post_des.setError("Bạn phải điền mô tả công thức");
                    return;
                }

                uploadFile();

                break;
            case R.id.add_post_add_image:
                openCameraImage(REQUEST_CAPTURE, REQUEST_GALLERY);
                break;

        }

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
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPostActivity.this);
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


    public void uploadFile() {
        if (uri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadIamgeUri(fileReference);
                    Toast.makeText(AddPostActivity.this, "them thanh cong : ", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddPostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(AddPostActivity.this, "choose a image", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    private void getDownloadIamgeUri(StorageReference file_url) {
        final DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        file_url.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("url", uri.toString());
                Post post = new Post();
                post.setPostName(add_post_name.getText().toString().trim());
                post.setPostImage(uri.toString());
                post.setMucDo(add_post_muc_do.getSelectedItem().toString());
                post.setKhauPhan(add_post_khau_phan.getSelectedItem().toString());
                post.setPostDes(add_post_des.getText().toString().trim());
                post.setPostTime(DateUtil.getcurrentDate());

                writePost(post);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                return;
            }
        });


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
            Intent intent = new Intent(this, AddPostStepActivity.class);
            intent.putExtra("post", post);
            startActivity(intent);

        } else {
            Log.e("aaa", "onCompleteAddUser: Failed=" + task.getException().getMessage());

            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.add_post_chi_tiet2:
                hideKeyboard(AddPostActivity.this);
                add_post_muc_do.requestFocus();
                return true;
            case R.id.add_post_step2:
                hideKeyboard(AddPostActivity.this);
                return true;
        }

        return false;
    }
}
