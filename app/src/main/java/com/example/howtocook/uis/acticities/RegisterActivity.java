package com.example.howtocook.uis.acticities;

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
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
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
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView register_image;
    TextView register_set_image;
    EditText edt_user_fullname;
    EditText edt_username;
    EditText edt_password;
    Button btn_register;

    Bitmap imgAvaBitmap = null;

    private static final int REQUEST_CAPTURE = 111;
    private static final int REQUEST_GALLERY = 222;
    Uri uri;

    //Firebase
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    FirebaseAuth firebase_auth;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);


        register_image = findViewById(R.id.register_image);
        register_set_image = findViewById(R.id.register_set_image);
        edt_user_fullname = findViewById(R.id.edt_user_fullname);
        edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);
        btn_register = findViewById(R.id.btn_register);

        firebase_auth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference("userava");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("userava");

        register_set_image.setOnClickListener(this);
        btn_register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_set_image:
                openCameraImage(REQUEST_CAPTURE, REQUEST_GALLERY);
                break;
            case R.id.btn_register:
                uploadFile();
                break;
        }
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
                    register_image.setImageURI(uri);
                }
                break;

            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    imgAvaBitmap = getBitmapFromUri(this, uri);
                    register_image.setImageBitmap(imgAvaBitmap);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            progressDialog.setTitle("Đăng ký...");
            progressDialog.show();
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadIamgeUri(fileReference);
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
                            Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(this, "choose a image", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDownloadIamgeUri(StorageReference file_url) {
        final DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        file_url.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("url", uri.toString());
                String username = edt_username.getText().toString().trim();
                String pass = edt_password.getText().toString().trim();
                String imglink = uri.toString();
                String fullName = edt_user_fullname.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    edt_username.setError("ko dc bo trong");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edt_password.setError("ko dc bo trong");
                    return;
                }
                if (TextUtils.isEmpty(fullName)){
                    edt_user_fullname.setError("ko dc bo trong");
                    return;
                }

                Users users = new Users(username, pass, fullName, imglink, true);
                signUP(users);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                return;
            }
        });


    }


    private void signUP(final Users users) {

        String email = users.getUsername();
        String password = users.getPassword();
        firebase_auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeUser(users);
                            Log.d("aaa","ok");
                        } else {
                            Log.e("aaa", "onComplete: Failed=" + task.getException().getMessage());
                            showDialogFail(
                                    "Ồ! Có lỗi xảy ra.",
                                    "Có vẻ như việc đăng kí tài khoản của bạn có trục trặc! Bạn có muốn tiếp tục hành dộng này.",
                                    "Thử lại sau",
                                    "Thử lại"
                            );
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    // ghi du lieu vao firebase
    private void writeUser(Users users){


        FirebaseUser curren_user = firebase_auth.getCurrentUser();
        // lay gia tri nhap tu activty
        users.setUserId(curren_user.getUid());


        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String key = reference.child("Users").push().getKey();

        Map<String, Object> user_values = users.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Users/"+curren_user.getUid(), user_values);

        Task<Void> task = reference.updateChildren(child_add);

        if(task.isSuccessful() == false){
            progressDialog.dismiss();
            showDialogSuccess();

        } else {
            Log.e("aaa", "onCompleteAddUser: Failed=" + task.getException().getMessage());
            showDialogFail(
                    "Ồ! Có lỗi xảy ra.",
                    "Có vẻ như việc đăng kí tài khoản của bạn có trục trặc",
                    "Thử lại sau",
                    "Thử lại"
            );
            progressDialog.dismiss();
        }
    }
    // ghi du lieu vao firebase

    /*phuong thuc show dialog khi dang ki thanh cong */
    private void showDialogSuccess(){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_success);

        Button btn_success;
        TextView txt_title_message_success, txt_content_message_success;

        txt_title_message_success = dialog.findViewById(R.id.txt_title_message_success);
        txt_content_message_success = dialog.findViewById(R.id.txt_content_message_success);
        btn_success = dialog.findViewById(R.id.btn_success);

        txt_title_message_success.setText("Thành công");
        txt_content_message_success.setText("Việc đăng ký tài khoản của bạn đã thành công. Bạn có muốn sử dụng hệ thống ngay.");

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

        dialog.show();
    }
    /*phuong thuc show dialog khi dang ki thanh cong */

    /*phuong thuc show dialog khi dang ki that bai*/
    private void showDialogFail(String title, String message, String btn_name_left, String btn_name_right){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_fail);

        Button btn_exit, btn_again;
        TextView txt_message_fail, txt_title_message_fail;

        btn_exit = dialog.findViewById(R.id.btn_action);
        btn_again = dialog.findViewById(R.id.btn_again);
        txt_message_fail = dialog.findViewById(R.id.txt_message_fail);
        txt_title_message_fail = dialog.findViewById(R.id.txt_title_message_fail);

        txt_message_fail.setText(message);
        txt_title_message_fail.setText(title);
        btn_exit.setText(btn_name_left);
        btn_again.setText(btn_name_right);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });

        dialog.show();
    }
}
